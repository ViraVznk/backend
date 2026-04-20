package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.EmployeeDao;
import com.krasnovozBek.krasnovozBek.domain.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    EmployeeDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Employee employee) {
        jdbcTemplate.update(
                "INSERT INTO Employee (id_employee, empl_surname, empl_name, empl_patronymic, empl_role, salary, date_of_birth, date_of_start, phone_number, city, street, zip_code) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                employee.getId_employee(), employee.getEmpl_surname(), employee.getEmpl_name(), employee.getEmpl_patronymic(),
                employee.getEmpl_role(), employee.getSalary(), employee.getDate_of_birth(), employee.getDate_of_start(),
                employee.getPhone_number(), employee.getCity(), employee.getStreet(), employee.getZip_code()
        );
    }

    @Override
    public void update(Employee employee) {
        jdbcTemplate.update(
                "UPDATE Employee SET empl_surname=?, empl_name=?, empl_patronymic=?, empl_role=?, salary=?, date_of_birth=?, date_of_start=?, phone_number=?, city=?, street=?, zip_code=? WHERE id_employee=?",
                employee.getEmpl_surname(), employee.getEmpl_name(), employee.getEmpl_patronymic(),
                employee.getEmpl_role(), employee.getSalary(), employee.getDate_of_birth(), employee.getDate_of_start(),
                employee.getPhone_number(), employee.getCity(), employee.getStreet(), employee.getZip_code(),
                employee.getId_employee()
        );
    }

    @Override
    public void delete(String idEmployee) {
        jdbcTemplate.update("DELETE FROM Employee WHERE id_employee=?", idEmployee);
    }

    @Override
    public List<Employee> findAllSortBySurname() {
        return jdbcTemplate.query(
                "SELECT * FROM Employee ORDER BY empl_surname",
                new EmployeeRowMapper()
        );
    }

    @Override
    public List<Employee> findAllCashiersSortBySurname() {
        return jdbcTemplate.query(
                "SELECT * FROM Employee WHERE empl_role='Cashier' ORDER BY empl_surname",
                new EmployeeRowMapper()
        );
    }

    @Override
    public Optional<Employee> findBySurname(String surname) {
        List<Employee> result = jdbcTemplate.query(
                "SELECT * FROM Employee WHERE empl_surname=?",
                new EmployeeRowMapper(),
                surname
        );
        return result.stream().findFirst();
    }

    @Override
    public Optional<Employee> findById(String idEmployee) {
        List<Employee> result = jdbcTemplate.query(
                "SELECT * FROM Employee WHERE id_employee=?",
                new EmployeeRowMapper(),
                idEmployee
        );
        return result.stream().findFirst();
    }

    private static final class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Employee.builder()
                    .id_employee(rs.getString("id_employee"))
                    .empl_surname(rs.getString("empl_surname"))
                    .empl_name(rs.getString("empl_name"))
                    .empl_patronymic(rs.getString("empl_patronymic"))
                    .empl_role(rs.getString("empl_role"))
                    .salary(rs.getBigDecimal("salary"))
                    .date_of_birth(rs.getDate("date_of_birth").toLocalDate())
                    .date_of_start(rs.getDate("date_of_start").toLocalDate())
                    .phone_number(rs.getString("phone_number"))
                    .city(rs.getString("city"))
                    .street(rs.getString("street"))
                    .zip_code(rs.getString("zip_code"))
                    .build();
        }
    }

}
