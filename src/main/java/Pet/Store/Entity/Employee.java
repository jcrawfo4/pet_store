package Pet.Store.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer employeeId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_store_id")
    @JsonBackReference("employee-petstore")
    PetStore petStore;

    String employeeFirstName;
    String employeeLastName;
    String employeePhone;

    @Column(name = "employee_job_title")
    String employeeJobTitle;
}
