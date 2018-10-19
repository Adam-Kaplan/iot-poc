package com.example.iot.smartrefrigerator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.iot.smartrefrigerator.excerpt.TypeExcerpt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@Valid
public class Type implements TypeExcerpt {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min=1,max=256,message="{com.example.iot.smartrefrigerator.entity.Type.name.size}")
	private String name;
	
	@OneToMany(orphanRemoval=true, mappedBy="type", cascade={CascadeType.ALL})
	private List<Item> items;
}
