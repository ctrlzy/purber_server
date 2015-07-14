package com.purber.common.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Entity implementation class for Entity: Customer
 *
 */
@Entity
@JsonRootName(value = "Customer")
@XmlRootElement(name = "Customer")
public class Customer implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	

	public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }



    public Customer() {
		super();
	}
   
}
