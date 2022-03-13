package com.grupo5.huiapi.modules.category.service;

import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.modules.Service;
import com.grupo5.huiapi.modules.category.entity.Category;

import java.util.List;

public interface CategoryService extends Service<Category, Long> {
    List<Category> getAll();
    Category get(Long id) throws EntityNotFoundException;
    List<Category> getSubCategories(Long id);
}
