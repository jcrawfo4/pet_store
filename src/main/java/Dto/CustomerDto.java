package Dto;

import Pet.Store.Entity.Customer;
import Pet.Store.Entity.PetStore;
import Dto.PetStoreDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class CustomerDto {

    Integer customerId;
    String customerFirstName;
    String customerLastName;
    String customerEmail;

    @Setter
    Set<PetStoreDto> petStores = new HashSet<>();

    public CustomerDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerFirstName = customer.getCustomerFirstName();
        this.customerLastName = customer.getCustomerLastName();
        this.customerEmail = customer.getCustomerEmail();
    }

    public CustomerDto(Integer customerId, String customerFirstName, String customerLastName, String customerEmail) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerFirstName(customerFirstName);
        customer.setCustomerLastName(customerLastName);
        customer.setCustomerEmail(customerEmail);
    }

}
