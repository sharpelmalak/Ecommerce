package iti.jets.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {

    @Autowired
    private  ProductService productService; 

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        String redirectUrl = (String) request.getSession().getAttribute("redirectAfterLogin");
        if (redirectUrl != null) {
                // Remove the URL from the session to prevent future redirects
                request.getSession().removeAttribute("redirectAfterLogin");
                // Redirect to the original requested URL
                response.sendRedirect(redirectUrl);
        }
        List<ProductDTO> first8Products = productService.getFirst8Products();
        List<ProductDTO> last8Products = productService.getLast8Products();

        model.addAttribute("first8Products", first8Products);
        model.addAttribute("last8Products", last8Products);
        return "index";
    }
   
}

