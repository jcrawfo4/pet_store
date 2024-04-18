package Pet.Store.Controller.Model;

import Pet.Store.Entity.Employee;
import Pet.Store.Entity.PetStore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class EmployeeDto {
    Integer employeeId;
    PetStore petStore;

    String employeeFirstName;
    String employeeLastName;
    String employeePhone;
    String employeeJobTitle;//TODO recursion? see c.ii. re: if it were static and inside  PetStoreDto

    public EmployeeDto(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.petStore = employee.getPetStore();
        this.employeeFirstName = employee.getEmployeeFirstName();
        this.employeeLastName = employee.getEmployeeLastName();
        this.employeePhone = employee.getEmployeePhone();
        this.employeeJobTitle = employee.getEmployeeJobTitle();
    }
}
