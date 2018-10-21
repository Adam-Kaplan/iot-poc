package com.example.iot.smartrefrigerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.iot.shared.entity.Type;
import com.example.iot.smartrefrigerator.repository.ItemRepository;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping(path="/custom/items")
@Transactional
@Setter
@Getter
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@RequestMapping(path="/deleteByType", method=RequestMethod.DELETE)
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteByType(@RequestParam(name="id", required=true) Long typeId) {
		Type type = new Type();
		type.setId(typeId);
		
		itemRepository.deleteByType(type);
	}
}
