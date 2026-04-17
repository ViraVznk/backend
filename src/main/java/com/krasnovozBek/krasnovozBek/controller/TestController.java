package com.krasnovozBek.krasnovozBek.controller;
import com.krasnovozBek.krasnovozBek.dao.CategoryDao;
import com.krasnovozBek.krasnovozBek.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private final JdbcTemplate jdbc;

    private final CategoryDao categoryDao;

    public TestController(JdbcTemplate jdbc, CategoryDao categoryDao) {
        this.jdbc = jdbc;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/api/categories")
    public List<Map<String, Object>> getAllCategories() {
        log.info("in categories");
        return jdbc.queryForList("SELECT * FROM Category");

    }

    @GetMapping("/api/products")
    public List<Map<String, Object>> getAllProducts() {
        log.info("in products");
        return jdbc.queryForList("SELECT * FROM Product");
    }

    @PostMapping("/api/categories")
    public ResponseEntity<Void> create(@RequestBody Category category) {
        log.info("trying to add new category");
        categoryDao.create(category);
        return ResponseEntity.ok().build();
    }


}