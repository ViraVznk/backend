package com.krasnovozBek.krasnovozBek.controller;


import com.krasnovozBek.krasnovozBek.dao.SaleDao;
import com.krasnovozBek.krasnovozBek.domain.Sale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/sales")
public class SaleController {
    private final SaleDao saleDao;

    SaleController(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Sale sale) {
        log.info("creating sale for check {}", sale.getCheck_number());
        try {
            saleDao.create(sale);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Помилка: перевірте дані продажу");
        }
    }

    @GetMapping("/quantity")
    public Integer getTotalQuantity(
            @RequestParam String upc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        log.info("getting total quantity for upc {} from {} to {}", upc, from, to);
        return saleDao.getTotalQuantityByUpcAndPeriod(upc, from, to);
    }
}