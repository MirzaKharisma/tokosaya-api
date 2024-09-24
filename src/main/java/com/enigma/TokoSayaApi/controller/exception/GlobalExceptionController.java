package com.enigma.TokoSayaApi.controller.exception;

import com.enigma.TokoSayaApi.model.dto.response.CommonResponse;
import com.enigma.TokoSayaApi.utils.exceptions.AuthenticationException;
import com.enigma.TokoSayaApi.utils.exceptions.EmailAlreadyExistException;
import com.enigma.TokoSayaApi.utils.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
           String fieldName = ((FieldError) error).getField();
           String errorMessage = error.getDefaultMessage();
           errors.put(fieldName, errorMessage);
        });

        CommonResponse<Map<String, String>> commonResponse = CommonResponse.<Map<String, String>>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Form must be valid")
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<CommonResponse<String>> handleEmailAlreadyExistsException(EmailAlreadyExistException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponse);
    }
}
