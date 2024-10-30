# E-commerce Application

## Overview

This project is a fully-functional e-commerce application developed using Spring Boot, Hibernate, MySQL, and JavaScript (with AJAX). The application allows users to browse products, add items to a cart, proceed to checkout, and place orders. It also includes robust user management, product management, and payment handling using Cash on Delivery (COD), Card Payment, and PayPal.

## Features

- **User Management**: Customers can sign up, log in, and manage their account information.
- **Product Catalog**: Users can browse products, filter by categories, and view detailed product information.
- **Shopping Cart**: Customers can add items to their cart, update quantities, and proceed to checkout.
- **Checkout Process**: Dynamic address and payment handling during checkout.
- **Order Management**: Customers can review past orders and track order statuses.
- **Payment Integration**:
    - **Cash on Delivery (COD)**: Customers can select COD as a payment option.
    - **Card Payment**: Customers can use saved cards or enter new card details at checkout, with real-time validation.
    - **PayPal**: Customers can make payments using PayPal during checkout.
- **Admin Functionality**: Admins can manage product listings, update stock, and process orders.

## Security Features

- **Spring Security Integration**: Provides a robust authentication and authorization framework to secure the application.
- **User Authentication**: Secure login process with password encryption to protect user credentials.
- **Role-Based Access Control**: Different user roles (e.g., admin, customer) with specific permissions to restrict access to sensitive operations.
- **Session Management**: Secure handling of user sessions, including timeout and invalidation of sessions after logout.
- **Password Recovery**: Secure password reset functionality for users to recover access to their accounts.
- **Input Validation and Sanitization**: Prevents common vulnerabilities like SQL injection and XSS (Cross-Site Scripting) through proper validation and sanitization of user inputs.

## Design

### Database Design

