package com.krasnovozBek.krasnovozBek.dao.impl;


import com.krasnovozBek.krasnovozBek.dao.SaleDao;
import com.krasnovozBek.krasnovozBek.domain.Sale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class SaleDaoImpl implements SaleDao {
    private final JdbcTemplate jdbcTemplate;

    SaleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Sale sale) {
        jdbcTemplate.update(
                "INSERT INTO Sale (UPC, check_number, product_number, selling_price) VALUES (?,?,?,?)",
                sale.getUpc(), sale.getCheck_number(),
                sale.getProduct_number(), sale.getSelling_price()
        );
        jdbcTemplate.update(
                "UPDATE Store_Product SET products_number = products_number - ? WHERE UPC = ?",
                sale.getProduct_number(), sale.getUpc()
        );
    }
    @Override
    public Integer getTotalQuantityByUpcAndPeriod(String upc, LocalDateTime from, LocalDateTime to) {
        String sql = """
            SELECT COALESCE(SUM(s.product_number), 0)
            FROM Sale s
            JOIN "Check" c ON s.check_number = c.check_number
            WHERE s.UPC = ?
              AND c.print_date BETWEEN ? AND ?
            """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, upc, from, to);
        return result != null ? result : 0;
    }
}