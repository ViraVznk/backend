package com.krasnovozBek.krasnovozBek.dao.impl;

import com.krasnovozBek.krasnovozBek.dao.StoreProductDao;
import com.krasnovozBek.krasnovozBek.domain.Check;
import com.krasnovozBek.krasnovozBek.domain.StoreProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StoreProductDaoImpl implements StoreProductDao {

    private final JdbcTemplate jdbcTemplate;

    StoreProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(StoreProduct storeProduct) {
        jdbcTemplate.update(
                "INSERT INTO Store_Product (UPC, UPC_prom, id_product, selling_price, products_number, promotional_product) VALUES (?,?,?,?,?,?)",
                storeProduct.getUpc(), storeProduct.getUpc_prom(), storeProduct.getId_product(),
                storeProduct.getSelling_price(), storeProduct.getProducts_number(), storeProduct.getPromotional()
        );
    }

    @Override
    public void update(StoreProduct storeProduct) {
        jdbcTemplate.update(
                "UPDATE Store_Product SET UPC_prom=?, id_product=?, selling_price=?, products_number=?, promotional_product=? WHERE UPC=?",
                storeProduct.getUpc_prom(), storeProduct.getId_product(),
                storeProduct.getSelling_price(), storeProduct.getProducts_number(), storeProduct.getPromotional(),
                storeProduct.getUpc()
        );
    }

    @Override
    public void delete(String upc) {
        jdbcTemplate.update("DELETE FROM Store_Product WHERE UPC=?", upc);
    }

    @Override
    public List<StoreProduct> findAllSortByName() {
        return jdbcTemplate.query(
                "SELECT sp.* FROM Store_Product sp JOIN Product p ON sp.id_product=p.id_product ORDER BY p.product_name",
                new StoreProductRowMapper()
        );
    }

    @Override
    public List<StoreProduct> findAllSortByQuantity() {
        return jdbcTemplate.query(
                "SELECT * FROM Store_Product ORDER BY products_number",
                new StoreProductRowMapper()
        );
    }

    @Override
    public List<StoreProduct> findAllNotPromSortByQuantity() {
        return jdbcTemplate.query(
                "SELECT * FROM Store_Product WHERE promotional_product=false ORDER BY products_number",
                new StoreProductRowMapper()
        );
    }

    @Override
    public List<StoreProduct> findAllNotPromSortByName() {
        return jdbcTemplate.query(
                "SELECT sp.* FROM Store_Product sp JOIN Product p ON sp.id_product=p.id_product WHERE sp.promotional_product=false ORDER BY p.product_name",
                new StoreProductRowMapper()
        );
    }

    @Override
    public List<StoreProduct> findAllPromSortByQuantity() {
        return jdbcTemplate.query(
                "SELECT * FROM Store_Product WHERE promotional_product=true ORDER BY products_number",
                new StoreProductRowMapper()
        );
    }

    @Override
    public List<StoreProduct> findAllPromSortByName() {
        return jdbcTemplate.query(
                "SELECT sp.* FROM Store_Product sp JOIN Product p ON sp.id_product=p.id_product WHERE sp.promotional_product=true ORDER BY p.product_name",
                new StoreProductRowMapper()
        );
    }

    @Override
    public List<Map<String, Object>> findByUpcWithDetails(String upc) {
        return jdbcTemplate.queryForList(
                "SELECT sp.UPC, sp.selling_price, sp.products_number, p.product_name, p.characteristics " +
                        "FROM Store_Product sp JOIN Product p ON sp.id_product = p.id_product " +
                        "WHERE sp.UPC = ? ",
                upc
        );
    }

    @Override
    public List<Map<String, Object>> findAllWithDetails() {
        return jdbcTemplate.queryForList(
                "SELECT sp.UPC, sp.selling_price, sp.products_number, p.product_name " +
                        "FROM Store_Product sp JOIN Product p ON sp.id_product = p.id_product "
        );
    }

    @Override
    public List<StoreProduct> findByUpc(String upc) {
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM Store_Product " +
                        "WHERE UPC = ? ",
                new StoreProductRowMapper(),
                upc
        );
    }



    private static final class StoreProductRowMapper implements RowMapper<StoreProduct> {
        @Override
        public StoreProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            return StoreProduct.builder()
                    .upc(rs.getString("UPC"))
                    .upc_prom(rs.getString("UPC_prom"))
                    .id_product(rs.getInt("id_product"))
                    .selling_price(rs.getBigDecimal("selling_price"))
                    .products_number(rs.getInt("products_number"))
                    .promotional(rs.getBoolean("promotional_product"))
                    .build();
        }
    }
}
