package iti.jets.ecommerce.repositories;


import iti.jets.ecommerce.models.Admin;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Check the actual type of user and assign authorities accordingly
        if (user instanceof Admin) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user instanceof Customer) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        } else {
            return Collections.emptyList(); // Default if no type matches
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


