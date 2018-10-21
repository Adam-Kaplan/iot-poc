package com.example.iot.shared.excerpt;

import org.springframework.data.rest.core.config.Projection;

import com.example.iot.shared.entity.Type;

@Projection(
		  name = "type", 
		  types = { Type.class })
public interface TypeExcerpt {
	Long getId();
	String getName();
}
