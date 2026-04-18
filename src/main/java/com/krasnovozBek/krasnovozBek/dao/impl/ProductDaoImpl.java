package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.ProductDao;
import com.krasnovozBek.krasnovozBek.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Product product) {
        jdbcTemplate.update(
                "INSERT INTO Product (id_product, category_number, product_name, manufacturer, characteristics) VALUES (?,?,?,?,?)",
                product.getId_product(),
                product.getCategory_number(),
                product.getProduct_name(),
                product.getManufacturer(),
                product.getCharacteristics()
        );
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(
                "UPDATE Product SET id_product=?, category_number=?, product_name=?, manufacturer=?, characteristics=? WHERE id_product=?",
                product.getId_product(),
                product.getCategory_number(),
                product.getProduct_name(),
                product.getManufacturer(),
                product.getCharacteristics(),
                product.getId_product()
        );
    }


    @Override
    public void delete(Integer productId) {
        jdbcTemplate.update("DELETE FROM Product WHERE id_product=?", productId);
    }

    @Override
    public List<Product> findAllSortByName() {
        return jdbcTemplate.query(
                "SELECT * FROM Product p JOIN Category c ON p.category_number = c.category_number ORDER BY product_name",
                new ProductRowMapper()
        );
    }

    @Override
    public List<Product> findByCategorySortByName(Integer categoryNumber) {
        return jdbcTemplate.query(
                "SELECT * FROM Product WHERE category_number=? ORDER BY product_name",
                new ProductRowMapper(),
                categoryNumber
        );
    }

    @Override
    public List<Product> findByName(String productName) {
        return jdbcTemplate.query(
                "SELECT * FROM Product WHERE LOWER(product_name) LIKE LOWER(CONCAT('%', ?, '%'))",
                new ProductRowMapper(),
                productName
        );

    }

    private static final class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Product.builder()
                    .id_product(rs.getInt("id_product"))
                    .category_number(rs.getInt("category_number"))
                    .product_name(rs.getString("product_name"))
                    .manufacturer(rs.getString("manufacturer"))
                    .characteristics(rs.getString("characteristics"))
                    .build();
        }
    }
}
