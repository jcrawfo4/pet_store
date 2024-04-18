package Pet.Store.Service;

import Dto.PetStoreDto;
import Pet.Store.Dao.PetStoreDao;
import Pet.Store.Entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;

    public PetStoreDto savePetStore(@RequestBody PetStoreDto petStoreDto) {
        Integer petStoreId = petStoreDto.getPetStoreId();
        PetStore petStore;
        petStore = findOrCreatePetStore(petStoreId);
        petStore = copyPetStoreFields(petStore, petStoreDto);
        return new PetStoreDto(petStoreDao.save(petStore));
    }

    private PetStore findOrCreatePetStore(Integer petStoreId) {
        PetStore petStore;
        if(petStoreId != null) {
            petStore = findPetStoreById(petStoreId);
        }
        else {
            petStore = new PetStore();
        }
        return petStore;
    }

    private PetStore findPetStoreById(Integer petStoreId) {
        return petStoreDao.findById(petStoreId)
                .orElseThrow( () -> new NoSuchElementException(
                        "Pet store with ID = " + petStoreId + "was not found"));
    }

    private PetStore copyPetStoreFields(PetStore petStore, PetStoreDto petStoreDto) {
        petStore.setPetStoreId(petStoreDto.getPetStoreId());
        petStore.setPetStoreName(petStoreDto.getPetStoreName());
        petStore.setPetStoreAddress(petStoreDto.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreDto.getPetStoreCity());
        petStore.setPetStoreState(petStoreDto.getPetStoreState());
        petStore.setPetStoreZip(petStoreDto.getPetStoreZip());
        petStore.setPetStorePhone(petStoreDto.getPetStorePhone());
        return petStore;
    }
}
