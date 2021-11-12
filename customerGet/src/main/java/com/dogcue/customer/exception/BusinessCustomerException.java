package com.dogcue.customer.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Mario
 */
public class BusinessCustomerException extends Exception {

    private long code;
    private String message;
    private HttpStatus status;

    public BusinessCustomerException(long code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }
    
    public BusinessCustomerException(HttpStatus status) {
        super("No message provide");
        this.message = "";
        this.status = status;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
