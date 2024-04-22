package Pet.Store.Controller;


import Dto.CustomerDto;
import Dto.EmployeeDto;
import Dto.PetStoreDto;
import Pet.Store.Service.PetStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/pet-store")
@RestController
@Slf4j
public class PetStoreController {
    @Autowired
    private PetStoreService petStoreService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreDto insertPetStore(@RequestBody PetStoreDto petStoreDto) {
        log.info("Creating pet store data: {}", petStoreDto);
        return petStoreService.savePetStore(petStoreDto);
    }

    @PutMapping("/{petStoreId}")
    public PetStoreDto updatePetStore(@PathVariable Integer petStoreId, @RequestBody PetStoreDto petStoreDto) {
        petStoreDto.setPetStoreId(petStoreId);
        log.info("Updating pet store with id: {}", petStoreId);
        return petStoreService.savePetStore(petStoreDto);
    }

    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeDto addEmployeeToStore(@PathVariable Integer petStoreId, @RequestBody EmployeeDto employeeDto) {
        log.info("Adding employee with id: {} to store with id: {}", petStoreId, employeeDto);
        return petStoreService.saveEmployee(petStoreId, employeeDto);
    }

    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDto addCustomerToStore(@PathVariable Integer petStoreId, @RequestBody CustomerDto customerDto) {
        log.info("Adding customer to store with id: {}", petStoreId);
        return petStoreService.saveCustomer(petStoreId, customerDto);
    }

    @GetMapping
    public List<PetStoreDto> retrieveAllPetStores() {
        log.info("Retrieving all pet stores");
        return petStoreService.retrieveAllPetStores();
    }

    @GetMapping("/{petStoreId}")
    public PetStoreDto retrievePetStoreById(@PathVariable Integer petStoreId) {
        log.info("Retrieving pet store by id: {}", petStoreId);
        return petStoreService.retrievePetStoreById(petStoreId);
    }

    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Integer petStoreId) {
        log.info("attempting to delete pet store with id: {}", petStoreId);
        petStoreService.deletePetStoreById(petStoreId);
        return Map.of("message", "Pet Store with id: " + petStoreId + " was deleted");
    }

    @DeleteMapping("/{petStoreId}/employee/{employeeId}")
    public Map<String, String> deleteEmployeeById(@PathVariable Integer petStoreId, @PathVariable Integer employeeId) {
        log.info("attempting to delete employee with id: {} from pet store with id: {}", employeeId, petStoreId);
        petStoreService.deleteEmployeeById(petStoreId, employeeId);
        return Map.of("message", "Employee with id: " + employeeId + " was deleted from pet store with id: " + petStoreId);
    }

}
