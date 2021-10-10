package com.cass.inventory.services.validation;

import java.util.List;

import com.cass.inventory.model.InventoryError;

public interface Validation {

	public List<InventoryError> validate(String string);

}
