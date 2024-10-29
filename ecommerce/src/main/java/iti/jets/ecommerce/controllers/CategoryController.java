package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.CategoryDTO;
import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.models.Product;
import iti.jets.ecommerce.repositories.CategoryRepository;
import iti.jets.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        return ResponseEntity.ok(categoryDTOS);
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);

        return ResponseEntity.ok(categoryDTO);
    }


}
