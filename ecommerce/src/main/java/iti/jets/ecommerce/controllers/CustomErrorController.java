package iti.jets.ecommerce.controllers;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Create a WebRequest wrapper from HttpServletRequest
        WebRequest webRequest = new ServletWebRequest(request);

        // Get the error attributes with options
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.BINDING_ERRORS));

        // Add error details to the model
        model.addAttribute("status", errorDetails.get("status"));
        model.addAttribute("error", errorDetails.get("error"));
        model.addAttribute("message", errorDetails.get("message"));
        model.addAttribute("path", errorDetails.get("path"));

        // Optionally handle specific status codes for custom views
        Integer status = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (status != null) {
            model.addAttribute("statusCode", status);
        }

        return "error"; // Default error view
    }

}
