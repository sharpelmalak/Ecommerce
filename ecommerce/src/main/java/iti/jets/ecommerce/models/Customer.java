package iti.jets.ecommerce.models;
// Generated Sep 2, 2024, 5:22:05 PM by Hibernate Tools 6.5.1.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer generated by hbm2java
 */
@Entity
@Table(name="customer"
    ,catalog="ecommerce"
)
public class Customer  extends User implements java.io.Serializable {

     private Date birthdate;
     private String job;
     private float creditLimit;
     private String address;
     private Set<CartItem> cartItems = new HashSet<CartItem>(0);
     private Set<Category> categories = new HashSet<Category>(0);
     private Set<Product> products = new HashSet<Product>(0);
     private Set<Order> orders = new HashSet<Order>(0);

    public Customer() {
    }

	
    public Customer(Date birthdate, float creditLimit, String address) {
        this.birthdate = birthdate;
        this.creditLimit = creditLimit;
        this.address = address;
    }
    public Customer( Date birthdate, String job, float creditLimit, String address) {
       this.birthdate = birthdate;
       this.job = job;
       this.creditLimit = creditLimit;
       this.address = address;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="birthdate", nullable=false, length=10)
    public Date getBirthdate() {
        return this.birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    
    @Column(name="job", length=45)
    public String getJob() {
        return this.job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }

    
    @Column(name="credit_limit", nullable=false, precision=12)
    public float getCreditLimit() {
        return this.creditLimit;
    }
    
    public void setCreditLimit(float creditLimit) {
        this.creditLimit = creditLimit;
    }

    
    @Column(name="address", nullable=false, length=100)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="customer")
    public Set<CartItem> getCartItems() {
        return this.cartItems;
    }
    
    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="customer_has_interests_category", catalog="ecommerce", joinColumns = { 
        @JoinColumn(name="customer_id", nullable=false) }, inverseJoinColumns = {
        @JoinColumn(name="category_id", nullable=false) })
    public Set<Category> getCategories() {
        return this.categories;
    }
    
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="customer_wishlist", catalog="ecommerce", joinColumns = { 
        @JoinColumn(name="customer_id", nullable=false) }, inverseJoinColumns = {
        @JoinColumn(name="product_id", nullable=false) })
    public Set<Product> getProducts() {
        return this.products;
    }
    
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="customer")
    public Set<Order> getOrders() {
        return this.orders;
    }
    
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

}


