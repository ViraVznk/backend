package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.CategoryDao;
import com.krasnovozBek.krasnovozBek.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    CategoryDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Category category) {
        jdbcTemplate.update("INSERT INTO Category (category_number ,category_name) VALUES (?,?)",
                category.getCategory_number(),
                category.getCategory_name()
                );
    }

    @Override
    public void update(int category_number, Category category) {
        jdbcTemplate.update("UPDATE Category SET category_number = ?, category_name = ? WHERE category_number = ?",
                category.getCategory_number(), category.getCategory_name(), category_number
        );

    }

    @Override
    public void delete(int category_number) {
        jdbcTemplate.update("DELETE FROM Category WHERE category_number = ?", category_number);
    }

    @Override
    public List<Category> selectAllCategories() {
        return jdbcTemplate.query("SELECT * FROM Category", new CategoryRowMapper());
    }


    private class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Category.builder()
                    .category_number(rs.getInt("category_number"))
                    .category_name(rs.getString("category_name"))
                    .build();
        }
    }

}
