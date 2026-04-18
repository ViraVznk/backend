package com.krasnovozBek.krasnovozBek.controller;
import com.krasnovozBek.krasnovozBek.dao.CategoryDao;
import com.krasnovozBek.krasnovozBek.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

//    @GetMapping("/api/categories")
//    public List<Category> getAllCategories() {
//        log.info("in categories");
//        return categoryDao.find();
//    }
//
//    @GetMapping("/api/products")
//    public List<Map<String, Object>> getAllProducts() {
//        log.info("in products");
//        return jdbc.queryForList("SELECT * FROM Product");
//    }
//
//    @PostMapping("/api/categories")
//    public ResponseEntity<Void> create(@RequestBody Category category) {
//        log.info("trying to add new category");
//        categoryDao.create(category);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/api/categories/{id}")
//    public ResponseEntity<String> delete(@PathVariable int id) {
//        try {
//            categoryDao.delete(id);
//            return ResponseEntity.ok().build();
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.badRequest().body("Неможливо видалити категорію — спочатку видаліть товари цієї категорії");
//        }
//    }
//    @PutMapping("/api/categories/{id}")
//    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Category category) {
//        log.info("updating category {}", id);
//        categoryDao.update(id, category);
//        return ResponseEntity.ok().build();
//    }
}