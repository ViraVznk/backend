package com.krasnovozBek.krasnovozBek.controller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private final JdbcTemplate jdbc;
    public TestController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @GetMapping
    public List<Map<String, Object>> getAll() {
        return jdbc.queryForList("SELECT * FROM Category");
    }

}