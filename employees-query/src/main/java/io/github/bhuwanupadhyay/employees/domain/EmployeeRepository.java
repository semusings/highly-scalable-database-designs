package io.github.bhuwanupadhyay.employees.domain;

import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeView;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<EmployeeView> find(String employeeId);

    List<EmployeeView> list();

    class EmployeeDataException extends RuntimeException {

        public EmployeeDataException(Throwable cause) {
            super(cause);
        }
    }
}
