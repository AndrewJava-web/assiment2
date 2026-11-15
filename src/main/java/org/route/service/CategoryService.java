package org.route.service;

import org.route.model.Category;
import org.route.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    private boolean isTopLevel(Category category){
        return category.getParentSlug() ==null || category.getParentSlug().isBlank();
    }
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public Optional<Category> findBySlug(String slug){
        return categoryRepository.findBySlug(slug);
    }
    public List<Category> findTopLevel(){
        return categoryRepository.findAll().stream()
                .filter(this::isTopLevel)
                .sorted(Comparator.comparing(Category::getDisplayOrder))
                .toList();
    }
    public List<Category> findSubCategories(String parentSlug){
        return categoryRepository.findAll().stream()
                .filter(c-> parentSlug.equals(c.getParentSlug()))
                .sorted(Comparator.comparing(Category::getDisplayOrder))
                .toList();
    }
    public List<Category> findAllCategories(){
        return categoryRepository.findAll().stream()
                .filter(c->!isTopLevel(c))
                .toList();
    }
    public boolean isSubCategory(String slug){
        return categoryRepository.findBySlug(slug)
                .map(c -> !isTopLevel(c))
                .orElse(false);
    }
    public List<Category> findAllOrdered(){
        List<Category> result = new ArrayList<>();
        for(Category c : findTopLevel()){
            result.add(c);
            result.addAll(findSubCategories(c.getSlug()));
        }
        return result;
    }
    public Map<String, String> getNamesBySlug() {
        return categoryRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Category::getSlug,
                        Category::getName,
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
    }
    public Map<String,String> validate(Category category){
        Map<String,String> errors = new HashMap<>();
        String parentSlug=category.getParentSlug()==null?"":category.getParentSlug();
        String parentName=null;
        if(categoryRepository.existsBySlug(category.getSlug())){
            errors.put("Category exists",category.getSlug());
        }
        if(!parentSlug.isEmpty()){
            Optional<Category> parent=categoryRepository.findBySlug(parentSlug);
            if(parent.isEmpty()){
                errors.put("Parent category not found",parentSlug);
            }else if(!isTopLevel(parent.get())){
                errors.put("Parent category is not top level",parentSlug);
            }else {
                parentName=parent.get().getName();
            }
        }
        if(!errors.containsKey("parentSlug")){
            boolean duplicate=categoryRepository.findAll().stream()
                    .filter(c->(c.getParentSlug()==null?"":c.getParentSlug()).equals(parentSlug))
                    .anyMatch(c->c.getName().equalsIgnoreCase(category.getName()));
            if(duplicate){
                errors.put("Duplicate category name",category.getName());
            }

        }
        return errors;
    }
    public void save(Category category){
        if(category.getParentSlug() == null){
            category.setParentSlug("");
        }
        categoryRepository.save(category);
    }
}
