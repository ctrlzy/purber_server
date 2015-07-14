/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.rest.dto;

public class LocationDto {
    
    private double longitude;
    
    private double latitude;
    
    public LocationDto() {
        super();        
        this.latitude = 0;
        this.longitude = 0;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }   
    

}
