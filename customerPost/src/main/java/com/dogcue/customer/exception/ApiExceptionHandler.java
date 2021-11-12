package com.dogcue.customer.exception;

import com.dogcue.customer.common.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Mario
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessCustomerException.class)
    public ResponseEntity<ApiExceptionResponse> handleBusinessCustomerException(BusinessCustomerException e) {
        HttpStatus status = e.getStatus();
        ApiExceptionResponse reponse = new ApiExceptionResponse(status.value(), e.getCode(), "Validation error", e.getMessage());

        return new ResponseEntity<>(reponse, status);
    }

}
