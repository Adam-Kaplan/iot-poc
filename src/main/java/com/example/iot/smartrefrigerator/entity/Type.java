package com.example.iot.smartrefrigerator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.iot.smartrefrigerator.excerpt.TypeExcerpt;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Type implements TypeExcerpt {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min=0,max=256)
	private String name;
	
	@OneToMany(orphanRemoval=true, mappedBy="type", cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Item> items;
}
