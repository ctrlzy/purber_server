/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.rest.dto;

import javax.ws.rs.core.Response.Status;

public class ErrorInfoDto {
    

    private String errorMessage;
    private Status status;
    
    
    public ErrorInfoDto(String errorMessage) {
        super();
        this.errorMessage = errorMessage;        
    }
    
    public ErrorInfoDto(String errorMessage, Status status) {
        super();
        this.errorMessage = errorMessage;
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }    
    
}
