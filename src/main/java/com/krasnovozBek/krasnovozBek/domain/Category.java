package com.krasnovozBek.krasnovozBek.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//creates equals, hashcode, to string and blabla
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //builder patterns
public class Category {

    private Integer category_number;

    private String category_name;

}
