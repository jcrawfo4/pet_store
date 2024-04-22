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
public class PetStore {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
            @JoinTable(name="pet_store_customer", joinColumns = @JoinColumn(name="pet_store_id"),
            inverseJoinColumns = @JoinColumn(name="customer_id"))
    Set<Customer> customers = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
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
