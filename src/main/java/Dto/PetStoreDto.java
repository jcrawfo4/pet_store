package Dto;

import Pet.Store.Entity.Customer;
import Pet.Store.Entity.Employee;
import Pet.Store.Entity.PetStore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PetStoreDto {
    Integer petStoreId;
    String petStoreName;
    String petStoreAddress;
    String petStoreCity;
    String petStoreState;
    String petStoreZip;
    String petStorePhone;
    Set<EmployeeDto> employees = new HashSet<EmployeeDto>();
    Set<CustomerDto> customers = new HashSet<>();

    public PetStoreDto(PetStore petStore) {
        this.petStoreId = petStore.getPetStoreId();
        this.petStoreName = petStore.getPetStoreName();
        this.petStoreAddress = petStore.getPetStoreAddress();
        this.petStoreCity = petStore.getPetStoreCity();
        this.petStoreState = petStore.getPetStoreState();
        this.petStoreZip = petStore.getPetStoreZip();
        this.petStorePhone = petStore.getPetStorePhone();

        for(Employee employee: petStore.getEmployees()){
            this.employees.add(new EmployeeDto(employee));
        }
        for(Customer customer: petStore.getCustomers()){
            this.customers.add(new CustomerDto(customer));
        }
    }
}
