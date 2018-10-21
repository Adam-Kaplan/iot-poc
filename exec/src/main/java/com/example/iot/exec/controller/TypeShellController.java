package com.example.iot.exec.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.example.iot.exec.service.TypeCRUD;
import com.example.iot.shared.entity.Type;

import lombok.Getter;
import lombok.Setter;

@ShellComponent
@Getter
@Setter
public class TypeShellController {
	
	@Autowired
	private TypeCRUD typeCRUD;

	@ShellMethod(group="type", key="type-by-id", value="Display specific type by id.")
	public Type type(@NotNull @ShellOption(help="Id of the type.") Long id) {
		return typeCRUD.get(id).getContent();
	}
	
	@ShellMethod(group="type", key="type-list", value="Display all saved types.")
	public Iterable<Type> listTypes() {
		return typeCRUD.getAll().getContent();
	}
	
	@ShellMethod(group="type", key="type-save", value="Save new type.")
	public Type saveType(
			@NotNull @ShellOption(help="Name of the type.") String name
	) {
		Type type = new Type();
		type.setName(name);
		
		return typeCRUD.save(type).getContent();
	}

	@ShellMethod(group="type", key="type-update", value="Update an existing type.")
	public Type updateType(
			@NotNull @ShellOption(help="Id of the type.") Long id,
			@ShellOption(help="Name of the item.", defaultValue=ShellOption.NULL) String name
	) {
		Type type = new Type();
		type.setId(id);
		type.setName(name);
		
		return typeCRUD.save(type).getContent();
	}

	@ShellMethod(group="type", key="type-delete", value="Delete a type.")
	public String updateType(
			@NotNull @ShellOption(help="Id of the type.") Long id
	) {
		if (typeCRUD.delete(id)) {
			return "Success";
		} else {
			return "Failure";
		}
	}
}
