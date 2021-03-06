package com.hcmute.tlcn2021.exception;

import com.hcmute.tlcn2021.payload.response.ErrorMessageResponse;
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

    @ExceptionHandler(UteForumException.class)
    public ResponseEntity<ErrorMessageResponse> handleUteForumException(UteForumException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(new ErrorMessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorMessageResponse> handleUniqueConstraintException
            (SQLIntegrityConstraintViolationException exception)
            throws SQLIntegrityConstraintViolationException {

        if (exception.getMessage().contains("'user.unique_username_constraint'")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessageResponse("Tên người dùng đã tồn tại"));

        } else if (exception.getMessage().contains("'user.unique_email_constraint'")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessageResponse("Email đã được sử dụng"));

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

