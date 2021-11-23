package com.cass.inventory.services.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cass.inventory.adapter.ProductAdapter;
import com.cass.inventory.model.InventoryError;

@Component
public class ProductValidation implements Validation {

	@Autowired
	private ProductAdapter productAdapter;

	@Override
	public List<InventoryError> validate(String productId) {
		return productAdapter.isValidProduct(productId);
	}

}
