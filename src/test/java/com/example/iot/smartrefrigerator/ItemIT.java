package com.example.iot.smartrefrigerator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.iot.smartrefrigerator.entity.Item;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class ItemIT {
	
	private static final String BASE_PATH = "/items";
	
	@BeforeAll
	public static void beforeClass() {
		RestAssured.baseURI = System.getProperty("application.baseURI");
		RestAssured.port = Integer.parseInt(System.getProperty("application.port"));
	}
	
	private List<String> cleanupList = new ArrayList<>();
	
	private String testItemName;
	private Float testFill;
	private Item testItem;
	
	@BeforeEach
	public void setup() {
		testItemName = RandomStringUtils.randomAlphanumeric(6) + "-Carrot";
		testFill = 0.5f;
		
		testItem = new Item();
		testItem.setName(testItemName);
		testItem.setFill(testFill);
	}
	
	@AfterEach
	public void cleanup() {
		for (String location : cleanupList) {
			delete(location);
		}
	}
	
	@Test
	public void saveNewItem() {
		
		String selfLink = 
			given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(testItem).
			when().
				post(BASE_PATH).
			then().
				body("name", equalTo(testItemName)).
				body("fill", equalTo(testFill)).
			extract().
				path("_links.self.href");
		
		cleanupList.add(selfLink);

		when().
			get(BASE_PATH).
		then().
			body("_embedded.items", not(empty())).
			body("_embedded.items.find { it.name == '"+testItemName+"' }._links.self.href", equalTo(selfLink));
	}
	
	@Test
	public void updateItem() {
		
		String selfLink = 
			given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(testItem).
			when().
				post(BASE_PATH).
			then().
				body("name", equalTo(testItemName)).
				body("fill", equalTo(testFill)).
			extract().
				path("_links.self.href");
		
		cleanupList.add(selfLink);
		
		String newName = RandomStringUtils.randomAlphanumeric(6) + "-Milk";
		Float newFill = 0.7f;
		Item newItem = new Item();
		newItem.setName(newName);
		newItem.setFill(newFill);
		
		given().
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(newItem).
		when().
			put(selfLink).
		then().
			body("name", equalTo(newName)).
			body("fill", equalTo(newFill));
	}
	
	

}
