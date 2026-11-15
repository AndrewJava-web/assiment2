package org.route.repository;

import org.route.model.Brand;

import java.util.List;

public interface BrandRepository {
    public List<Brand> findAll();
    public Brand findBySlug(String slug);
    public void save(Brand brand);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
