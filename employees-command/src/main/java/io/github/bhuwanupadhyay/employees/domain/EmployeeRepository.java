package io.github.bhuwanupadhyay.employees.domain;

import java.util.Optional;

import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.Employee;

import static io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeId;

public interface EmployeeRepository {

	Optional<Employee> find(EmployeeId employeeId);

	Employee save(Employee employee);

	class EmployeeDataException extends RuntimeException {

		public EmployeeDataException(Throwable cause) {
			super(cause);
		}
	}
}
