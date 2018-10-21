package com.example.iot.exec.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.example.iot.exec.service.SmartFridgeManager;

import lombok.Getter;
import lombok.Setter;

@ShellComponent
@Getter
@Setter
public class SmartFridgeController {
	
	@Autowired
	@Qualifier("HTTP-Manager")
	private SmartFridgeManager manager;

	@ShellMethod(group="sf", key="sf-remove-item", value="Emulates call to remove item from refrigerator.")
	public void handleItemRemoved(@NotNull @ShellOption(help="Id of the item to remove.") String itemUUID) {
		manager.handleItemRemoved(itemUUID);
	}

	@ShellMethod(group="sf", key="sf-add-item", value="Emulates call to add item from refrigerator.")
	public void handleItemAdded(
			@Min(1) @ShellOption(help="Id of the type to associate with this item.") long itemType, 
			@ShellOption(help="Id of the item to add.", defaultValue=ShellOption.NULL) String itemUUID, 
			@ShellOption(help="Name of the item being added.") String name, 
			@Min(0) @Max(1) @ShellOption(help="Quantity remaining of item.", defaultValue="1") Double fillFactor
	) {
		manager.handleItemAdded(itemType, itemUUID, name, fillFactor);
	}

	@ShellMethod(group="sf", key="sf-get-by-fill", value="Emulates call to retrieve items that have a fill less than or equal to the fill supplied.")
	public String getItems(
			@Min(0) @Max(1) @ShellOption(help="Quantity remaining of item.") Double fillFactor
	) {
		return Stream.of(manager.getItems(fillFactor))
				.map(Object::toString)
				.collect(Collectors.joining("\n"));
	}

	@ShellMethod(group="sf", key="sf-quantity-of-type", value="Emulates call to retrive average quanity for items associated with a type.")
	public Double getFillFactor(
			@Min(1) @ShellOption(help="Id of the type.") long itemType
	) {
		return manager.getFillFactor(itemType);
	}

	@ShellMethod(group="sf", key="sf-remove-type", value="Emulates call to remove all items by type.")
	public void forgetItem(
			@Min(1) @ShellOption(help="Id of the type.") long itemType
	) {
		manager.forgetItem(itemType);
	}

}
