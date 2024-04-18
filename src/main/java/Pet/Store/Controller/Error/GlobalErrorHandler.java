package Pet.Store.Controller.Error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    public Map<String, String> handleNoSuchElementException(NoSuchElementException e) {
        log.error("No such element found", e);
        return Map.of("message", e.toString());
    }
}
