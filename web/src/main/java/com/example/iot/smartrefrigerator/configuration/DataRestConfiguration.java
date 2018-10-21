package com.example.iot.smartrefrigerator.configuration;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.example.iot.shared.entity.Item;
import com.example.iot.shared.entity.Type;

@Component
public class DataRestConfiguration extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Item.class, Type.class);
	}
}
