package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Check;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CheckDao {
    void create(Check check);
    void delete(String checkNumber);

    List<Check> findAllByPeriodByEmployee(String idEmployee, LocalDateTime from, LocalDateTime to);
    List<Check> findAllByDayByEmployee(String idEmployee, LocalDateTime day);
    List<Check> findAllByPeriod(LocalDateTime from, LocalDateTime to);
    List<Check> findAll();
    BigDecimal getTotalSumByEmployeeAndPeriod(String idEmployee, LocalDateTime from, LocalDateTime to);
    BigDecimal getTotalSumByPeriod(LocalDateTime from, LocalDateTime to);
    Optional<Check> findByNumber(String checkNumber);
    List<Map<String, Object>> findSalesByCheckNumber(String checkNumber);

}
