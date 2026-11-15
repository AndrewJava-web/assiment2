package org.route.service;


import org.route.model.Brand;
import org.route.model.Product;
import org.route.repository.BrandRepository;
import org.route.repository.CategoryRepository;
import org.route.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, CategoryService categoryService, BrandService brandService) {
        this.productRepository = productRepository;

        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    public List<Product> getAvailableProducts() {
        List<Product> availableProducts = new ArrayList<>();
        for (Product p : productRepository.findAll()) {
            if (p.getInStock() > 0)
                availableProducts.add(p);
        }
        return availableProducts;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public boolean isProductExist(String sku) {
        return productRepository.findBySku(sku) != null;
    }
    public Map<String,String>validateProduct(Product product) {
        Map<String,String> errors = new HashMap<>();
        if(!categoryService.isSubCategory(product.getCategory())){
            errors.put("category","Sub-Category");
        }
        String brandSlug=product.getBrandSlug();
        if(brandSlug != null && !brandSlug.isBlank()){
            boolean ok=brandService.findBySlug(brandSlug)
                    .map(Brand::isActive)
                    .orElse(false);
            if(!ok){
                errors.put("brand","Brand");
            }
        }
        return errors;


    }
    public Map<String,String> getNamesBySlug(){
        Map<String,String> names= new HashMap<>();
        for (Product p : productRepository.findAll()) {
            names.put(p.getSku(),p.getName());
        }
        return names;

    }
}
