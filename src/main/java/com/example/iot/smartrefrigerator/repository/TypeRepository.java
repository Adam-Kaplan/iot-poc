package com.example.iot.smartrefrigerator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.iot.smartrefrigerator.entity.Type;
import com.example.iot.smartrefrigerator.excerpt.TypeExcerpt;

@Repository
@RepositoryRestResource(excerptProjection=TypeExcerpt.class)
public interface TypeRepository extends CrudRepository<Type, Long> {
	
}
