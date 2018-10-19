package com.example.iot.smartrefrigerator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.iot.smartrefrigerator.entity.Type;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class TypeIT {

	private static final String BASE_PATH = "/types";
	
	@BeforeAll
	public static void beforeClass() {
		RestAssured.baseURI = System.getProperty("application.baseURI");
		RestAssured.port = Integer.parseInt(System.getProperty("application.port"));
	}
	
	private List<String> cleanupList = new ArrayList<>();
	
	private String testTypeName;
	private Type testType;
	
	@BeforeEach
	public void setup() {
		testTypeName = RandomStringUtils.randomAlphanumeric(6) + "-Fruit";
		
		testType = new Type();
		testType.setName(testTypeName);
	}
	
	@AfterEach
	public void cleanup() {
		for (String location : cleanupList) {
			delete(location);
		}
	}
	
	@Test
	public void saveNewType() {
		
		String selfLink = 
			given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(testType).
			when().
				post(BASE_PATH).
			then().
				body("name", equalTo(testTypeName)).
			extract().
				path("_links.self.href");
		
		cleanupList.add(selfLink);

		when().
			get(BASE_PATH).
		then().
			body("_embedded.types", not(empty())).
			body("_embedded.types.find { it.name == '"+testTypeName+"' }._links.self.href", equalTo(selfLink));
	}
	
	@Test
	public void updateType() {
		
		String selfLink = 
			given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(testType).
			when().
				post(BASE_PATH).
			then().
				body("name", equalTo(testTypeName)).
			extract().
				path("_links.self.href");
		
		cleanupList.add(selfLink);
		
		String newName = RandomStringUtils.randomAlphanumeric(6) + "-Dairy";
		Type newType = new Type();
		newType.setName(newName);
		
		given().
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(newType).
		when().
			put(selfLink).
		then().
			body("name", equalTo(newName));
	}

}
