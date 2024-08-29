package dev.felipequeiroz.simplepms.infra.exception;

import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity tratarBusinessValidationException(BusinessValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
