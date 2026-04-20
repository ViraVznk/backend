package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.CustomerCard;

import java.util.List;
import java.util.Optional;

public interface CustomerCardDao {

    void create(CustomerCard customerCard);
    void update(CustomerCard customerCard);
    void delete(String cardNumber);

    List<CustomerCard> findAllSortBySurname();
    List<CustomerCard> findByPercentSortBySurname(Integer percent);

    List<CustomerCard> findBySurname(String surname);
}
