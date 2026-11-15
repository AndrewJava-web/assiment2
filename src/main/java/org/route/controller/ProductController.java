package org.route.controller;


import jakarta.validation.Valid;
import org.route.model.Product;
import org.route.service.CategoryService;
import org.route.service.BrandService;
import org.route.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products =  productService.getAvailableProducts();
        model.addAttribute("availableProducts", products);
        model.addAttribute("categoryNames",categoryService.getNamesBySlug());
        model.addAttribute("brandNames",brandService.getNamesBySlug());
        return "products";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());

        return "productForm";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult result,Model model) {
        if (productService.isProductExist(product.getSku())) {
            result.rejectValue("sku", "400", "sku is already exist");
        }
        if (result.hasErrors()) {
            return "redirect:/new";
        }
        productService.saveProduct(product);
        return "redirect:/products";

    }


}
