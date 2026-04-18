package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.CategoryDao;
import com.krasnovozBek.krasnovozBek.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryContoller {
    private final JdbcTemplate jdbc;
    private final CategoryDao categoryDao;

    CategoryContoller(JdbcTemplate jdbc, CategoryDao categoryDao) {
        this.jdbc = jdbc;
        this.categoryDao = categoryDao;
    }

    @PostMapping("/api/categories")
    public ResponseEntity<Void> create(@RequestBody Category category) {
        log.info("trying to add new category");
        categoryDao.create(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/categories")
    public List<Category> getAllCategories() {
        log.info("in categories");
        return categoryDao.selectAllCategories();
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            categoryDao.delete(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Неможливо видалити категорію — спочатку видаліть товари цієї категорії");
        }
    }

    @PutMapping("/api/categories/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Category category) {
        log.info("updating category {}", id);
        categoryDao.update(id, category);
        return ResponseEntity.ok().build();
    }

}
