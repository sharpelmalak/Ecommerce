package iti.jets.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String to, String orderDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("waxenwatches@gmail.com");
        message.setTo(to);
        message.setSubject("Order Confirmation");
        message.setText("Thank you for your order!\n\nOrder Details:\n" + orderDetails);

        mailSender.send(message);
    }
}

