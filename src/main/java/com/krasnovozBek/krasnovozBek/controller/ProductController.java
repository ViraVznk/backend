package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.CategoryDao;
import com.krasnovozBek.krasnovozBek.dao.ProductDao;
import com.krasnovozBek.krasnovozBek.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final JdbcTemplate jdbc;

    private final ProductDao productDao;

    ProductController(JdbcTemplate jdbc, ProductDao productDao) {
        this.jdbc = jdbc;
        this.productDao = productDao;
    }

    @GetMapping("/api/products")
    public List<Product> getAllProducts() {
        log.info("in products");
        return productDao.findAllSortByName();
    }

}
