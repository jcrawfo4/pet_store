package Pet.Store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "Pet.Store")
public class PetStoreApplication{

    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
    }
}