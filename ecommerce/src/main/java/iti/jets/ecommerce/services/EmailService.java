package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.dto.OrderItemDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendOrderConfirmation(String to, OrderDTO orderDetails) throws MessagingException {
        //SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("waxenwatches@gmail.com");
//        message.setTo(to);
//        message.setSubject("Order Confirmation");
//        message.setText("Thank you for your order!\n\nOrder Details:\n" + orderDetails);

        String subject = "Order Confirmation";
        String toEmail = to;
        String htmlContent = buildEmailContent(orderDetails);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // 'true' indicates multipart (for HTML support)
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);  // Pass 'true' for HTML content

        mailSender.send(message);
    }

    private String buildEmailContent(OrderDTO orderDTO) {
        // Start building the HTML email content
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html>")
                .append("<head>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }")
                .append("table { width: 100%; max-width: 600px; margin: 20px auto; border-collapse: collapse; background-color: #ffffff; }")
                .append("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }")
                .append("th { background-color: #4CAF50; color: white; }")
                .append("h1 { color: #333; }")
                .append(".total { font-size: 18px; font-weight: bold; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h1>Thank you for your order!</h1>")
                .append("<p>Your order has been confirmed. Below are the details of your order:</p>")
                .append("<table>")
                .append("<tr><th>Order ID</th><td>").append(orderDTO.getOrderId()).append("</td></tr>")
                .append("<tr><th>Order Date</th><td>").append(orderDTO.getOrderDate()).append("</td></tr>")
                .append("<tr><th>Order Status</th><td>").append(orderDTO.getOrderStatus()).append("</td></tr>")
                .append("<tr><th>Shipping Cost</th><td>").append(orderDTO.getShippingCost()).append("</td></tr>")
                .append("<tr><th>Shipping Address</th><td>").append(orderDTO.getShippingAddress()).append("</td></tr>")
                .append("<tr><th>Total Price</th><td>").append(orderDTO.getTotalPrice()).append("</td></tr>")
                .append("</table>");

        // Order Items table
        emailContent.append("<h2>Order Items</h2>");
        emailContent.append("<table>");
        emailContent.append("<tr><th>Item Name</th><th>Quantity</th><th>Price</th></tr>");
        for (OrderItemDTO item : orderDTO.getOrderItems()) {
            emailContent.append("<tr>")
                    .append("<td>").append(item.getProductName()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append(item.getPrice()).append("</td>")
                    .append("</tr>");
        }
        emailContent.append("</table>");

        // Final Total Section
        emailContent.append("<p class='total'>Total: ").append(orderDTO.getTotalPrice()).append("</p>");
        emailContent.append("</body>")
                .append("</html>");

        return emailContent.toString();
    }
}

