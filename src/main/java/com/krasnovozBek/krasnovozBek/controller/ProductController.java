package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.ProductDao;
import com.krasnovozBek.krasnovozBek.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
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

    @PostMapping("/api/products")
    public ResponseEntity<String> create(@RequestBody Product product) {
        log.info("creating product {}", product.getProduct_name());
        try {
            productDao.create(product);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка: перевірте чи існує вказана категорія");
        }
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Product product) {
        log.info("updating product {}", id);
        try {
            productDao.update(product);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка оновлення товару");
        }
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        log.info("deleting product {}", id);
        try {
            productDao.delete(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Неможливо видалити товар — він використовується в чеках");
        }
    }
}