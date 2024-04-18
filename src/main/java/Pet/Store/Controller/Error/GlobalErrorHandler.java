package Pet.Store.Controller.Error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String, String> handleNoSuchElementException(NoSuchElementException e) {
        log.error("No such element found {}", e.toString());
        return Map.of("message", e.toString());
    }
}
