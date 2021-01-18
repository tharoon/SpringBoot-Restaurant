package com.playground.tcubeBurger;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author tharoon
 *
 */
@SpringBootApplication
@Log4j2
public class TcubeBurgerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TcubeBurgerApplication.class, args);
		log.info("Run Successfull!");
	}

}
