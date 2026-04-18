package com.krasnovozBek.krasnovozBek.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private String id_employee;
    private String empl_surname;
    private String empl_name;
    private String empl_patronymic;
    private String empl_role;// "Manager" // "Cashier"
    private BigDecimal salary;
    private LocalDate date_of_birth;
    private LocalDate date_of_start;
    private String phone_number;
    private String city;
    private String street;
    private String zip_code;

}
