package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.EmployeeDao;
import com.krasnovozBek.krasnovozBek.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class EmployeeController {

    private final EmployeeDao employeeDao;

    EmployeeController(JdbcTemplate jdbc, EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/api/employees")
    public List<Employee> getAllEmployees() {
        log.info("in employees");
        return employeeDao.findAllSortBySurname();
    }

    @GetMapping("/api/employees/cashiers")
    public List<Employee> getCashiers() {
        log.info("in cashiers");
        return employeeDao.findAllCashiersSortBySurname();
    }

    @PostMapping("/api/employees")
    public ResponseEntity<String> create(@RequestBody Employee employee) {
        log.info("creating employee {}", employee.getEmpl_surname());
        try {
            employeeDao.create(employee);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка: перевірте дані працівника");
        }
    }

    @PutMapping("/api/employees/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Employee employee) {
        log.info("updating employee {}", id);
        try {
            employeeDao.update(employee);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка оновлення працівника");
        }
    }

    @DeleteMapping("/api/employees/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        log.info("deleting employee {}", id);
        try {
            employeeDao.delete(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Неможливо видалити працівника — він прив'язаний до чеків");
        }
    }
}