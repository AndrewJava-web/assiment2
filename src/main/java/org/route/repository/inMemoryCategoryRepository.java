package org.route.repository;

import jakarta.annotation.PostConstruct;
import org.route.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class inMemoryCategoryRepository implements CategoryRepository{
    private final List<Category> categories = new ArrayList<>();
    @PostConstruct
    public void init(){
        categories.add(Category.builder()
                .name("Electronics")
                .slug("electronics")
                .displayOrder(1)
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Mobile Phones")
                .slug("mobile-phone")
                .parentSlug("electronics")
                .displayOrder(1)
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Labtops")
                .slug("laptops")
                .displayOrder(2)
                .parentSlug("electronics")
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Accessories")
                .slug("electronics-accessories")
                .parentSlug("Accessories")
                .displayOrder(3)
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Home & Kitchen")
                .slug("home-kitchen")
                .displayOrder(2)
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Cookware")
                .slug("cookware")
                .parentSlug("home-kitchen")
                .displayOrder(1)
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Lighting")
                .slug("lighting")
                .parentSlug("home-kitchen")
                .displayOrder(2)
                .active(true)
                .build());
        categories.add(Category.builder()
                .name("Fashion")
                .slug("fashion")
                .displayOrder(3)
                .active(false)
                .build());
        categories.add(Category.builder()
                .name("Men s Clothing")
                .slug("men-clothing")
                .parentSlug("fashion")
                .displayOrder(1)
                .active(true)
                .build());
    }
    @Override
    public List<Category> findAll() {
        return categories;
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return Optional.ofNullable(categories.stream().filter(c -> c.getSlug().equals(slug)).findFirst().orElse(null));
    }

    @Override
    public boolean existsBySlug(String slug) {
        return categories.stream().anyMatch(category -> category.getSlug().equals(slug));
    }

    @Override
    public void save(Category category) {
        categories.add(category);

    }
}
