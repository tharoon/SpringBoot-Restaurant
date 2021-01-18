package com.playground.tcubeBurger.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author tharoon
 *
 */

@Document(collection = "menus")
@Data
public class Menu {

	@Id
	private String id;
	private String itemName;
	private String category;
	private String price;
}
