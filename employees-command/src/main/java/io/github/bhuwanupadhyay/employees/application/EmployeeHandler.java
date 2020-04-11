package io.github.bhuwanupadhyay.employees.application;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface EmployeeHandler {

    String EMPLOYEE_ID = "id";
    String EMPLOYEE_ENTITY_NOT_FOUND = "employee_entity_not_found";

    Mono<ServerResponse> createEmployee(ServerRequest request);

    Mono<ServerResponse> updateEmployee(ServerRequest request);

    Mono<ServerResponse> deleteEmployee(ServerRequest request);

    record EmployeeRequest(@JsonProperty("name")String name) {
    }

    record EmployeeResource(@JsonProperty("employeeId")String employeeId,
                            @JsonProperty("name")String name,
                            @JsonProperty("status")String status) {
    }

    record MessageResource(@JsonProperty("lang")String lang,
                           @JsonProperty("value")String value) {
    }

    record ErrorResource(@JsonProperty("errorId")String errorId,
                         @JsonProperty("translations")List<MessageResource>translations) {
    }

    record ServerResource<T>(@JsonProperty("statusCode")int statusCode,
                             @JsonProperty("resource")T resource,
                             @JsonProperty("errors")List<ErrorResource>errors) {

        public static <E> ServerResource<E> withSuccess(E resource) {
            return new ServerResource<>(HttpStatus.OK.value(), resource, new ArrayList<>());
        }

        public static ServerResource<Void> withErrors(ErrorResource... errors) {
            return new ServerResource<>(HttpStatus.BAD_REQUEST.value(), null, List.of(errors));
        }
    }

}
