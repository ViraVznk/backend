package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.impl.CheckDaoImpl;
import com.krasnovozBek.krasnovozBek.dao.impl.CustomerCardDaoImpl;
import com.krasnovozBek.krasnovozBek.domain.Check;
import com.krasnovozBek.krasnovozBek.domain.CustomerCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/vira")
@CrossOrigin(origins = {"http://localhost:3000"})
public class VozController {

    private final JdbcTemplate jdbc;

    VozController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/sql1")
    public List<Map<String, Object>> sql1Vira() {
        log.info("in sql 1 by vira");
        String sql = """
                SELECT cc.card_number, cc.cust_surname, cc.cust_name, SUM(s.product_number) as products_bought
                FROM Customer_Card cc
                JOIN "Check" ch ON cc.card_number = ch.card_number
                JOIN Sale s ON s.check_number = ch.check_number
                GROUP BY cc.card_number, cc.cust_surname, cc.cust_name 
                HAVING  SUM(s.product_number) =
                SELECT MAX(pb)
                FROM (SELECT SUM(s2.product_number) AS pb
                FROM Customer_Card cc2
                JOIN "Check" ch2 ON cc2.card_number = ch2.card_number
                JOIN Sale s2 ON s2.check_number = ch2.check_number
                GROUP BY cc2.card_number
                )
                ORDER BY products_bought DESC
                
        """;
        return jdbc.queryForList(sql);
    }

    @GetMapping("/sql2/{category_number}")
    public List<CustomerCard> sql2Vira(@PathVariable int category_number) {
        log.info("in sql 2 by vira");
        String sql = """
                SELECT *
                FROM Customer_Card cc
                WHERE cc.card_number IN (
                SELECT ch.card_number FROM "Check" ch)
                  AND NOT EXISTS (
                  SELECT 1 FROM "Check" ch
                           WHERE cc.card_number = ch.card_number
                             AND NOT EXISTS (
                             SELECT 1
                             FROM Sale s JOIN Store_Product sp ON s.upc = sp.upc
                                 JOIN Product p ON sp.id_product = p.id_product
                             WHERE s.check_number = ch.check_number
                               AND p.category_number = ?))
                """;
        return jdbc.query(sql ,new CustomerCardDaoImpl.CustomerCardRowMapper(),
                category_number);
    }


}
