/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.rest.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseDto {
    
    private static ObjectMapper mapper;

    @Override
    public String toString() {
        if( mapper == null) mapper = new ObjectMapper();        
        try {
            String jsonString = mapper.writeValueAsString(this);
            return jsonString;            
        } catch (JsonProcessingException e) {            
            return super.toString();
        }
    }    

}
