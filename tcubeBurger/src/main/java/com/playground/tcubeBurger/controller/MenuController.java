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

import com.playground.tcubeBurger.model.Menu;
import com.playground.tcubeBurger.repository.MenuRepository;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author tharoon
 *
 */

@RestController
@RequestMapping("/api")
@Log4j2
public class MenuController {

	@Autowired
	MenuRepository menuRepository;

	@GetMapping("/menus")
	public ResponseEntity<List<Menu>> getAllMenus(@RequestParam(required = false) String itemName) {
		try {
			List<Menu> menuList = new ArrayList<Menu>();
			if (itemName == null) {
				log.info(
						"FROM com.playground.tcubeBurger.controller.MenuController/getAllMenus() ---> itemName is null");
				menuRepository.findAll().forEach(menuList::add);
			} else {
				log.info(
						"FROM com.playground.tcubeBurger.controller.MenuController/getAllMenus() ---> itemName is not null");
				menuRepository.findByItemNameContaining(itemName).forEach(menuList::add);
			}
			if (menuList.isEmpty()) {
				log.info(
						"FROM com.playground.tcubeBurger.controller.MenuController/getAllMenus() ---> menuList is empty");
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(menuList, HttpStatus.OK);

		} catch (Exception e) {
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/getAllMenus() --->" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/menus/{id}")
	public ResponseEntity<Menu> getMenus(@PathVariable("id") String id) {
		Optional<Menu> menu = menuRepository.findById(id);
		if (menu.isPresent()) {
			return new ResponseEntity<>(menu.get(), HttpStatus.OK);
		} else {
			log.info(
					"FROM com.playground.tcubeBurger.controller.MenuController/getMenus() ---> No menu found for the id: "
							+ id);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/menus")
	public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
		try {
			Menu newMenu = menuRepository.save(menu);
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/createMenu() ---> Saving Menu");
			return new ResponseEntity<>(newMenu, HttpStatus.OK);
		} catch (Exception e) {
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/createMenu() ---> " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/menus/{itemName}")
	public ResponseEntity<Menu> updateMenu(@PathVariable("itemName") String itemName, @RequestBody Menu menu) {
		Optional<Menu> menuData = menuRepository.findByItemName(itemName);
		if (menuData.isPresent()) {
			Menu currentMenu = menuData.get();
			currentMenu.setItemName(menu.getItemName());
			currentMenu.setCategory(menu.getCategory());
			currentMenu.setPrice(menu.getPrice());
			log.info(
					"FROM com.playground.tcubeBurger.controller.MenuController/updateMenu() ---> Updating " + itemName);
			return new ResponseEntity<>(menuRepository.save(currentMenu), HttpStatus.OK);
		} else {
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/updateMenu() --->" + itemName
					+ " not found");
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/menus/{itemName}")
	public ResponseEntity<HttpStatus> deleteMenu(@PathVariable("itemName") String itemName) {
		try {
			menuRepository.deleteByItemName(itemName);
			log.info(
					"FROM com.playground.tcubeBurger.controller.MenuController/deleteMenu() ---> Deleting " + itemName);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/deleteMenu() --->" + itemName
					+ " not found");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/menus")
	public ResponseEntity<HttpStatus> deleteAllMenus() {
		try {
			menuRepository.deleteAll();
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/deleteAllMenus() ---> Deleting items");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("FROM com.playground.tcubeBurger.controller.MenuController/deleteAllMenus() ---> "
					+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
