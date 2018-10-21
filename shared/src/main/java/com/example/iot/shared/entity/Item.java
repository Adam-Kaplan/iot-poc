package com.example.iot.shared.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.iot.shared.excerpt.ItemExcerpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Valid
public class Item implements ItemExcerpt {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@NotNull
	@Size(min=1,max=256,message="{com.example.iot.smartrefrigerator.entity.Item.name.size}")
	private String name;
	
	@NotNull
	@Min(value = 0)
	@Max(value = 1)
	private Float fill = 1f;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Type type;

}
