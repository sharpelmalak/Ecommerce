package iti.jets.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private float price;
    private int quantity;
    private String description;
    private String image; // Modified from byte[] image to String image
    private int categoryId; // Added field for category ID
    private boolean isDeleted;
    private String brand;
    // Watch-specific fields
    private String material;
    private int caseDiameter;
    private String waterResistance;
    private String gender;
}
