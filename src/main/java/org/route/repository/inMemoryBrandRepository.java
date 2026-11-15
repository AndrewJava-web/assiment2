package org.route.repository;

import jakarta.annotation.PostConstruct;
import org.route.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class inMemoryBrandRepository implements BrandRepository{
    List<Brand> myBrands=new ArrayList<Brand>();
    @PostConstruct
    public void init(){
        myBrands.add(Brand.builder()
                .name("Samsung")
                .slug("samsung")
                .active(true)
                .build());
        myBrands.add(Brand.builder()
                .name("Asus")
                .slug("asus")
                .active(true)
                .build());
        myBrands.add(Brand.builder()
                .name("Anker")
                .slug("anker")
                .active(true)
                .build());
        myBrands.add(Brand.builder()
                .name("Tefal")
                .slug("tefal")
                .active(true)
                .build());
        myBrands.add(Brand.builder()
                .name("Old brand")
                .slug("old brand")
                .active(false)
                .build());
    }
    @Override
    public List<Brand> findAll() {
        return myBrands;
    }

    @Override
    public Brand findBySlug(String slug) {
        for(Brand brand:myBrands){
            if(brand.getSlug().equals(slug)){
                return brand;
            }
        }
        return null;
    }

    @Override
    public void save(Brand brand) {
        myBrands.add(brand);

    }

    @Override
    public boolean existsBySlug(String slug) {
        return myBrands.stream().anyMatch(b -> b.getSlug().equals(slug));
    }

    @Override
    public boolean existsByName(String name) {
        return myBrands.stream().anyMatch(b -> b.getName().equals(name));
    }
}