The following diagram represents the Entity-Relationship Diagram (ERD) for the application's database.
![Entity-Relationship Diagram](https://github.com/sharpelmalak/Ecommerce/blob/dev/Docs/erd.png)

### Project Architecture

The project follows a well-structured architecture pattern:

- **Model-View-Controller (MVC)**: The application separates concerns using the MVC pattern, where:
    - **Model**: Represents the data and business logic (Entities, Services).
    - **View**: The frontend that displays data to the user (HTML, CSS, JavaScript).
    - **Controller**: Manages user requests and responses (Spring Controllers).

- **Client-Server Architecture**: The application uses a client-server architecture, where:
    - The **client** (frontend) interacts with the **server** (backend) to fetch and manipulate data.
    - The **server** (backend) processes requests, communicates with the database, and returns responses.

- **REST API**: The application exposes RESTful endpoints to facilitate communication between the client and server. These endpoints follow standard HTTP methods (GET, POST, PUT, DELETE) for CRUD operations.


## Technologies Used

- **Backend**: Spring Boot, Hibernate, JPA, MySQL, RESTful APIs
- **Frontend**: HTML, CSS, JavaScript, Bootstrap, AJAX, jQuery
- **Database**: MySQL
- **Payment**: Cash on Delivery (COD), Card (Credit/Debit), PayPal
- **Tools & Libraries**:
   - Spring Data JPA for database interactions
   - ModelMapper for entity-DTO mapping
   - jQuery & AJAX for asynchronous frontend-backend interactions
   - Bootstrap for responsive design

## Project Structure

<details>
<summary>Project Structure</summary>

```plaintext
Ecommerce/
│
├── pom.xml                                                   // Maven Project Object Model file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── iti/
│   │   │       └── jets/
│   │   │           └── ecommerce/
│   │   │               ├── EcommerceApplication.java          // Main Spring Boot Application
│   │   │               │
│   │   │               ├── config/                            // Configuration classes
│   │   │               │   ├── CustomAuthenticationSuccessHandler.java
│   │   │               │   ├── CustomLogoutHandler.java
│   │   │               │   ├── JwtFilter.java
│   │   │               │   ├── ModelMapperConfig.java
│   │   │               │   ├── PayPalConfig.java
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── SwaggerConfig.java
│   │   │               │
│   │   │               ├── controllers/                       // Controller classes
│   │   │               │   ├── AdminController.java
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── CardController.java
│   │   │               │   ├── CartController.java
│   │   │               │   ├── CategoryController.java
│   │   │               │   ├── CheckoutController.java
│   │   │               │   ├── CustomerController.java
│   │   │               │   ├── CustomErrorController.java
│   │   │               │   ├── GoToCheckout.java
│   │   │               │   ├── HomeController.java
│   │   │               │   ├── OrderController.java
│   │   │               │   ├── PaymentController.java
│   │   │               │   ├── ProductController.java
│   │   │               │   ├── ProductDetailsController.java
│   │   │               │   ├── PromotionController.java
│   │   │               │   ├── ShopController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── dto/                                // Data Transfer Objects (DTOs)
│   │   │               │   ├── AdminDTO.java
│   │   │               │   ├── AuthDTO.java
│   │   │               │   ├── CardDTO.java
│   │   │               │   ├── CartItemDTO.java
│   │   │               │   ├── CategoryDTO.java
│   │   │               │   ├── CheckoutRequest.java
│   │   │               │   ├── CustomerAddressDTO.java
│   │   │               │   ├── CustomerDTO.java
│   │   │               │   ├── CustomerDTOAdmin.java
│   │   │               │   ├── CustomerRegistrationDTO.java
│   │   │               │   ├── LoginDTO.java
│   │   │               │   ├── OrderDTO.java
│   │   │               │   ├── OrderItemDTO.java
│   │   │               │   ├── OrderTrackingRequest.java
│   │   │               │   ├── PasswordChangeDTO.java
│   │   │               │   ├── PaymentDTO.java
│   │   │               │   ├── PaymentRequestDTO.java
│   │   │               │   ├── ProductDTO.java
│   │   │               │   ├── PromotionDTO.java
│   │   │               │   ├── RegisterDTO.java
│   │   │               │   └── UserDTO.java
│   │   │               │
│   │   │               ├── exceptions/                        // Custom Exception classes
│   │   │               │   ├── CardExpirationDateException.java
│   │   │               │   ├── CartException.java
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── InvalidCardNumberException.java
│   │   │               │   ├── InvalidCvvException.java
│   │   │               │   ├── InvalidEmailException.java
│   │   │               │   ├── InvalidPromotionException.java
│   │   │               │   ├── ItemNotAvailableException.java
│   │   │               │   ├── ProductNotFoundException.java
│   │   │               │   └── UnsupportedPaymentMethodException.java
│   │   │               │
│   │   │               ├── mappers/                            // Mapper classes
│   │   │               │   ├── AdminMapper.java
│   │   │               │   ├── CartItemMapper.java
│   │   │               │   ├── CustomerMapper.java
│   │   │               │   ├── OrderMapper.java
│   │   │               │   ├── ProductMapper.java
│   │   │               │   └── PromotionMapper.java
│   │   │               │
│   │   │               ├── models/                             // Entity models
│   │   │               │   ├── Admin.java
│   │   │               │   ├── Card.java
│   │   │               │   ├── CartItem.java
│   │   │               │   ├── CartItemId.java
│   │   │               │   ├── Category.java
│   │   │               │   ├── Customer.java
│   │   │               │   ├── Order.java
│   │   │               │   ├── OrderItem.java
│   │   │               │   ├── OrderItemId.java
│   │   │               │   ├── Payment.java
│   │   │               │   ├── Product.java
│   │   │               │   ├── Promotion.java
│   │   │               │   └── User.java
│   │   │               │
│   │   │               ├── repositories/                       // Repository interfaces
│   │   │               │   ├── AdminRepository.java
│   │   │               │   ├── CardRepository.java
│   │   │               │   ├── CartItemRepository.java
│   │   │               │   ├── CategoryRepository.java
│   │   │               │   ├── CustomerRepository.java
│   │   │               │   ├── CustomUserDetails.java
│   │   │               │   ├── OrderItemRepository.java
│   │   │               │   ├── OrderRepository.java
│   │   │               │   ├── PaymentRepository.java
│   │   │               │   ├── ProductRepository.java
│   │   │               │   ├── PromotionRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               │
│   │   │               ├── services/                           // Service classes
│   │   │               │   ├── AdminService.java
│   │   │               │   ├── AuthService.java
│   │   │               │   ├── CardService.java
│   │   │               │   ├── CartService.java
│   │   │               │   ├── CategoryService.java
│   │   │               │   ├── CustomerService.java
│   │   │               │   ├── CustomOAuth2UserService.java
│   │   │               │   ├── CustomUserDetailsService.java
│   │   │               │   ├── JWTService.java
│   │   │               │   ├── OrderService.java
│   │   │               │   ├── OrderServiceImpl.java
│   │   │               │   ├── ProductService.java
│   │   │               │   └── PromotionService.java
│   │   │               │
│   │   │               ├── services/payment/                   // Payment Service classes and strategies
│   │   │               │   ├── CardPaymentStrategy.java
│   │   │               │   ├── CODPaymentStrategy.java
│   │   │               │   ├── FawryPaymentStrategy.java
│   │   │               │   ├── PaymentService.java
│   │   │               │   ├── PaymentServiceImpl.java
│   │   │               │   ├── PaymentStrategy.java
│   │   │               │   ├── PaypalPaymentStrategy.java
│   │   │               │   └── StripePaymentStrategy.java
│   │   │               │
│   │   │               └── utils/                              // Utility classes
│   │   │                   └── ValidationUtils.java
│   │   │
│   │   ├── resources/
│   │   │   ├── static/                                           // Static assets (CSS, JS, etc.)
│   │   │   ├── templates/                                        // Thymeleaf templates
│   │   │   └── application.properties                            // Application configuration
│   │
│   └── test/                                                    // Test cases
│       └── ...

```
</details>

## Setup and Installation

### Prerequisites

- Java 17+
- Maven 3.6+
- MySQL 8.x+
- PayPal API credentials (for PayPal integration)
- IDE: IntelliJ IDEA / Eclipse / VSCode

### Steps

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/ecommerce-app.git
    cd ecommerce-app
    ```

2. Configure the MySQL database:

   Create a database in MySQL:

    ```sql
    CREATE DATABASE ecommerce_db;
    ```

   Update the `application.properties` file with your MySQL credentials:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. Configure PayPal:

   Add your PayPal Client ID and Secret to the `application.properties`:

    ```properties
    paypal.client.id=your_paypal_client_id
    paypal.client.secret=your_paypal_client_secret
    paypal.mode=sandbox  # Set to live for production
    ```

4. Install dependencies:

    ```bash
    mvn clean install
    ```

5. Run the application:

    ```bash
    mvn spring-boot:run
    ```

   Access the application:
   Navigate to `http://localhost:8080` to use the application.

# API Endpoints

<details>
<summary>Order Controller</summary>

### 1. `GET /customers/{id}`
- **Description**: Retrieve customer details by ID.

### 2. `PUT /customers/{id}`
- **Description**: Update customer details by ID.

### 3. `GET /customers/address`
- **Description**: Retrieve customer address details.

### 4. `PUT /customers/address`
- **Description**: Update customer address details.

### 5. `POST /customers/{customerId}/order`
- **Description**: Create a new order for a customer.

### 6. `POST /customers/{customerId}/order/{orderId}/pay`
- **Description**: Process payment for a specific order.

### 7. `GET /customers/{customerId}/orders`
- **Description**: Retrieve all orders for a customer.

</details>

<details>
<summary>Payment Controller</summary>

### 1. `POST /payment/process`
- **Description**: Process a payment.

</details>

<details>
<summary>Card Controller</summary>

### 1. `GET /cards/{customerId}`
- **Description**: Retrieve all cards for a specific customer.

### 2. `POST /cards/{customerId}`
- **Description**: Add a new card for a specific customer.

</details>

<details>
<summary>Test Controller</summary>

### 1. `GET /test`
- **Description**: Test endpoint (useful for health checks).

</details>

<details>
<summary>Promotion Controller</summary>

### 1. `GET /promotions/validate`
- **Description**: Validate a promotion.

</details>

<details>
<summary>Product Controller</summary>

### 1. `GET /products/search`
- **Description**: Search for products.

### 2. `GET /products/result`
- **Description**: Get search results for products.

### 3. `GET /products/products`
- **Description**: Retrieve a list of all products.

### 4. `GET /products/product/{id}`
- **Description**: Retrieve product details by ID.

### 5. `GET /products/price`
- **Description**: Retrieve products based on price criteria.

### 6. `GET /products/materials`
- **Description**: Retrieve products based on materials.

### 7. `GET /products/filter`
- **Description**: Filter products based on criteria.

### 8. `GET /products/category/{category}`
- **Description**: Retrieve products by category.

### 9. `GET /products/category/{category}/price`
- **Description**: Retrieve products by category and price criteria.

### 10. `GET /products/brands`
- **Description**: Retrieve all product brands.

### 11. `GET /products/brand/{brand}`
- **Description**: Retrieve products by brand.

</details>

<details>
<summary>Category Controller</summary>

### 1. `GET /category/categories`
- **Description**: Retrieve all product categories.

### 2. `GET /category/categories/{id}`
- **Description**: Retrieve category details by ID.

</details>


## Key Features and Components

1. **Checkout Flow**
   - **Dynamic Cart Handling**: Cart items are loaded dynamically in the checkout page using AJAX.
   - **Shipping Address**: AJAX-based address handling with form validation.
   - **Payment Methods**:
      - **Cash on Delivery (COD)**: Simple selection for COD payment.
      - **Card Payment**: Customers can use saved cards or enter new card details, with real-time validation (only numbers allowed for card number, month/year restrictions, and CVV validation).
      - **PayPal Integration**: Allows customers to pay via PayPal using the PayPal JavaScript SDK.

2. **Admin Functionality**
   - Admins can:
      - Add or remove products.
      - Update product stock.
      - Process and manage orders.

3. **Payment Integration**
   - **PayPal Integration**: Customers can choose to pay via PayPal during checkout. Once the payment is processed via PayPal, the order status is updated in the application.
   - **Card Payment**: Real-time card validation is implemented. Customers can either choose saved cards or input new card information during checkout.
   - **Cash on Delivery (COD)**: A simple payment option where the customer pays upon receiving the product.


## Future Enhancements

- **Product Review System**: Customers will be able to review and rate products.
- **Order Tracking**: Provide real-time order tracking for customers.
- **Email Notifications**: Send order confirmation and shipping updates via email.


## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/my-feature`).
3. Commit your changes (`git commit -m 'Add my feature'`).
4. Push to the branch (`git push origin feature/my-feature`).
5. Open a pull request.


## Contributers

* Ahmed Haroun
* Sharpel Malak
* Ahmed Dwidar
* Mohamed Khalid