package com.krasnovozBek.krasnovozBek.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Check {
    private String check_number;
    private String id_employee;
    private String card_number;
    private LocalDateTime print_date;
    private BigDecimal sum_total;
    private BigDecimal vat;

}
