package com.krasnovozBek.krasnovozBek.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCard {

    private String card_number;
    private String cust_surname;
    private String cust_name;
    private String cust_patronymic;
    private String phone_number;
    private String city;
    private String street;
    private String zip_code;
    private Integer percent;

}
