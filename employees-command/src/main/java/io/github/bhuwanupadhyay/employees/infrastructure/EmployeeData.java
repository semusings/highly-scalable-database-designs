package io.github.bhuwanupadhyay.employees.infrastructure;


import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.Employee;
import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeId;
import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeName;

import static io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeStatus;

class EmployeeData {

	private final Long id;

	private final Employee employee;

	public EmployeeData(Long id,
			String employeeId,
			String employeeName,
			String employeeStatus) {
		this.id = id;
		this.employee = new Employee(new EmployeeId(employeeId), new EmployeeName(employeeName), EmployeeStatus.valueOf(employeeStatus));
	}

	public Employee getEmployee() {
		return employee;
	}
}