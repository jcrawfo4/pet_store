package Pet.Store.Dao;

import Pet.Store.Entity.PetStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetStoreDao extends JpaRepository<PetStore, Integer> {

}
