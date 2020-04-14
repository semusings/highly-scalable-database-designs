package io.github.bhuwanupadhyay.employees;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.github.bhuwanupadhyay.employees.application.EmployeeHandler.EmployeeRequest;

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
	void canCreateEmployee() {
		this.client.post()
				.uri(
						builder -> builder
								.port(serverPort)
								.path("/employees")
								.build()
				)
				.body(BodyInserters.fromValue(new EmployeeRequest("Apple Gamma")))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.resource").isNotEmpty()
				.jsonPath("$.resource.employeeId").isNotEmpty()
				.jsonPath("$.resource.name").isNotEmpty()
				.jsonPath("$.resource.name").isEqualTo("Apple Gamma")
				.jsonPath("$.resource.status").isNotEmpty()
				.jsonPath("$.resource.status").isEqualTo("JOINED");
	}

	@Test
	void canUpdateEmployee() {
		this.client.post()
				.uri(
						builder -> builder
								.port(serverPort)
								.path("/employees")
								.build()
				)
				.body(BodyInserters.fromValue(new EmployeeRequest("Apple Gamma")))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.resource").isNotEmpty()
				.jsonPath("$.resource.employeeId").isNotEmpty()
				.jsonPath("$.resource.name").isNotEmpty()
				.jsonPath("$.resource.name").isEqualTo("Apple Gamma")
				.jsonPath("$.resource.employeeId")
				.value(employeeId -> {
					this.client.put()
							.uri(
									builder -> builder
											.port(serverPort)
											.path("/employees/" + employeeId)
											.build()
							)
							.body(BodyInserters.fromValue(new EmployeeRequest("Gamma Apple")))
							.accept(MediaType.APPLICATION_JSON)
							.exchange()
							.expectStatus()
							.isOk()
							.expectBody()
							.jsonPath("$.resource").isNotEmpty()
							.jsonPath("$.resource.employeeId").isNotEmpty()
							.jsonPath("$.resource.employeeId").isEqualTo(employeeId)
							.jsonPath("$.resource.name").isNotEmpty()
							.jsonPath("$.resource.name").isEqualTo("Gamma Apple")
							.jsonPath("$.resource.status").isNotEmpty()
							.jsonPath("$.resource.status").isEqualTo("JOINED");

				});

	}


	@Test
	void canDeleteEmployee() {
		this.client.post()
				.uri(
						builder -> builder
								.port(serverPort)
								.path("/employees")
								.build()
				)
				.body(BodyInserters.fromValue(new EmployeeRequest("Apple Gamma")))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.resource").isNotEmpty()
				.jsonPath("$.resource.employeeId").isNotEmpty()
				.jsonPath("$.resource.name").isNotEmpty()
				.jsonPath("$.resource.name").isEqualTo("Apple Gamma")
				.jsonPath("$.resource.employeeId")
				.value(employeeId -> {
					this.client.delete()
							.uri(
									builder -> builder
											.port(serverPort)
											.path("/employees/" + employeeId)
											.build()
							)
							.accept(MediaType.APPLICATION_JSON)
							.exchange()
							.expectStatus()
							.isOk()
							.expectBody()
							.jsonPath("$.resource").isNotEmpty()
							.jsonPath("$.resource.employeeId").isNotEmpty()
							.jsonPath("$.resource.employeeId").isEqualTo(employeeId)
							.jsonPath("$.resource.name").isNotEmpty()
							.jsonPath("$.resource.name").isEqualTo("Apple Gamma")
							.jsonPath("$.resource.status").isNotEmpty()
							.jsonPath("$.resource.status").isEqualTo("RESIGNED");

				});

	}

	@AfterEach
	void tearDown() {
		jdbc.update("DELETE FROM employee");
	}
}
