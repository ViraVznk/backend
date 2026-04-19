package com.krasnovozBek.krasnovozBek.dao.impl;
import com.krasnovozBek.krasnovozBek.dao.CustomerCardDao;
import com.krasnovozBek.krasnovozBek.domain.CustomerCard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerCardDaoImpl implements CustomerCardDao {
    private final JdbcTemplate jdbcTemplate;
    public CustomerCardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(CustomerCard customerCard) {
        jdbcTemplate.update("INSERT INTO Customer_Card (card_number ,cust_surname,cust_name,cust_patronymic,phone_number,city,street,zip_code,percent) VALUES (?,?,?,?,?,?,?,?,?)",
                customerCard.getCard_number(),
                customerCard.getCust_surname(),
                customerCard.getCust_name(),
                customerCard.getCust_patronymic(),
                customerCard.getPhone_number(),
                customerCard.getCity(),
                customerCard.getStreet(),
                customerCard.getZip_code(),
                customerCard.getPercent()
        );

    }

    @Override
    public void update(CustomerCard customerCard) {
        jdbcTemplate.update("UPDATE Customer_Card SET cust_surname = ?, cust_name = ?, cust_patronymic = ?, phone_number = ?, city = ?, street = ?, zip_code = ?, percent = ? WHERE card_number = ?",
                customerCard.getCust_surname(),
                customerCard.getCust_name(), customerCard.getCust_patronymic(),
                customerCard.getPhone_number(), customerCard.getCity(),
                customerCard.getStreet(), customerCard.getZip_code(),
                customerCard.getPercent(),customerCard.getCard_number()
        );

    }

    @Override
    public void delete(String cardNumber) {
        jdbcTemplate.update("DELETE FROM Customer_Card WHERE card_number=?", cardNumber);
    }

    @Override
    public List<CustomerCard> findAllSortBySurname() {
        return jdbcTemplate.query(
                "SELECT * FROM Customer_Card ORDER BY cust_surname",
                new CustomerCardRowMapper()
        );
    }

    @Override
    public List<CustomerCard> findHasPercentSortBySurname(Integer percent) {
        return jdbcTemplate.query(
                "SELECT * FROM Customer_Card WHERE percent > 0 ORDER BY cust_surname",
                new CustomerCardRowMapper()
        );
    }

    @Override
    public Optional<CustomerCard> findBySurname(String surname) {
        List<CustomerCard> result = jdbcTemplate.query(
                "SELECT * FROM Customer_Card WHERE cust_surname=?",
                new CustomerCardRowMapper(),
                surname
        );
        return result.stream().findFirst();
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
