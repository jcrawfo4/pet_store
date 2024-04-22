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
        property = "petStoreId")
public class PetStore {

    @JsonIdentityReference(alwaysAsId = true) // otherwise first ref will be used
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="pet_store_customer", joinColumns = @JoinColumn(name="pet_store_id"),
            inverseJoinColumns = @JoinColumn(name="customer_id"))
    Set<Customer> customers = new HashSet<>();

    @JsonManagedReference("employee-petstore")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Employee> employees = new HashSet<>();


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer petStoreId;
    String petStoreName;
    String petStoreAddress;
    String petStoreCity;
    String petStoreState;
    String petStoreZip;
    String petStorePhone;
}
