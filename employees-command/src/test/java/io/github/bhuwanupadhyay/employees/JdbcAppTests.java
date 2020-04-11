package io.github.bhuwanupadhyay.employees;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static io.github.bhuwanupadhyay.employees.application.EmployeeHandler.EmployeeRequest;

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
