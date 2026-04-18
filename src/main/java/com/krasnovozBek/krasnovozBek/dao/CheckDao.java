package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Check;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CheckDao {
    void create(Check check);
    void delete(String checkNumber);

    List<Check> findAllByPeriodByEmployee(String idEmployee, LocalDateTime from, LocalDateTime to);
    List<Check> findAllByDayByEmployee(String idEmployee, LocalDateTime day);
    List<Check> findAllByPeriod(LocalDateTime from, LocalDateTime to);
    List<Check> findAllByPeriodByProduct(String product_id, LocalDateTime from, LocalDateTime to);

    Optional<Check> findByNumber(String checkNumber);

}
