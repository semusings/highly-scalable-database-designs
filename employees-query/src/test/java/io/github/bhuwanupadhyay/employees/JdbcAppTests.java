package io.github.bhuwanupadhyay.employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(
        classes = App.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class JdbcAppTests {

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

