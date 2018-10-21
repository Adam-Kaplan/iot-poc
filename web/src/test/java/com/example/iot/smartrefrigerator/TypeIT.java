package com.example.iot.smartrefrigerator;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.iot.shared.entity.Type;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;

public class TypeIT {

	public static final String BASE_PATH = "/types";
	
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
		
		String selfLink = save(testType);

		when().
			get(BASE_PATH).
		then().
			body("_embedded.types", not(empty())).
			body("_embedded.types.find { it.name == '"+testTypeName+"' }._links.self.href", equalTo(selfLink));
	}
	
	@Test
	public void updateType() {
		
		String selfLink = save(testType);
		
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
	
	private String save(Type type) {
		String href = given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(type).
				log().ifValidationFails(LogDetail.BODY).
			when().
				post(BASE_PATH).
			then().
				log().ifValidationFails(LogDetail.BODY).
				body("name", equalTo(type.getName())).
			extract().
				path("_links.self.href");
		
		cleanupList.add(href);
		
		return href;
	}

}
