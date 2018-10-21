package com.example.iot.exec.service;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.iot.shared.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ItemCRUD {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${baseURI}/items")
	private String baseURI;

	public Resource<Item> get(UUID id) {
		ResponseEntity<Resource<Item>> item = restTemplate.exchange(
				baseURI+'/'+id.toString(), 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<Resource<Item>>() {});
		
		return item.getBody();
	}
	
	public Resources<Item> getAll() {
		ResponseEntity<Resources<Item>> items = restTemplate.exchange(
				baseURI, 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<Resources<Item>>() {});
		
		return items.getBody();
	}
	
	public Resource<Item> save(Item item) {
		RequestEntity<Item> request;
		
		if (item.getId() == null) {
			request = RequestEntity.post(URI.create(baseURI))
					.contentType(MediaType.APPLICATION_JSON)
					.body(item);
		} else {
			request = RequestEntity.patch(URI.create(baseURI+"/"+item.getId().toString()))
					.contentType(MediaType.APPLICATION_JSON)
					.body(item);
		}
		
		ResponseEntity<Resource<Item>> response = restTemplate.exchange(
				request, 
				new ParameterizedTypeReference<Resource<Item>>() {});
		
		if (response.getStatusCode().is2xxSuccessful()) {
			// have to extract the id manually, since the library won't
			// https://github.com/spring-projects/spring-hateoas/issues/67
			String href = response.getBody().getId().getHref();
			String id = href.substring(href.lastIndexOf("/")+1);
			
			response.getBody().getContent().setId(UUID.fromString(id));

			return response.getBody();
		} else {
			// failed to create or update
			return null;
		}
	}
	
	public boolean delete(UUID id) {
		ResponseEntity<String> response = restTemplate.exchange(
				baseURI+"/"+id.toString(), 
				HttpMethod.DELETE, 
				null, 
				String.class);
		
		return response.getStatusCode().is2xxSuccessful();
	}

}
