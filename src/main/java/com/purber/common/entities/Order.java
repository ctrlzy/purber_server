/*
 * Copyright (c) 2015 Nokia. All rights reserved.
 */
package com.purber.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name = "Order")
@JsonRootName(value = "Order")
@XmlRootElement(name = "Order")
public class Order {
    
    public Order() {
        super();
    }

    @Id
    @Column(name = "order_id", nullable = false, unique = true)
    private int    order_id;
    
    @Column(name = "service_type")
    private int service_type;
    
    @Column(name = "brand")
    private int brand;
    
    @Column(name = "model")
    private int model;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
    
    

}
