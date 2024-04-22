package Pet.Store.Service;

import Dto.CustomerDto;
import Dto.EmployeeDto;
import Dto.PetStoreDto;
import Pet.Store.Dao.CustomerDao;
import Pet.Store.Dao.EmployeeDao;
import Pet.Store.Dao.PetStoreDao;
import Pet.Store.Entity.Customer;
import Pet.Store.Entity.Employee;
import Pet.Store.Entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;

    @Transactional(readOnly = false)
    public PetStoreDto savePetStore(@RequestBody PetStoreDto petStoreDto) {
        Integer petStoreId = petStoreDto.getPetStoreId();
        PetStore petStore = findOrCreatePetStore(petStoreId);
        copyPetStoreFields(petStore, petStoreDto);
        return new PetStoreDto(petStoreDao.save(petStore));
    }

    private void copyPetStoreFields(PetStore petStore, PetStoreDto petStoreDto) {
        petStore.setPetStoreId(petStoreDto.getPetStoreId());
        petStore.setPetStoreName(petStoreDto.getPetStoreName());
        petStore.setPetStoreAddress(petStoreDto.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreDto.getPetStoreCity());
        petStore.setPetStoreState(petStoreDto.getPetStoreState());
        petStore.setPetStoreZip(petStoreDto.getPetStoreZip());
        petStore.setPetStorePhone(petStoreDto.getPetStorePhone());
    }

    private PetStore findOrCreatePetStore(Integer petStoreId) {
        if(petStoreId == null){
            return new PetStore();
        } else {
            return findPetStoreById(petStoreId);
        }
    }

    private PetStore findPetStoreById(Integer petStoreId) {
        return petStoreDao.findById(petStoreId)
                .orElseThrow( () -> new NoSuchElementException(
                        "Pet store with ID = " + petStoreId + "was not found"));
    }

    @Transactional(readOnly = false)
    public EmployeeDto saveEmployee(Integer petStoreId, EmployeeDto employeeDto) {
        PetStore petStore = findPetStoreById(petStoreId);
        Integer employeeId = employeeDto.getEmployeeId();
        Employee employee = findOrCreateEmployee(petStoreId, employeeId);
        copyEmployeeFields(employee, employeeDto);
        employee.setPetStore(petStore);
        petStore.getEmployees().add(employee);
        Employee databaseEmployee = employeeDao.save(employee);
        return new EmployeeDto(databaseEmployee);
    }

    private void copyEmployeeFields(Employee employee, EmployeeDto employeeDto) {
        employee.setPetStore(employeeDto.getPetStore());
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setEmployeeFirstName(employeeDto.getEmployeeFirstName());
        employee.setEmployeeLastName(employeeDto.getEmployeeLastName());
        employee.setEmployeePhone(employeeDto.getEmployeePhone());
        employee.setEmployeeJobTitle(employeeDto.getEmployeeJobTitle());
    }

    private Employee findOrCreateEmployee(Integer petStoreId, Integer employeeId) {
        if(employeeId == null){
            return new Employee();
        } else {
            return findEmployeeById(employeeId, petStoreId);
        }
    }

    private Employee findEmployeeById(Integer employeeId, Integer petStoreId){
        Employee employee;
        if(Objects.equals(petStoreId, employeeId)){
            employee = employeeDao.findById(employeeId)
                    .orElseThrow( () -> new NoSuchElementException(
                            "Employee with ID = " + employeeId + "was not found"));
        }
        else {
            throw new IllegalArgumentException("Employee ID must match Pet Store ID");
        }
        return employee;
    }

    @Transactional(readOnly = false)
    public CustomerDto saveCustomer(Integer petStoreId, CustomerDto customerDto) {
        PetStore petStore = findPetStoreById(petStoreId);
        Integer customerId = customerDto.getCustomerId();
        Customer customer = findOrCreateCustomer(petStoreId, customerId);
        copyCustomerFields(customer, customerDto);
        for(PetStore ps: customer.getPetStores()){
            customer.getPetStores().add(ps);
        }
        petStore.getCustomers().add(customer);
        Customer databaseCustomer = customerDao.save(customer);
        CustomerDto result = new CustomerDto(databaseCustomer);
        //convert the get list of PetStore objects to a stream
        Stream<PetStore> petStoreStream = databaseCustomer.getPetStores().stream();
        //Transform each PetStore object into a PetStoreDto object
        Stream<PetStoreDto> petStoreDtoStream = petStoreStream.map(PetStoreDto::new);
        //collect PetStoreDto objects into a list
        Set<PetStoreDto> petStoreDtoSet = petStoreDtoStream.collect(Collectors.toSet());
        //set the list of PetStoreDto objects in the result object
        result.setPetStores(petStoreDtoSet);

        return result;
    }

    private void copyCustomerFields(Customer customer, CustomerDto customerDto) {
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setCustomerFirstName(customerDto.getCustomerFirstName());
        customer.setCustomerLastName(customerDto.getCustomerLastName());
        customer.setCustomerEmail(customerDto.getCustomerEmail());
    }

    private Customer findOrCreateCustomer(Integer petStoreId, Integer customerId) {
        if(customerId == null){
            return new Customer();
        } else {
            return findCustomerById(customerId, petStoreId);
        }
    }
//Note:  that customer and pet store have a many-to-many relationship. This means that a Customer
// object has a List of PetStore objects. This means that, in the method findCustomerById(), you will
// need to loop through the list of PetStore objects looking for the pet store with the given pet store ID.
// If not found, throw an IllegalArgumentException.
    private Customer findCustomerById(Integer customerId, Integer petStoreId) {
        Customer customer = new Customer();
        for(PetStore petStore: customer.getPetStores()){
            if(Objects.equals(petStore.getPetStoreId(), customerId)){
                customer = customerDao.findById(customerId)
                        .orElseThrow( () -> new NoSuchElementException(
                                "Customer with ID = " + customerId + "was not found"));
            }
            else {
                throw new IllegalArgumentException("Customer ID must match Pet Store ID");
            }
        }
        return customer;
    }


    @Transactional(readOnly = true)
    public List<PetStoreDto> retrieveAllPetStores() {
        List<PetStoreDto> petStoreDtos = new LinkedList<>();
        for(PetStore petStore:  petStoreDao.findAll()){
            PetStoreDto petStoreDto = new PetStoreDto(petStore);
            petStoreDto.getEmployees().clear();
            petStoreDto.getCustomers().clear();
            petStoreDtos.add(petStoreDto);
        }
        return petStoreDtos;
    }

    @Transactional(readOnly = true)
    public PetStoreDto retrievePetStoreById(Integer petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        return new PetStoreDto(petStore);
    }

    public void deletePetStoreById(Integer petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        petStoreDao.delete(petStore);
    }

    public void deleteEmployeeById(Integer petStoreId, Integer employeeId) {
        Employee employee = findEmployeeById(employeeId, petStoreId);
        employeeDao.delete(employee);
    }
}
