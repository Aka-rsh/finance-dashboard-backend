package com.finance.dashboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.finance.dashboard.dto.response.ResponseStructureDto;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handling Resource Not Found (e.g., Record ID not found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setData("Failure");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 2. Handling Access Denied (The "Lock" part of your project)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleAccessDenied(AccessDeniedException ex) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.FORBIDDEN.value());
        response.setMessage("Access Denied: You do not have permission to perform this action.");
        response.setData("Security Failure");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // 3. Handling Validation Errors (e.g., negative amount entered)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleValidation(MethodArgumentNotValidException ex) {
        String errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation Failed: " + errorDetails);
        response.setData("Invalid Input");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 4. Handling Financial Logic Errors
    @ExceptionHandler(FinancialOperationException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleFinancialException(FinancialOperationException ex) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setData("Transaction Failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // 5. Handles Inactive User errors
    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleUserInactive(UserInactiveException ex) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.FORBIDDEN.value()); // 403
        response.setMessage(ex.getMessage());
        response.setData("Failure: Account Deactivated");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // 6. Handling Generic Exceptions (The "Catch-All")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructureDto<String>> handleGeneral(Exception ex) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("An unexpected error occurred: " + ex.getMessage());
        response.setData("System Failure");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
       // 7. Handling Generic RuntimeExceptions (The "Catch-All")
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ResponseStructureDto<String>> handleRuntime(RuntimeException e) {
            ResponseStructureDto<String> response = new ResponseStructureDto<>();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}