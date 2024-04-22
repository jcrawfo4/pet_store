package Pet.Store.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {

    @JsonBackReference("customer-petstore")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
    Set<PetStore> petStores = new HashSet<>();

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer customerId;
    String customerFirstName;
    String customerLastName;
    String customerEmail;
}
