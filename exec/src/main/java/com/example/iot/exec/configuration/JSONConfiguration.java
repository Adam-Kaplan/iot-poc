package com.example.iot.exec.configuration;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
public class JSONConfiguration implements Jackson2ObjectMapperBuilderCustomizer {

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.serializationInclusion(Include.NON_EMPTY);
	}

}
