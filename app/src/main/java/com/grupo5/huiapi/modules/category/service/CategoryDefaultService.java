package com.grupo5.huiapi.modules.category.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.exceptions.CustomException;
import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.category.repository.CategoryRepository;
import com.grupo5.huiapi.modules.event.entity.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("CategoryDefaultService")
public class CategoryDefaultService implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public CategoryDefaultService(CategoryRepository repository, ObjectMapper objectMapper) {
        this.categoryRepository = repository;
        this.objectMapper = objectMapper;
    }

    public List<Category> getAll() {return categoryRepository.findAll(); }

    public Category get(Long id) throws EntityNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            throw new EntityNotFoundException(EntityType.CATEGORY);
        return optionalCategory.get();
    }

    public Set<Category> getCategoriesFromNode(JsonNode categoriesNode) throws EntityNotFoundException {
        JsonNode categories = categoriesNode.get("categories");
        List<Integer> categoryIds = objectMapper.convertValue(categories, ArrayList.class);
        return getCategoriesFromIds(categoryIds);
    }

    public Set<Category> getCategoriesFromIds(List<Integer> categories) throws EntityNotFoundException {
        Set<Category> ret = new HashSet<>();
        for (Integer category : categories) {
            ret.add(get( Long.valueOf(category) ));
        }
        return ret;
    }

    public List<Category> getSubCategories(Long id) {
        return categoryRepository.findSubCategories(id);
    }

    //TODO: implement this methods
    @Override
    public String insert(JsonNode object) throws CustomException, JsonProcessingException {
        return null;
    }

    @Override
    public String update(Long id, String password, JsonNode object) throws CustomException, JsonProcessingException {
        return null;
    }

    @Override
    public String delete(Long id, String password) throws CustomException {
        return null;
    }

	@Override
	public Category get(String name) throws EntityNotFoundException {
		Optional<Category> optionalCategory = categoryRepository.findCategoryByName(name);
		if (optionalCategory.isEmpty())
			throw new EntityNotFoundException(EntityType.CATEGORY);
		return optionalCategory.get();
	}
}
