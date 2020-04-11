package io.github.bhuwanupadhyay.employees.application;


import java.time.LocalTime;
import java.util.function.Supplier;

import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeView;
import io.github.bhuwanupadhyay.employees.domain.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static io.github.bhuwanupadhyay.employees.application.EmployeeHandler.ServerResource.withErrors;
import static io.github.bhuwanupadhyay.employees.application.EmployeeHandler.ServerResource.withSuccess;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
class ReactiveEmployeeHandler implements EmployeeHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ReactiveEmployeeHandler.class);

	private final EmployeeRepository employeeRepository;

	private final ErrorResolver errorResolver;

	public ReactiveEmployeeHandler(EmployeeRepository employeeRepository,
			ErrorResolver errorResolver) {
		this.employeeRepository = employeeRepository;
		this.errorResolver = errorResolver;
	}

	@Override
	public Mono<ServerResponse> listEmployee(ServerRequest request) {

		LOG.debug("list employee at: {}", LocalTime.now());

		return ok().bodyValue(withSuccess(employeeRepository.list()));

	}

	@Override
	public Mono<ServerResponse> getEmployee(ServerRequest request) {
		String id = request.pathVariable(EMPLOYEE_ID);

		LOG.debug("update employee id: {}", id);

		return employeeRepository.find(id)
				.map(employee -> ok()
						.bodyValue(
								withSuccess(new EmployeeView(employee.employeeId(), employee.name(), employee.status()))
						))
				.orElseGet(employeeNotFound(id));
	}


	private Supplier<Mono<ServerResponse>> employeeNotFound(String id) {
		return () -> badRequest().bodyValue(withErrors(errorResolver.resolve(EMPLOYEE_ENTITY_NOT_FOUND, id)));
	}
}
