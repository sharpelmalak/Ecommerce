package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.CategoryDTO;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
import iti.jets.ecommerce.models.Category;
import iti.jets.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {


    @Autowired
    private CategoryRepository CategoryRepository;


    // Get all products
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = CategoryRepository.findAllByIsDeletedFalse();

        /*  Convert List<Product> to List<ProductDTO> */
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return categoryDTOS;
    }
    public CategoryDTO getCategoryById(int id) {
        Category category = CategoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category Not Found with ID: "+id));

        return convertToDTO(category);
    }
    
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(category.getName());
        dto.setId(category.getId());
        dto.setImage(category.getImage());
        dto.setItemsCount(category.getProducts().size());
        return dto;
    }


}
