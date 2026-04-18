package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Sale;

import java.util.List;

public interface SaleDao {

    void create(Sale sale);

    List<Sale> findByCheckNumber(String checkNumber);
}
