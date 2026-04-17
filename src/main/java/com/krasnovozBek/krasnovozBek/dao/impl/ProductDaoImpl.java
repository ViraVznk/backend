package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.ProductDao;
import com.krasnovozBek.krasnovozBek.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Integer productId) {

    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<Product> findByCategory(Integer categoryNumber) {
        return null;
    }
}
