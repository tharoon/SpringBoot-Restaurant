package com.playground.tcubeBurger.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author tharoon
 *
 */

@Document(collection = "locations")
@Data
public class Location {

	@Id
	private String id;
	private String streetName;
	private String city;
	private String state;
	private String zip;
	private String phone;

}
