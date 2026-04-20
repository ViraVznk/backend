package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.CheckDao;
import com.krasnovozBek.krasnovozBek.domain.Check;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/checks")
public class CheckController {
    private final JdbcTemplate jdbc;


    private final CheckDao checkDao;
    CheckController(JdbcTemplate jdbc, CheckDao checkDao) {
        this.jdbc = jdbc;
        this.checkDao = checkDao;
    }

    @GetMapping
    public List<Check> getAllByPeriod(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        if (from == null) from = LocalDateTime.of(2000, 1, 1, 0, 0);
        if (to == null) to = LocalDateTime.now();

        return checkDao.findAllByPeriod(from, to);
    }

    @GetMapping("/all")
    public List<Check> getAll() {
        return checkDao.findAll();
    }

    @GetMapping("/employee/{id}")
    public List<Check> getAllByEmployeeAndPeriod(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        log.info("getting checks for employee {} from {} to {}", id, from, to);
        return checkDao.findAllByPeriodByEmployee(id, from, to);
    }

    @GetMapping("/employee/{id}/day")
    public List<Check> getAllByEmployeeAndDay(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime day) {
        log.info("getting checks for employee {} on day {}", id, day);
        return checkDao.findAllByDayByEmployee(id, day);
    }

    @GetMapping("/{checkNumber}")
    public ResponseEntity<Check> getByNumber(@PathVariable String checkNumber) {
        log.info("getting check {}", checkNumber);
        return checkDao.findByNumber(checkNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sum")
    public BigDecimal getTotalSum(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        log.info("getting total sum from {} to {}", from, to);
        return checkDao.getTotalSumByPeriod(from, to);
    }

    @GetMapping("/sum/employee/{id}")
    public BigDecimal getTotalSumByEmployee(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        log.info("getting total sum for employee {} from {} to {}", id, from, to);
        return checkDao.getTotalSumByEmployeeAndPeriod(id, from, to);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Check check) {
        log.info("creating check {}", check.getCheck_number());
        try {
            checkDao.create(check);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка: перевірте дані чеку");
        }
    }

    @DeleteMapping("/{checkNumber}")
    public ResponseEntity<String> delete(@PathVariable String checkNumber) {
        log.info("deleting check {}", checkNumber);
        try {
            checkDao.delete(checkNumber);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Неможливо видалити чек");
        }
    }
    @GetMapping("/{checkNumber}/sales")
    public List<Map<String, Object>> getSalesByCheck(@PathVariable String checkNumber) {
        log.info("getting sales for check {}", checkNumber);
        return checkDao.findSalesByCheckNumber(checkNumber);

    }
}