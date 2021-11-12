package com.dogcue.customer.common;

import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;

/**
 *
 * @author Mario
 * 
 * API standard exception response in JSON format. Returns following keys: 
 * 1. timestamp - Exception timestamp
 * 2. status - HTTP status code
 * 2. type - URI identifier
 * 3. error - Brief human-readable error explanation
 * 4. detail - Complete human-readable error explanation
 *
 */
public class ApiExceptionResponse {

    @ApiModelProperty(notes = "Exception timestamp", name = "timestamp", required = true)
    private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

    @ApiModelProperty(notes = "HTTP status code", name = "status", required = true)
    private int status;

    @ApiModelProperty(notes = "URI identifier", name = "type", required = true)
    private String type = "/errors/uncategorized";

    @ApiModelProperty(notes = "URI identifier", name = "type", required = true)
    private long code;

    @ApiModelProperty(notes = "Brief human-readable error explanation", name = "error", required = true)
    private String error;

    @ApiModelProperty(notes = "Complete human-readable error explanation", name = "detail", required = true)
    private String detail;

    public ApiExceptionResponse(int status, long code, String type, String error, String detail) {
        this.status = status;
        this.code = code;
        this.type = type;
        this.error = error;
        this.detail = detail;
    }

    public ApiExceptionResponse(int status, long code, String title, String detail) {
        this.status = status;
        this.code = code;
        this.error = title;
        this.detail = detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public long getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getDetail() {
        return detail;
    }

}
