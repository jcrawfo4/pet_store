package Pet.Store.Controller;


import Dto.PetStoreDto;
import Pet.Store.Service.PetStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
