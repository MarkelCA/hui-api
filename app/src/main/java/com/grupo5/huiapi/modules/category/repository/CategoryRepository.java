package com.grupo5.huiapi.modules.category.repository;

import com.grupo5.huiapi.modules.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category FROM Category category WHERE name = ?1")
    Optional<Category> findCategoryByName(String name);

    @Query("SELECT category FROM Category category WHERE parent_id = ?1")
    List<Category> findSubCategories(Long id);
}
