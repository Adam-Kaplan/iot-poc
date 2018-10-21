package com.example.iot.exec.service;

import java.net.URI;

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

import com.example.iot.shared.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class TypeCRUD {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${baseURI}/types")
	private String baseURI;

	
	public Resource<Type> get(Long id) {
		ResponseEntity<Resource<Type>> types = restTemplate.exchange(
				baseURI+'/'+id.toString(), 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<Resource<Type>>() {});
		
		return types.getBody();
	}
	
	public Resources<Type> getAll() {
		ResponseEntity<Resources<Type>> types = restTemplate.exchange(
				baseURI, 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<Resources<Type>>() {});
		
		return types.getBody();
	}
	
	public Resource<Type> save(Type type) {
		RequestEntity<Type> request;
		
		if (type.getId() == null) {
			request = RequestEntity.post(URI.create(baseURI))
					.contentType(MediaType.APPLICATION_JSON)
					.body(type);
		} else {
			request = RequestEntity.patch(URI.create(baseURI+"/"+type.getId().toString()))
					.contentType(MediaType.APPLICATION_JSON)
					.body(type);
		}
		
		ResponseEntity<Resource<Type>> response = restTemplate.exchange(
				request, 
				new ParameterizedTypeReference<Resource<Type>>() {});
		
		if (response.getStatusCode().is2xxSuccessful()) {
			// have to extract the id manually, since the library won't
			// https://github.com/spring-projects/spring-hateoas/issues/67
			String href = response.getBody().getId().getHref();
			String id = href.substring(href.lastIndexOf("/")+1);
			
			response.getBody().getContent().setId(Long.parseLong(id));

			return response.getBody();
		} else {
			// failed to create or update
			return null;
		}
	}
	
	public boolean delete(Long id) {
		ResponseEntity<String> response = restTemplate.exchange(
				baseURI+"/"+id.toString(), 
				HttpMethod.DELETE, 
				null, 
				String.class);
		
		return response.getStatusCode().is2xxSuccessful();
	}

}
