package iti.jets.ecommerce.services;

import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.models.User;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private CustomerRepository userRepository;
    private HttpServletResponse response;
    private JWTService jwtService;

    @Autowired
    public CustomOAuth2UserService(CustomerRepository userRepository,HttpServletResponse response, JWTService jwtService) {
        this.userRepository = userRepository;
        this.response = response;
        this.jwtService = jwtService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes().keySet());
        String username = null;
        String email = oAuth2User.getAttribute("email"); // Retrieve email
        String name = oAuth2User.getAttribute("name");   // Retrieve name or other attributes

        Optional<Customer> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            Customer userEntity = new Customer();
            userEntity.setUsername(email);
            userEntity.setEmail(email);
            userEntity.setName(name);
            userEntity.setPassword(email);
            userEntity.setBirthdate(new Date(2022,10,10));
            userEntity.setAddress("DEFAULT");
            userEntity.setCity("DEFAULT");
            userEntity.setCountry("DEFAULT");
            userRepository.save(userEntity);
            username = email;
        }
        else {
            username = user.get().getUsername();
        }
        // Generate JWT
        String token = jwtService.generateToken(username,"ROLE_CUSTOMER");

        // Create and add the token cookie
        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Only if served over HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(jwtCookie);

        return oAuth2User;
    }
}

