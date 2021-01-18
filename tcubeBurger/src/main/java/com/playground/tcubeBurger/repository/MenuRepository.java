package com.playground.tcubeBurger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.playground.tcubeBurger.model.Menu;

/**
 * 
 * @author tharoon
 *
 */

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
	List<Menu> findByItemNameContaining(String itemName);

	Optional<Menu> findByItemName(String itemName);

	void deleteByItemName(String itemName);
}
