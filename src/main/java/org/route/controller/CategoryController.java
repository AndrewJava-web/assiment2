package org.route.controller;
import jakarta.validation.Valid;
import org.route.model.Category;
import org.route.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAllOrdered());
        model.addAttribute("parentNames", categoryService.getNamesBySlug());
        return "categories";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("parents", categoryService.findTopLevel());
        return "categoryForm";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult result,
                       Model model) {
        if (!result.hasErrors()) {
            categoryService.validate(category)
                    .forEach((field, msg) -> result.rejectValue(field, "error", msg));
        }
        if (result.hasErrors()) {
            model.addAttribute("parents", categoryService.findTopLevel());
            return "categoryForm";
        }
        categoryService.save(category);
        return "redirect:/categories";
    }
}