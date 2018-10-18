package com.example.iot.smartrefrigerator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.iot.smartrefrigerator.entity.Item;
import com.example.iot.smartrefrigerator.entity.Type;
import com.example.iot.smartrefrigerator.excerpt.ItemExcerpt;

@Repository
@RepositoryRestResource(excerptProjection=ItemExcerpt.class)
public interface ItemRepository extends CrudRepository<Item, UUID> {
	
	@Modifying
	void deleteByType(Type type);
}
