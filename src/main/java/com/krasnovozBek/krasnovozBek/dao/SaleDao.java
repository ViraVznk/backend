package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Sale;

import java.time.LocalDateTime;

public interface SaleDao {
    void create(Sale sale);
    Integer getTotalQuantityByUpcAndPeriod(String upc, LocalDateTime from, LocalDateTime to);
}
