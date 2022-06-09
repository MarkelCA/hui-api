package com.grupo5.huiapi.modules.category.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(@Qualifier("CategoryDefaultService") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @GetMapping("{id}")
    public Category getCategory(@PathVariable("id") Long id) {
        try {
            return categoryService.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "list")
    public Set<Category> getCategories(@RequestBody List<Integer> categoryIds) {
        try {
            return categoryService.getCategoriesFromIds(categoryIds);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    @GetMapping("{id}/subcategories")
    public List<Category> getSubCategories(@PathVariable("id") Long id) {
        return categoryService.getSubCategories(id);
    }
}
