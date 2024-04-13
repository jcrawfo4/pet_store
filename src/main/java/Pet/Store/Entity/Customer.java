package Pet.Store.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {

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
