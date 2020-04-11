package io.github.bhuwanupadhyay.employees.domain;

import java.util.List;
import java.util.Optional;

import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeView;

public interface EmployeeRepository {

	Optional<EmployeeView> find(String employeeId);

	List<EmployeeView> list();

	class EmployeeDataException extends RuntimeException {

		public EmployeeDataException(Throwable cause) {
			super(cause);
		}
	}
}
