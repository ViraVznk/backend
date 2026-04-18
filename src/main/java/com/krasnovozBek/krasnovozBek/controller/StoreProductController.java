package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.StoreProductDao;
import com.krasnovozBek.krasnovozBek.domain.StoreProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class StoreProductController {

    private final StoreProductDao storeProductDao;

    StoreProductController(JdbcTemplate jdbc, StoreProductDao storeProductDao) {
        this.storeProductDao = storeProductDao;
    }

    @GetMapping("/api/store-products")
    public List<StoreProduct> getAllSortByName() {
        log.info("in store-products sorted by name");
        return storeProductDao.findAllSortByName();
    }

    @GetMapping("/api/store-products/by-quantity")
    public List<StoreProduct> getAllSortByQuantity() {
        log.info("in store-products sorted by quantity");
        return storeProductDao.findAllSortByQuantity();
    }

    @GetMapping("/api/store-products/promotional/by-quantity")
    public List<StoreProduct> getPromSortByQuantity() {
        log.info("in promotional store-products sorted by quantity");
        return storeProductDao.findAllPromSortByQuantity();
    }

    @GetMapping("/api/store-products/promotional/by-name")
    public List<StoreProduct> getPromSortByName() {
        log.info("in promotional store-products sorted by name");
        return storeProductDao.findAllPromSortByName();
    }

    @GetMapping("/api/store-products/not-promotional/by-quantity")
    public List<StoreProduct> getNotPromSortByQuantity() {
        log.info("in not-promotional store-products sorted by quantity");
        return storeProductDao.findAllNotPromSortByQuantity();
    }

    @GetMapping("/api/store-products/not-promotional/by-name")
    public List<StoreProduct> getNotPromSortByName() {
        log.info("in not-promotional store-products sorted by name");
        return storeProductDao.findAllNotPromSortByName();
    }

    @GetMapping("/api/store-products/{upc}")
    public ResponseEntity<?> getByUpc(@PathVariable String upc) {
        log.info("finding store-product by upc {}", upc);
        return storeProductDao.findByUpc(upc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/store-products")
    public ResponseEntity<String> create(@RequestBody StoreProduct storeProduct) {
        log.info("creating store-product {}", storeProduct.getUpc());
        try {
            storeProductDao.create(storeProduct);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка: перевірте чи існує вказаний товар або UPC вже зайнятий");
        }
    }

    @PutMapping("/api/store-products/{upc}")
    public ResponseEntity<String> update(@PathVariable String upc, @RequestBody StoreProduct storeProduct) {
        log.info("updating store-product {}", upc);
        try {
            storeProductDao.update(storeProduct);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка оновлення товару в магазині");
        }
    }

    @DeleteMapping("/api/store-products/{upc}")
    public ResponseEntity<String> delete(@PathVariable String upc) {
        log.info("deleting store-product {}", upc);
        try {
            storeProductDao.delete(upc);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Неможливо видалити — товар присутній у чеках");
        }
    }
}