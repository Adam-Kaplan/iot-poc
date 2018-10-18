package com.example.iot.smartrefrigerator.excerpt;

import java.util.UUID;

import org.springframework.data.rest.core.config.Projection;

import com.example.iot.smartrefrigerator.entity.Item;

@Projection(
		  name = "item", 
		  types = { Item.class })
public interface ItemExcerpt {
	UUID getId();
	String getName();
	Float getFill();
	TypeExcerpt getType();
}
