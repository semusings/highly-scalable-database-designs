package io.github.bhuwanupadhyay.employees;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(
		classes = App.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = {JdbcAppTests.Initializer.class})
@Testcontainers
class JdbcAppTests {

	@Container
	private static final PostgreSQLContainer SQL_CONTAINER = new PostgreSQLContainer()
			.withDatabaseName("employees")
			.withUsername("repl_user")
			.withPassword("repl_password");

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + SQL_CONTAINER.getJdbcUrl(),
					"spring.datasource.username=" + SQL_CONTAINER.getUsername(),
					"spring.datasource.password=" + SQL_CONTAINER.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private WebTestClient client;

	@LocalServerPort
	private int serverPort;

	@Autowired
	private JdbcTemplate jdbc;

	@Test
	void canGetEmployee() {
		this.client.get()
				.uri(
						builder -> builder
								.port(serverPort)
								.path("/employees/emp01")
								.build()
				)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.resource").isNotEmpty()
				.jsonPath("$.resource.employeeId").isNotEmpty()
				.jsonPath("$.resource.employeeId").isEqualTo("emp01")
				.jsonPath("$.resource.name").isNotEmpty()
				.jsonPath("$.resource.name").isEqualTo("Apple Gamma")
				.jsonPath("$.resource.status").isNotEmpty()
				.jsonPath("$.resource.status").isEqualTo("JOINED");
	}

	@Test
	void canListEmployee() {
		this.client.get()
				.uri(
						builder -> builder
								.port(serverPort)
								.path("/employees")
								.build()
				)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.resource").isNotEmpty()
				.jsonPath("$.resource.length()").isEqualTo(2)
				.jsonPath("$.resource[1].employeeId").isNotEmpty()
				.jsonPath("$.resource[1].employeeId").isEqualTo("emp02")
				.jsonPath("$.resource[1].name").isNotEmpty()
				.jsonPath("$.resource[1].name").isEqualTo("Gamma Apple")
				.jsonPath("$.resource[1].status").isNotEmpty()
				.jsonPath("$.resource[1].status").isEqualTo("RESIGNED");
	}

}

