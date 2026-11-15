package org.route.repository;

import org.route.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    public List<Category> findAll();
    public Optional<Category> findBySlug(String slug);
    boolean existsBySlug(String slug);
    public void save(Category category);
}
