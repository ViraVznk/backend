package com.krasnovozBek.krasnovozBek.controller;

import com.krasnovozBek.krasnovozBek.dao.CustomerCardDao;
import com.krasnovozBek.krasnovozBek.domain.CustomerCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/customer-cards")
public class CustomerCardController {
    private final JdbcTemplate jdbc;

    private final CustomerCardDao customerCardDao;

    CustomerCardController(JdbcTemplate jdbc, CustomerCardDao customerCardDao) {
        this.jdbc = jdbc;
        this.customerCardDao = customerCardDao;

    }

    @GetMapping
    public List<CustomerCard> getAllSortBySurname() {
        log.info("getting all customer cards");
        return customerCardDao.findAllSortBySurname();
    }

      @GetMapping("/with-percent")
    public List<CustomerCard> getAllByPercent(@RequestParam int percent) {
        log.info("getting customer cards with percent");
        return customerCardDao.findByPercentSortBySurname(percent);
    }

    @GetMapping("/by-surname")
    public List<CustomerCard> getBySurname(@RequestParam String surname) {
        log.info("getting customer card by surname {}", surname);
        return customerCardDao.findBySurname(surname);
    }

//    @GetMapping("/{cardNumber}")
//    public ResponseEntity<CustomerCard> getByCardNumber(@PathVariable String cardNumber) {
//        log.info("getting customer card {}", cardNumber);
//        return customerCardDao.findBySurname(cardNumber)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CustomerCard customerCard) {
        log.info("creating customer card {}", customerCard.getCard_number());
        try {
            customerCardDao.create(customerCard);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка: картка з таким номером вже існує");
        }
    }

    // PUT /api/customer-cards/{cardNumber}
    @PutMapping("/{cardNumber}")
    public ResponseEntity<String> update(@PathVariable String cardNumber,
                                         @RequestBody CustomerCard customerCard) {
        log.info("updating customer card {}", cardNumber);
        try {
            customerCardDao.update(customerCard);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Помилка оновлення картки клієнта");
        }
    }

    // DELETE /api/customer-cards/{cardNumber}
    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<String> delete(@PathVariable String cardNumber) {
        log.info("deleting customer card {}", cardNumber);
        try {
            customerCardDao.delete(cardNumber);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Неможливо видалити картку — вона прив'язана до чеків");
        }
    }
}