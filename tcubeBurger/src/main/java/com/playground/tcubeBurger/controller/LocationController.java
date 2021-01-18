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

import com.playground.tcubeBurger.model.Location;
import com.playground.tcubeBurger.repository.LocationRepository;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author tharoon
 *
 */

@RestController
@RequestMapping("/api")
@Log4j2
public class LocationController {

	@Autowired
	LocationRepository locationRepository;

	@GetMapping("/locations")
	/*
	 * Giving @RequestParam = false because end user can either get all locations or
	 * location by name. We are combining the two ways of getting the location in
	 * one method. So we are explicitly saying that the request parameters from the
	 * URI is not mandatory. It may have or may not have.
	 */
	public ResponseEntity<List<Location>> getAllLocations(@RequestParam(required = false) String streetName) {
		try {
			List<Location> locationList = new ArrayList<Location>();
			if (streetName == null) {
				log.info(
						"FROM com.playground.tcubeBurger.controller.LocationController/getAllLocations() ---> streetName is null");
				locationRepository.findAll().forEach(locationList::add);
			} else {
				log.info(
						"FROM com.playground.tcubeBurger.controller.LocationController/getAllLocations() ---> streetName is not null");
				locationRepository.findByStreetNameContaining(streetName).forEach(locationList::add);
			}
			if (locationList.isEmpty()) {
				log.info(
						"FROM com.playground.tcubeBurger.controller.LocationController/getAllLocations() ---> Location list empty");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(locationList, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("FROM com.playground.tcubeBurger.controller.LocationController/getAllLocations() --->"
					+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Getting location by id */
	@GetMapping("/locations/{id}")
	public ResponseEntity<Location> getLocation(@PathVariable("id") String id) {
		Optional<Location> location = locationRepository.findById(id);
		if (location.isPresent()) {
			return new ResponseEntity<>(location.get(), HttpStatus.OK);
		} else {
			log.info(
					"FROM com.playground.tcubeBurger.controller.LocationController/getLocations() ---> No location found for the "
							+ id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* Inserting a new location into the database using HTTP POST method */
	@PostMapping("/locations")
	public ResponseEntity<Location> createLocation(@RequestBody Location location) {
		try {
			Location newLocation = locationRepository.save(location);
			log.info(
					"FROM com.playground.tcubeBurger.controller.LocationController/createLocation() ---> Saving Location");
			return new ResponseEntity<>(newLocation, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("FROM com.playground.tcubeBurger.controller.LocationController/createLocation() --->"
					+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Updating the location based on the ID from the end user */
	@PutMapping("/locations/{id}")
	public ResponseEntity<Location> updateLocation(@PathVariable("id") String id, @RequestBody Location location) {
		Optional<Location> locationData = locationRepository.findById(id);
		if (locationData.isPresent()) {
			Location currentLocation = locationData.get();
			currentLocation.setStreetName(location.getStreetName());
			currentLocation.setCity(location.getCity());
			currentLocation.setState(location.getState());
			currentLocation.setZip(location.getZip());
			currentLocation.setPhone(location.getPhone());
			log.info(
					"FROM com.playground.tcubeBurger.controller.LocationController/updateLocation() ---> Updating Location of id: "
							+ id);
			return new ResponseEntity<>(locationRepository.save(currentLocation), HttpStatus.OK);
		} else {
			log.info(
					"FROM com.playground.tcubeBurger.controller.LocationController/updateLocation() ---> No Location found with id: "
							+ id);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Deleting the location based on the ID from the end user */
	@DeleteMapping("/locations/{id}")
	public ResponseEntity<HttpStatus> deleteLocation(@PathVariable("id") String id) {
		try {
			locationRepository.deleteById(id);
			log.info(
					"FROM com.playground.tcubeBurger.controller.LocationController/deleteLocation() ---> Deleting Location with id: "
							+ id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.debug(
					"FROM com.playground.tcubeBurger.controller.LocationController/deleteLocation() ---> No Location found with id: "
							+ id + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Deleting all the locations based on the ID from the end user */
	@DeleteMapping("/locations")
	public ResponseEntity<HttpStatus> deleteAllLocations() {
		try {
			locationRepository.deleteAll();
			log.info(
					"FROM com.playground.tcubeBurger.controller.LocationController/deleteLocation() ---> Deleting Locations");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.debug("FROM com.playground.tcubeBurger.controller.LocationController/deleteLocation() --->"
					+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
