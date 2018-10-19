package com.example.iot.smartrefrigerator.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.example.iot.smartrefrigerator.entity.Item;
import com.example.iot.smartrefrigerator.entity.Type;
import com.example.iot.smartrefrigerator.excerpt.ItemExcerpt;

@Repository
@RepositoryRestResource(excerptProjection=ItemExcerpt.class)
public interface ItemRepository extends CrudRepository<Item, UUID> {
	
	List<Item> findByFillLessThanEqual(Float fill);
	
	@Modifying
	@RestResource(exported=false)
	void deleteByType(Type type);
}
