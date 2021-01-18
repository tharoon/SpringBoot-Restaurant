package com.playground.tcubeBurger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playground.tcubeBurger.repository.PartyReservationRepository;

import lombok.extern.log4j.Log4j2;

import com.playground.tcubeBurger.model.PartyReservation;

/**
 * 
 * @author tharoon
 *
 */

@RestController
@RequestMapping("/api")
@Log4j2
public class PartyReservationController {

	@Autowired
	PartyReservationRepository partyReservationRepository;

	@GetMapping("/partyreservations")
	public ResponseEntity<List<PartyReservation>> getAllPartyReservations(
			@RequestParam(required = false) String partyType) {
		try {
			List<PartyReservation> partyReservationList = new ArrayList<PartyReservation>();
			if (partyType == null) {
				log.info(
						"FROM com.playground.tcubeBurger.controller.PartyReservationController/getAllPartyReservations() ---> partyType is null");
				partyReservationRepository.findAll().forEach(partyReservationList::add);
			} else {
				log.info(
						"FROM com.playground.tcubeBurger.controller.PartyReservationController/getAllPartyReservations() ---> partyType is not null");
				partyReservationRepository.findByPartyTypeContaining(partyType).forEach(partyReservationList::add);
			}
			if (partyReservationList.isEmpty()) {
				log.info(
						"FROM com.playground.tcubeBurger.controller.PartyReservationController/getAllPartyReservations() ---> partyReservationList is empty");
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(partyReservationList, HttpStatus.OK);

		} catch (Exception e) {
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/getAllPartyReservations() ---> "
							+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/partyreservations/{id}")
	public ResponseEntity<PartyReservation> getPartyReservations(@PathVariable("id") String id) {
		Optional<PartyReservation> menu = partyReservationRepository.findById(id);
		if (menu.isPresent()) {
			return new ResponseEntity<>(menu.get(), HttpStatus.OK);
		} else {
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/getPartyReservations() ---> No party found for the id: "
							+ id);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/partyreservations")
	public ResponseEntity<PartyReservation> createPartyReservations(@RequestBody PartyReservation partyReservation) {
		try {
			PartyReservation newPartyReservation = partyReservationRepository.save(partyReservation);
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/createPartyReservations() ---> Saving Party");
			return new ResponseEntity<>(newPartyReservation, HttpStatus.OK);
		} catch (Exception e) {
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/createPartyReservations() --->"
							+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/partyreservations/{partyType}")
	public ResponseEntity<PartyReservation> updatePartyReservations(@PathVariable("partyType") String partyType,
			@RequestBody PartyReservation partyReservation) {
		Optional<PartyReservation> partyReservationData = partyReservationRepository.findByPartyType(partyType);
		if (partyReservationData.isPresent()) {
			PartyReservation currentPartyReservation = partyReservationData.get();
			currentPartyReservation.setPartyType(partyReservation.getPartyType());
			currentPartyReservation.setPrice(partyReservation.getPrice());
			currentPartyReservation.setStatus(partyReservation.getStatus());
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/updatePartyReservations() ---> Updating:"
							+ partyType);
			return new ResponseEntity<>(partyReservationRepository.save(currentPartyReservation), HttpStatus.OK);
		} else {
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/updatePartyReservations() --->"
							+ partyType + " not found");
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/partyreservations/{partyType}")
	public ResponseEntity<HttpStatus> deletePartyReservations(@PathVariable("partyType") String partyType) {
		try {
			partyReservationRepository.deleteByPartyType(partyType);
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/deletePartyReservations() ---> Deleting"
							+ partyType);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/deletePartyReservations() --->"
							+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/partyreservations")
	public ResponseEntity<HttpStatus> deleteAllPartyReservations() {
		try {
			partyReservationRepository.deleteAll();
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/deleteAllPartyReservations() ---> Deleting all Party");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info(
					"FROM com.playground.tcubeBurger.controller.PartyReservationController/deleteAllPartyReservations() --->"
							+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
