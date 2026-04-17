package com.krasnovozBek.krasnovozBek.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreProduct {
    private String upc;
    private String upc_prom;

    private Integer product_id;
    private java.math.BigDecimal selling_price;
    private Integer products_number;
    private Boolean promotional;


}
