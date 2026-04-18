package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.Category;

import java.util.List;

public interface CategoryDao {

    void create(Category category);
    void update(int number, Category category);
    void delete(int categoryNumber);
    List<Category> selectAllCategories();

}
