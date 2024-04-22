package Dto;

import Pet.Store.Entity.Employee;
import Pet.Store.Entity.PetStore;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public EmployeeDto(Integer employeeId, PetStore petStore, String employeeFirstName, String employeeLastName, String employeePhone, String employeeJobTitle) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setPetStore(petStore);
        employee.setEmployeeFirstName(employeeFirstName);
        employee.setEmployeeLastName(employeeLastName);
        employee.setEmployeePhone(employeePhone);
        employee.setEmployeeJobTitle(employeeJobTitle);
    }
}
