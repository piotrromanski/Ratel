package com.payu.hackathon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mariusz Smykuła
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private static List<String> features = Arrays.asList("Service Discovering", "Service Registering", "Bleeding Edge Features");

	public static void main(String[] args) {

		LOG.info("ServiceDiscovery System starting...");

		features.forEach(System.out::println);

		SpringApplication.run(Application.class, args);
	}

}
