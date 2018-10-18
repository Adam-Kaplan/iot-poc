package com.example.iot.smartrefrigerator.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.example.iot.smartrefrigerator.excerpt.ItemExcerpt;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Item implements ItemExcerpt {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@NotNull
	@Size(min=1,max=256)
	private String name;
	
	@NotNull
	@PositiveOrZero
	@Max(value = 100)
	private Float fill;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Type type;

}
