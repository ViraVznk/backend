package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    void create(Product product);
    void update(Product product);
    void delete(Integer productId);

    List<Product> findAllSortByName();
    List<Product> findByCategorySortByName(Integer categoryNumber);

    Optional<Product> findByName(String productName);

}
