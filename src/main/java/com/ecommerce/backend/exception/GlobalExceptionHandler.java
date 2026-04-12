package com.ecommerce.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.ecommerce.backend.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<?> handleOtherExceptions(Exception ex) {
    //     return ResponseEntity
    //             .status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body(ex.getMessage());
    // }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
    return ResponseEntity.badRequest()
            .body(ApiResponse.error(ex.getMessage()));
}
}


// AKIAZQ3DPIQEDZADMNOX
// atlW4fJyjDanU5/MPXyVqi09cvyhMC2EK6XyTEBa