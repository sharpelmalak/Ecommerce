package iti.jets.ecommerce.dto;

import iti.jets.ecommerce.models.Admin;

public class AdminConverter {
    
     /* Utility method to map Admin entity to AdminDTO */
    public static AdminDTO convertToDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setName(admin.getName());
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setHireDate(admin.getHireDate());
        return adminDTO;
    }
}
