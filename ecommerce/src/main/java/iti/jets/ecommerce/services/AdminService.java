package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.mappers.AdminMapper;
import iti.jets.ecommerce.models.Admin;
import iti.jets.ecommerce.mappers.*;

import iti.jets.ecommerce.repositories.AdminRepository;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class AdminService {


    private AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder){
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;


        Admin admin = adminRepository.findByEmail("admin@admin.com");
        if (admin == null) {
            admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setName("admin");
            admin.setEmail("admin@admin.com");
            admin.setHireDate(new Date(2022,1,1));
            adminRepository.save(admin);
        }


    }

    /* Get admin profile by ID */
    public AdminDTO getAdminProfile(int adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));
        return AdminMapper.convertToDTO(admin);
    }

    /* Get admin profile by ID */
    public AdminDTO getAdminProfile(String username) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + username));
        return AdminMapper.convertToDTO(admin);
    }

    /* Get all admins */
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        
        return admins.stream()
                .map(AdminMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /* Update admin profile */
    public AdminDTO updateAdminProfile(int adminId, AdminDTO adminDTO) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));

        admin.setName(adminDTO.getName());
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        admin.setHireDate(adminDTO.getHireDate());

        Admin updatedAdmin = adminRepository.save(admin);
        return AdminMapper.convertToDTO(updatedAdmin);
    }

    /* Change admin password */
    public void changePassword(int adminId, PasswordChangeDTO passwordChangeDTO) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));

        // Verify current password
        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Set the new password after encoding it
        admin.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        adminRepository.save(admin);
    }


    public AdminDTO findAdminByEmailAndPassword(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        System.out.println(admin);
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            // Assuming you have a method to convert Admin to AdminDTO
            return AdminMapper.convertToDTO(admin);
        }
        return null; // Or throw an exception if preferred
    }

}
