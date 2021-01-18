package com.playground.tcubeBurger.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.playground.tcubeBurger.model.Location;

/**
 * 
 * @author tharoon
 *
 */

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
	List<Location> findByStreetNameContaining(String streetName);
}
