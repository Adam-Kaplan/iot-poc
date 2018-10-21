package com.example.iot.exec.service;

import java.net.URI;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.iot.shared.entity.Item;
import com.example.iot.shared.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Service("HTTP-Manager")
@Getter
@Setter
public class HttpManagerService implements SmartFridgeManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpManagerService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ItemCRUD itemCRUD;

	@Autowired
	private TypeCRUD typeCRUD;

	@Value("${baseURI}")
	private String baseURI;
	
	@Override
	public void handleItemRemoved(String itemUUID) {
		itemCRUD.delete(UUID.fromString(itemUUID));
	}

	@Override
	public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {
		Item newItem = new Item();
		newItem.setName(name);
		newItem.setFill(fillFactor.floatValue());
		if (StringUtils.isNotBlank(itemUUID)) {
			newItem.setId(UUID.fromString(itemUUID));
		}
		
		Resource<Item> item = itemCRUD.save(newItem);
		
		Resource<Type> type = typeCRUD.get(itemType);
		
		if (
			// item and type exist
			item != null && type != null &&
			// item has not type or incoming type is different
			(item.getContent().getType() == null || !item.getContent().getType().equals(type.getContent()))
		) {
			RequestEntity<String> request = RequestEntity.put(item.getLink("type").getTemplate().expand())
				.contentType(MediaType.parseMediaType("text/uri-list"))
				.body(type.getId().getHref());
			
			ResponseEntity<String> response = restTemplate.exchange(request, String.class);
			
			LOGGER.info("Item + Type association [{}]", response.getStatusCode());
		}
	}

	@Override
	public Object[] getItems(Double fillFactor) {
		
		ResponseEntity<Resources<Item>> items = restTemplate.exchange(
				itemCRUD.getBaseURI()+"/search/findByFillLessThanEqual?fill={fill}", 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<Resources<Item>>() {}, 
				fillFactor);
		
		return items.getBody().getContent().toArray();
	}

	@Override
	public Double getFillFactor(long itemType) {
		return new Traverson(URI.create(typeCRUD.getBaseURI()+'/'+itemType), MediaTypes.HAL_JSON)
			.follow("items")
			.toObject(new ParameterizedTypeReference<Resources<Item>>() {})
			.getContent()
			.stream()
			.mapToDouble(Item::getFill)
			.filter(fill -> fill > 0)
			.average()
			.orElse(0);
	}

	@Override
	public void forgetItem(long itemType) {
		typeCRUD.delete(itemType);
	}

	
}
