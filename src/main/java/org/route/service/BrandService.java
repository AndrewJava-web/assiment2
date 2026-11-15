package org.route.service;

import org.jspecify.annotations.Nullable;
import org.route.model.Brand;
import org.route.model.Category;
import org.route.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
    public Optional<Brand> findBySlug(String slug) {
        return Optional.ofNullable(brandRepository.findBySlug(slug));
    }
    public void save(Brand brand) {

        if(brandRepository.existsByName(brand.getName())) {
            throw new RuntimeException("the name is exist");
        }
        if(brandRepository.existsBySlug(brand.getSlug())) {
            throw new RuntimeException("the slug is exist");
        }
        brandRepository.save(brand);

    }
    public List<Brand> findActive(){
        return brandRepository.findAll().stream()
                .filter(Brand::isActive)
                .toList();
    }
    public Map<String, String> validate(Brand brand) {
        Map<String, String> errors = new HashMap<>();
        if (brandRepository.existsByName(brand.getName())) {
            errors.put("name", "Brand name already exists");
        }
        if (brandRepository.existsBySlug(brand.getSlug())) {
            errors.put("slug", "Slug already exists");
        }
        return errors;
    }
    public Map<String, String> getNamesBySlug() {
        return brandRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Brand::getSlug,
                        Brand::getName,
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
    }


}
