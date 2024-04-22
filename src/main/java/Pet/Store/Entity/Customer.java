package Pet.Store.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "customerId")
public class Customer {

    @JsonIdentityReference(alwaysAsId = true) // otherwise first ref will be used    @EqualsAndHashCode.Exclude
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
