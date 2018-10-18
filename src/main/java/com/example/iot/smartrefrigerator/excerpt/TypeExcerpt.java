package com.example.iot.smartrefrigerator.excerpt;

import org.springframework.data.rest.core.config.Projection;

import com.example.iot.smartrefrigerator.entity.Type;

@Projection(
		  name = "type", 
		  types = { Type.class })
public interface TypeExcerpt {
	Long getId();
	String getName();
}
