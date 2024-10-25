package iti.jets.ecommerce.mappers;

import iti.jets.ecommerce.dto.AdminDTO;
import iti.jets.ecommerce.models.Admin;

public class AdminMapper {
    
    /* Utility method to map Admin entity to AdminDTO */
    public static AdminDTO convertToDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setName(admin.getName());
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setPassword(admin.getPassword());
        adminDTO.setHireDate(admin.getHireDate());
        return adminDTO;
    }
}
