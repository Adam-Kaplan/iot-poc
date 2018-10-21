package com.example.iot.exec.controller;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.example.iot.exec.service.ItemCRUD;
import com.example.iot.shared.entity.Item;

import lombok.Getter;
import lombok.Setter;

@ShellComponent
@Getter
@Setter
public class ItemShellController {
	
	@Autowired
	private ItemCRUD itemCRUD;

	@ShellMethod(group="item", key="item-by-id", value="Display specific item by id.")
	public Item get(@NotNull @ShellOption(help="Id of the item.") UUID id) {
		return itemCRUD.get(id).getContent();
	}
	
	@ShellMethod(group="item", key="item-list", value="Display all saved items.")
	public Iterable<Item> listItems() {
		return itemCRUD.getAll().getContent();
	}
	
	@ShellMethod(group="item", key="item-save", value="Save new item.")
	public Item saveItem(
			@NotNull @ShellOption(help="Name of the item.") String name, 
			@NotNull @Min(0) @Max(1) @ShellOption(help="Quantity remaining of item.", defaultValue="1") Float fill
	) {
		Item item = new Item();
		
		item.setName(name);
		item.setFill(fill);
		
		return itemCRUD.save(item).getContent();
	}

	@ShellMethod(group="item", key="item-update", value="Update an existing item.")
	public Item updateItem(
			@NotNull @ShellOption(help="Id of the item.") UUID id,
			@ShellOption(help="Name of the item.", defaultValue=ShellOption.NULL) String name, 
			@Min(0) @Max(1) @ShellOption(help="Quantity remaining of item.", defaultValue="1") Float fill
	) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setFill(fill);
		
		return itemCRUD.save(item).getContent();
	}

	@ShellMethod(group="item", key="item-delete", value="Delete an item.")
	public String updateItem(
			@NotNull @ShellOption(help="Id of the item.") UUID id
	) {
		if (itemCRUD.delete(id)) {
			return "Success";
		} else {
			return "Failure";
		}
	}
}
