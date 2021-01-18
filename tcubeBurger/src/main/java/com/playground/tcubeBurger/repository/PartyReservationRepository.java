package com.playground.tcubeBurger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.playground.tcubeBurger.model.PartyReservation;

/**
 * 
 * @author tharoon
 *
 */

@Repository
public interface PartyReservationRepository extends MongoRepository<PartyReservation, String> {
	List<PartyReservation> findByPartyTypeContaining(String partyType);

	Optional<PartyReservation> findByPartyType(String partyType);

	void deleteByPartyType(String partyType);
}
