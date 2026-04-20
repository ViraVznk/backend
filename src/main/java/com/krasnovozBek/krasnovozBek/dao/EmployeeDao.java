package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    void create(Employee employee);
    void update(Employee employee);
    void delete(String idEmployee);

    List<Employee> findAllSortBySurname();
    List<Employee> findAllCashiersSortBySurname();

    Optional<Employee> findBySurname(String surname);
    Optional<Employee> findById(String idEmployee);
}
