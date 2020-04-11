package io.github.bhuwanupadhyay.employees.infrastructure;

import io.github.bhuwanupadhyay.employees.domain.EmployeeDomain.EmployeeView;
import io.github.bhuwanupadhyay.employees.domain.EmployeeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JdbcEmployeeRepository implements EmployeeRepository {

    private final JdbcTemplate jdbc;

    JdbcEmployeeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<EmployeeView> find(String employeeId) {
        try {
            return jdbc.query(
                    """
                            SELECT e.id, e.emp_id, e.name, e.status 
                            FROM employee e where e.emp_id = ?
                            """,
                    new Object[]{employeeId},
                    rs -> {
                        if (rs.next()) {
                            return Optional.of(
                                    new EmployeeView(
                                            rs.getString("emp_id"),
                                            rs.getString("name"),
                                            rs.getString("status"))
                            );
                        } else {
                            return Optional.empty();
                        }
                    }
            );
        } catch (Exception ex) {
            throw new EmployeeDataException(ex);
        }
    }

    @Override
    public List<EmployeeView> list() {
        try {
            return jdbc.query(
                    """
                            SELECT e.id, e.emp_id, e.name, e.status 
                            FROM employee e
                            """,
                    (rs, r) -> new EmployeeView(
                            rs.getString("emp_id"),
                            rs.getString("name"),
                            rs.getString("status"))
            );
        } catch (Exception ex) {
            throw new EmployeeDataException(ex);
        }
    }


}
