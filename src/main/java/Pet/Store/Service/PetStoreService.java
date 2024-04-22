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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


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
        if (petStoreId == null) {
            return new PetStore();
        } else {
            return findPetStoreById(petStoreId);
        }
    }

    private PetStore findPetStoreById(Integer petStoreId) {
        return petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException(
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
        if (employeeId == null) {
            return new Employee();
        } else {
            return findEmployeeById(employeeId, petStoreId);
        }
    }

    private Employee findEmployeeById(Integer employeeId, Integer petStoreId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with ID = " + employeeId + "was not found"));
        if (employee.getPetStore().getPetStoreId() != petStoreId) {
            throw new IllegalArgumentException("Employee with Id= )" + employeeId + " does not have a relationship with Pet Store with ID = " + petStoreId);
        }
        return employee;
    }

    @Transactional(readOnly = false)
    public CustomerDto saveCustomer(Integer petStoreId, CustomerDto customerDto) {
        PetStore petStore = findPetStoreById(petStoreId);
        Integer customerId = customerDto.getCustomerId();
        Customer customer = findOrCreateCustomer(petStoreId, customerId);
        copyCustomerFields(customer, customerDto);
        for (PetStore ps : customer.getPetStores()) {
            customer.getPetStores().add(ps);
        }
        customer.getPetStores().add(petStore);
        petStore.getCustomers().add(customer);
        Customer databaseCustomer = customerDao.save(customer);
        CustomerDto customerAsCustomerDto = new CustomerDto(databaseCustomer);
        return customerAsCustomerDto;
    }

    private void copyCustomerFields(Customer customer, CustomerDto customerDto) {
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setCustomerFirstName(customerDto.getCustomerFirstName());
        customer.setCustomerLastName(customerDto.getCustomerLastName());
        customer.setCustomerEmail(customerDto.getCustomerEmail());
    }

    private Customer findOrCreateCustomer(Integer petStoreId, Integer customerId) {
        if (customerId == null) {
            return new Customer();
        } else {
            return findCustomerById(customerId, petStoreId);
        }
    }

    private Customer findCustomerById(Integer customerId, Integer petStoreId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Customer with ID = " + customerId + "was not found"));
        boolean found = false;
        for (PetStore ps : customer.getPetStores()) {
            if (ps.getPetStoreId() == petStoreId) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Customer with Id= )" + customerId + " does not have a relationship with Pet Store with ID = " + petStoreId);
        }
        return customer;
    }


    @Transactional(readOnly = true)
    public List<PetStoreDto> retrieveAllPetStores() {

        List<PetStore> petStores = petStoreDao.findAll();
        List<PetStoreDto> result = new LinkedList<>();
        for (PetStore petStore : petStores) {
            PetStoreDto petStoreDto = new PetStoreDto(petStore);
            petStoreDto.getEmployees().clear();
            petStoreDto.getCustomers().clear();
            result.add(petStoreDto);
        }
        return result;
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
