// package iti.jets.ecommerce.services;

// import iti.jets.ecommerce.dto.*;
// import iti.jets.ecommerce.models.Admin;
// import iti.jets.ecommerce.repositories.AdminRepository;
// import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.Optional;

// @Service
// public class AdminService {

//     @Autowired
//     private AdminRepository adminRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     /* Get admin profile by ID */
//     public AdminDTO getAdminProfile(int adminId) {
//         Admin admin = adminRepository.findById(adminId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));
//         return AdminConverter.convertToDTO(admin);
//     }

//     /* Update admin profile */
//     public AdminDTO updateAdminProfile(int adminId, AdminDTO adminDTO) {
//         Admin admin = adminRepository.findById(adminId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));

//         admin.setName(adminDTO.getName());
//         admin.setUsername(adminDTO.getUsername());
//         admin.setEmail(adminDTO.getEmail());
//         admin.setHireDate(adminDTO.getHireDate());

//         Admin updatedAdmin = adminRepository.save(admin);
//         return AdminConverter.convertToDTO(updatedAdmin);
//     }

//     /* Change admin password */
//     public void changePassword(int adminId, PasswordChangeDTO passwordChangeDTO) {
//         Admin admin = adminRepository.findById(adminId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));

//         // Verify current password
//         if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), admin.getPassword())) {
//             throw new IllegalArgumentException("Current password is incorrect");
//         }

//         // Set the new password after encoding it
//         admin.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
//         adminRepository.save(admin);
//     }

// }
