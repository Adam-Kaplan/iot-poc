package com.example.iot.smartrefrigerator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.iot.smartrefrigerator.entity.Item;
import com.example.iot.smartrefrigerator.entity.Type;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class ItemIT {
	
	public static final String BASE_PATH = "/items";
	
	@BeforeAll
	public static void beforeClass() {
		RestAssured.baseURI = System.getProperty("application.baseURI");
		RestAssured.port = Integer.parseInt(System.getProperty("application.port"));
	}
	
	private List<String> cleanupList;
	
	private String testItemName;
	private Float testFill;
	private Item testItem;
	
	private String testTypeName;
	private Type testType;

	private String testUnderFillItemName;
	private Float testUnderFill;
	private Item testUnderFillItem;
	
	@BeforeEach
	public void setup() {
		cleanupList = new ArrayList<>();
		
		testTypeName = RandomStringUtils.randomAlphanumeric(6) + "-Vegetable";
		
		testType = new Type();
		testType.setName(testTypeName);
		
		
		testItemName = RandomStringUtils.randomAlphanumeric(6) + "-Carrot";
		testFill = 0.5f;
		
		testItem = new Item();
		testItem.setName(testItemName);
		testItem.setFill(testFill);
		testItem.setType(testType);
		

		testUnderFillItemName = RandomStringUtils.randomAlphanumeric(6) + "-Onion";
		testUnderFill = testFill - 0.1f;
		
		testUnderFillItem = new Item();
		testUnderFillItem.setName(testUnderFillItemName);
		testUnderFillItem.setFill(testUnderFill);
	}
	
	@AfterEach
	public void cleanup() {
		for (String location : cleanupList) {
			delete(location);
		}
	}
	
	@Test
	public void saveNewItem() {

		String selfLink = save(testItem);

		when().
			get(BASE_PATH).
		then().
			body("_embedded.items", not(empty())).
			body("_embedded.items.find { it.name == '"+testItemName+"' }._links.self.href", equalTo(selfLink));
	}
	
	@Test
	public void updateItem() {
		
		String selfLink = save(testItem);
		
		String newName = RandomStringUtils.randomAlphanumeric(6) + "-Potato";
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
	
	@Test
	public void deleteByType() {

		String itemLink = save(testItem);

		Integer typeId = 
			when().
				get(itemLink).
			then().
				statusCode(HttpStatus.OK.value()).
			extract().
				path("_embedded.type.id");
		
		given().
			param("id", typeId).
		when().
			delete("/custom/items/deleteByType").
		then().
			statusCode(HttpStatus.NO_CONTENT.value());
		
		when().
			get(itemLink).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
		
		when().
			get(TypeIT.BASE_PATH + '/' + typeId).
		then().
			statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void findLessThanFill() {
		
		String over = save(testItem);
		
		String under = save(testUnderFillItem);

		given().
			param("fill", testUnderFill).
		when().
			get(BASE_PATH + "/search/findByFillLessThanEqual").
		then().
			body("_embedded.items", not(empty())).
			body("_embedded.items.find { it._links.self.href == '"+over+"' }", nullValue()).
			body("_embedded.items.find { it.name == '"+testUnderFillItemName+"' }._links.self.href", equalTo(under));
	}
	
	private String save(Item item) {
		String typeHref = null;
		Type type = item.getType();
		
		if (type != null) {
			typeHref = save(type);
			item.setType(null);
		}
		
		String href = 
			given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(item).
				log().ifValidationFails(LogDetail.BODY).
			when().
				post(BASE_PATH).
			then().
				log().ifValidationFails(LogDetail.BODY).
				body("name", equalTo(item.getName())).
				body("fill", equalTo(item.getFill())).
			extract().
				path("_links.self.href");
		
		cleanupList.add(href);
		
		if (StringUtils.isNotEmpty(typeHref)) {
			given().
				contentType("text/uri-list").
				body(typeHref).
				log().ifValidationFails(LogDetail.ALL).
			when().
				put(href + "/type").
			then().
				log().ifValidationFails(LogDetail.ALL).
				statusCode(HttpStatus.NO_CONTENT.value());
		}
		
		return href;
	}
	
	private String save(Type type) {
		String href = 
			given().
				contentType(MediaType.APPLICATION_JSON_VALUE).
				body(type).
				log().ifValidationFails(LogDetail.BODY).
			when().
				post(TypeIT.BASE_PATH).
			then().
				log().ifValidationFails(LogDetail.BODY).
				body("name", equalTo(type.getName())).
			extract().
				path("_links.self.href");
		
		cleanupList.add(href);
		
		return href;
	}
	

}
