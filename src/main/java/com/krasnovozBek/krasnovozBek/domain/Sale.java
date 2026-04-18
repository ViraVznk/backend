package com.krasnovozBek.krasnovozBek.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale {

    private String upc;
    private String check_number;
    private Integer product_number;
    private BigDecimal selling_price;

}
