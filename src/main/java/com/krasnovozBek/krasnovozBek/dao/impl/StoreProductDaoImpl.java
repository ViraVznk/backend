package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.StoreProductDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StoreProductDaoImpl implements StoreProductDao {

    private final JdbcTemplate jdbcTemplate;

    StoreProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
