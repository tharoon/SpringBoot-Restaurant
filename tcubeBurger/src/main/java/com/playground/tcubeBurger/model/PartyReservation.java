package com.playground.tcubeBurger.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author tharoon
 *
 */

@Document(collection = "partyReservations")
@Data
public class PartyReservation {

	@Id
	private String id;
	private String partyType;
	private String price;
	private String status;
}
