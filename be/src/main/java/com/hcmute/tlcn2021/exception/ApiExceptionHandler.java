package com.hcmute.tlcn2021.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

//    @ExceptionHandler(EmailExistedException.class)
//    public ResponseEntity<String> handleEmailAlreadyExistedException(EmailExistedException exception) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }
//
//    @ExceptionHandler(UsernameExistedException.class)
//    public ResponseEntity<String> handleUsernameAlreadyExistedException(UsernameExistedException exception) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleUniqueConstraintException(SQLIntegrityConstraintViolationException exception) throws SQLIntegrityConstraintViolationException {
        if (exception.getMessage().contains("'user.unique_username_constraint'")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already existed");
        } else if (exception.getMessage().contains("'user.unique_email_constraint'")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already existed");
        }
        throw exception;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}

