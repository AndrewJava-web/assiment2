package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Brand;
import org.route.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;


    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }
    @GetMapping
    public String getBrands(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "brands";
    }
    @GetMapping("/new")
    public String getNewBrand(Model model) {
        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        return "brandForm";
    }
    @PostMapping
    public String saveBrand(@ModelAttribute("brand") @Valid Brand brand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "brandForm";
        }
        try {
            brandService.save(brand);

        }catch(Exception e) {
            bindingResult.reject("brand.error");
        }
        return "redirect:/brands";
    }
}
