package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.CheckDao;
import com.krasnovozBek.krasnovozBek.domain.Check;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CheckDaoImpl implements CheckDao {
    private final JdbcTemplate jdbcTemplate;

    CheckDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Check check) {
        jdbcTemplate.update(
                "INSERT INTO \"Check\" (check_number,id_employee,card_number,print_date,sum_total,vat) VALUES (?,?,?,?,?,?) ",
                check.getCheck_number(), check.getId_employee(),
                check.getCard_number(), check.getPrint_date(),
                check.getSum_total(), check.getVat()
        );
    }

    @Override
    public void delete(String checkNumber) {
        jdbcTemplate.update("DELETE FROM \"Check\" WHERE check_number=?", checkNumber);
    }

    @Override
    public List<Check> findAllByPeriodByEmployee(String idEmployee, LocalDateTime from, LocalDateTime to) {
        String sql = """
        SELECT * FROM "Check" c
        WHERE c.id_employee = ?
          AND c.print_date BETWEEN ? AND ?
        ORDER BY c.print_date
        """;
        return jdbcTemplate.query(sql, new CheckRowMapper(), idEmployee, from, to);
    }

    @Override
    public List<Check> findAllByPeriod(LocalDateTime from, LocalDateTime to) {
        String sql = """
        SELECT * FROM "Check" c
        WHERE c.print_date BETWEEN ? AND ?
        ORDER BY c.print_date
        """;
        return jdbcTemplate.query(sql, new CheckRowMapper(), from, to);
    }

    @Override
    public BigDecimal getTotalSumByEmployeeAndPeriod(String idEmployee, LocalDateTime from, LocalDateTime to) {
        String sql = """
            SELECT SUM(c.sum_total)
            FROM "Check" c
            WHERE c.id_employee = ?
              AND c.print_date BETWEEN ? AND ?
            """;
        BigDecimal result = jdbcTemplate.queryForObject(sql, BigDecimal.class, idEmployee, from, to);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalSumByPeriod(LocalDateTime from, LocalDateTime to) {
        String sql = """
            SELECT SUM(c.sum_total)
            FROM "Check" c
            WHERE c.print_date BETWEEN ? AND ?
            """;
        BigDecimal result = jdbcTemplate.queryForObject(sql, BigDecimal.class, from, to);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public Optional<Check> findByNumber(String checkNumber) {
        String sql = """
        SELECT * FROM "Check" c
        WHERE c.check_number = ?
        """;
        return jdbcTemplate.query(sql, new CheckRowMapper(), checkNumber)
                .stream()
                .findFirst();
    }
    @Override
    public List<Check> findAllByDayByEmployee(String idEmployee, LocalDateTime day) {
        String sql = """
            SELECT DISTINCT * FROM "Check" c
            WHERE c.id_employee = ?
              AND CAST(c.print_date AS DATE) = CAST(? AS DATE)
            ORDER BY c.print_date
            """;
        return jdbcTemplate.query(sql, new CheckRowMapper(), idEmployee, day);
    }

    @Override
    public List<Map<String, Object>> findSalesByCheckNumber(String checkNumber) {
        return jdbcTemplate.queryForList(
                "SELECT s.UPC, p.product_name, s.product_number, s.selling_price " +
                        "FROM Sale s " +
                        "JOIN Store_Product sp ON s.UPC = sp.UPC OR s.UPC = sp.UPC_prom " +
                        "JOIN Product p ON sp.id_product = p.id_product " +
                        "WHERE s.check_number = ? " +
                        "GROUP BY s.UPC, p.product_name, s.product_number, s.selling_price",
                checkNumber);
    }
    @Override
    public List<Check> findAll (){
        String sql = """
                SELECT * FROM "Check" c
                """;
        return jdbcTemplate.query(sql, new CheckRowMapper());
    }

    public static final class CheckRowMapper implements RowMapper<Check> {
        @Override
        public Check mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Check.builder()
                    .check_number(rs.getString("check_number"))
                    .id_employee(rs.getString("id_employee"))
                    .card_number(rs.getString("card_number"))
                    .print_date(rs.getTimestamp("print_date").toLocalDateTime())
                    .sum_total(rs.getBigDecimal("sum_total"))
                    .vat(rs.getBigDecimal("vat"))
                    .build();
        }
    }

}