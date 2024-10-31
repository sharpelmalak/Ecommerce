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
import iti.jets.ecommerce.models.Promotion;
import iti.jets.ecommerce.services.ProductService;
import iti.jets.ecommerce.services.PromotionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {

    @Autowired
    private  ProductService productService; 

    @Autowired
    private PromotionService  promotionService;


    @GetMapping(value = {"/home","/"})
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
        List<Promotion> promotions = promotionService.getActivePromotions();
        
        System.out.println("heree"+promotions.size());

        for (Promotion promo : promotions) {
            System.out.println("Country: " + promo.getCountry() + ", Value: " + promo.getDiscountPercentage() + 
                            ", Start Date: " + promo.getStartDate() + ", End Date: " + promo.getEndDate());
        }

        
        model.addAttribute("first8Products", first8Products);
        model.addAttribute("last8Products", last8Products);
        model.addAttribute("promotions", promotions);
        return "index";
    }
}

