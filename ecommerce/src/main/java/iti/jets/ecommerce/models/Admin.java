package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

@Entity
@Table(name = "admin", catalog = "ecommerce")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User implements java.io.Serializable {
    
    @Temporal(TemporalType.DATE) 
    @Column(name = "hire_date", nullable = false, length = 10)
    private Date hireDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    private Set<Product> products = new HashSet<>(0);
}
