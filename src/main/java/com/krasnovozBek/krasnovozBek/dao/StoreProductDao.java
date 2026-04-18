package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.StoreProduct;

import java.util.List;
import java.util.Optional;

public interface StoreProductDao {
    void create(StoreProduct storeProduct);
    void update(StoreProduct storeProduct);
    void delete(String upc);

    List<StoreProduct> findAllSortByName();
    List<StoreProduct> findAllSortByQuantity();

    List<StoreProduct> findAllNotPromSortByQuantity();
    List<StoreProduct> findAllNotPromSortByName();

    List<StoreProduct> findAllPromSortByQuantity();
    List<StoreProduct> findAllPromSortByName();

    Optional<StoreProduct> findByUpc(String upc);


}
