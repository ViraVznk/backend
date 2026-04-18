package com.krasnovozBek.krasnovozBek.dao.impl;


import com.krasnovozBek.krasnovozBek.domain.CustomerCard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerCardDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    CustomerCardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class CustomerCardRowMapper implements RowMapper<CustomerCard> {
        @Override
        public CustomerCard mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CustomerCard.builder()
                    .card_number(rs.getString("card_number"))
                    .cust_surname(rs.getString("cust_surname"))
                    .cust_name(rs.getString("cust_name"))
                    .cust_patronymic(rs.getString("cust_patronymic"))
                    .phone_number(rs.getString("phone_number"))
                    .city(rs.getString("city"))
                    .street(rs.getString("street"))
                    .zip_code(rs.getString("zip_code"))
                    .percent(rs.getInt("percent"))
                    .build();
        }
    }
}
