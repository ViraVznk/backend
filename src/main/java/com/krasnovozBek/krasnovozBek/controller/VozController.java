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
// клієет з максимальною кількістю куплених товарів
        String sql = """
                SELECT cc.card_number, cc.cust_surname, cc.cust_name, SUM(s.product_number) as products_bought
                FROM Customer_Card cc
                JOIN "Check" ch ON cc.card_number = ch.card_number
                JOIN Sale s ON s.check_number = ch.check_number
                GROUP BY cc.card_number, cc.cust_surname, cc.cust_name
                ORDER BY products_bought DESC LIMIT 1;
                
        """;
        return jdbc.queryForList(sql);
    }
    //камтомер в яких в кожному чеку є хоча б один продукт категорії
    @GetMapping("/sql2/{category_number}")
    public List<CustomerCard> sql2Vira(@PathVariable int category_number) {
        log.info("in sql 2 by vira");

        return jdbc.query(
                "SELECT * " +
                        "FROM Customer_Card cc " +
                        "WHERE NOT EXISTS ("+
                        "SELECT 1 FROM \"Check\" ch "+
                        "WHERE cc.card_number = ch.card_number "+
                        "AND NOT EXISTS ("+
                        "SELECT 1 "+
                        "FROM Sale s JOIN Store_Product sp ON s.upc = sp.upc "+
                        "JOIN Product p ON sp.id_product = p.id_product "+
                        "WHERE s.check_number = ch.check_number "+
                        "AND p.category_number = ?))",
                new CustomerCardDaoImpl.CustomerCardRowMapper(),
                category_number);
    }


}
