package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface ProductDao {

    void create(Product product);
    void update(Product product);
    void delete(Integer productId);
    List<Product> findAll();
    List<Product> findByCategory(Integer categoryNumber);
}
