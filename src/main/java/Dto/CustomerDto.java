package Dto;

import Pet.Store.Entity.Customer;
import Pet.Store.Entity.PetStore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CustomerDto {

    Integer customerId;
    String customerFirstName;
    String customerLastName;
    String customerEmail;
    Set<PetStore> petStores = new HashSet<>();

    public CustomerDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerFirstName = customer.getCustomerFirstName();
        this.customerLastName = customer.getCustomerLastName();
        this.customerEmail = customer.getCustomerEmail();
        for(PetStore petStore: customer.getPetStores()){
            this.petStores.add(petStore);
        }
    }
}
