package io.github.bhuwanupadhyay.employees.infrastructure;

import java.util.Optional;
import java.util.UUID;

import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.Employee;
import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeId;
import io.github.bhuwanupadhyay.employees.domain.EmployeeRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class JdbcEmployeeRepository implements EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;

	JdbcEmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Employee> find(EmployeeId employeeId) {
		try {
			return findByRef(employeeId).map(EmployeeData::getEmployee);
		}
		catch (Exception ex) {
			throw new EmployeeDataException(ex);
		}
	}

	@Override
	public Employee save(Employee employee) {
		try {

			Optional<EmployeeId> optional = Optional.ofNullable(employee.employeeId());

			EmployeeId employeeId;

			if (optional.isEmpty()) {

				employeeId = new EmployeeId(UUID.randomUUID().toString());

				jdbcTemplate.update(
						"""
								        INSERT INTO employee
								            (emp_id, name, status) values (?, ?, ?)
								"""
						, employeeId.id(), employee.name().name(), employee.status().name());

			}
			else {

				employeeId = optional.get();

				jdbcTemplate.update("""
								    UPDATE employee SET
								    name = ?,
								    status = ?
								    where emp_id = ?
								"""
						, employee.name().name(), employee.status().name(), employeeId.id());
			}

			return findByRef(employeeId).map(EmployeeData::getEmployee).get();

		}
		catch (Exception ex) {
			throw new EmployeeDataException(ex);
		}
	}

	private Optional<EmployeeData> findByRef(EmployeeId employeeId) {
		return jdbcTemplate.query(
				"""
						SELECT e.id, e.emp_id, e.name, e.status
						FROM employee e where e.emp_id = ?
						""",
				new Object[] { employeeId.id() },
				rs -> {
					if (rs.next()) {
						return Optional.of(
								new EmployeeData(
										rs.getLong("id"),
										rs.getString("emp_id"),
										rs.getString("name"),
										rs.getString("status"))
						);
					}
					else {
						return Optional.empty();
					}
				}
		);
	}

}
