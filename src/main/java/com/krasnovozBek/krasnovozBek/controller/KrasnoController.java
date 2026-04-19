package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.impl.CheckDaoImpl;
import com.krasnovozBek.krasnovozBek.dao.impl.CustomerCardDaoImpl;
import com.krasnovozBek.krasnovozBek.dao.impl.EmployeeDaoImpl;
import com.krasnovozBek.krasnovozBek.domain.Check;
import com.krasnovozBek.krasnovozBek.domain.CustomerCard;
import com.krasnovozBek.krasnovozBek.domain.Employee;

import com.krasnovozBek.krasnovozBek.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/anna")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class KrasnoController {

    private final JdbcTemplate jdbc;

    KrasnoController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/sql1")
    public List<Map<String, Object>> sql1Anna() {
        log.info("in sql 1 by anna");
        return jdbc.queryForList(
                "SELECT c.category_number, c.category_name, SUM(s.product_number) as products_sold " +
                "FROM Sale s JOIN Store_Product sp ON s.upc = sp.upc " +
                            "JOIN Product p ON p.id_product = sp.id_product " +
                            "JOIN Category c ON p.category_number = c.category_number " +
                "GROUP BY c.category_name, c.category_number"
        );

    }


    @GetMapping("/sql2/{category_number}")
    public List<Check> sql2Anna(@PathVariable String category_number) {
        log.info("in sql 2 by anna");
        return jdbc.query(
        "SELECT * " +
        "FROM \"Check\" c WHERE NOT EXISTS (" +
              "SELECT id_product " +
                "FROM Product p " +
                "WHERE category_number = ? AND NOT EXISTS (" +
                        "SELECT * " +
                        "FROM Sale s JOIN Store_Product sp ON s.upc = sp.upc " +
                        "WHERE s.check_number = c.check_number AND " +
                        "sp.id_product = p.id_product" +
                        ")" +
                ")",
                new CheckDaoImpl.CheckRowMapper(),
                category_number);
    }

//    "" +
//            "FROM Product p JOIN Store_Product sp ON p.id_product = sp.id_product WHERE UPC IN (" +
//            "SELECT UPC " +
//            "FROM Store_Product WHERE product_id IN (" +
//            "SELECT product_id FROM Product p " +
//            "WHERE category_number = ? AND product_id IN (" +
//            "SELECT product_id FROM Store_Product " +
//            "WHERE UPC NOT IN (SELECT UPC FROM Sale s2 WHERE s.UPC = " +
//            ")" +
//            ")",
}
