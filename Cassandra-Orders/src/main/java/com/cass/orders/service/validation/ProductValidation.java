package com.cass.orders.service.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cass.orders.adapter.ProductAdapter;
import com.cass.orders.model.Order;
import com.cass.orders.model.OrderError;

@Component("ProductValidation")
public class ProductValidation implements Validation {
	
	@Autowired
	private ProductAdapter productAdapter;

	@Override
	public List<OrderError> validate(Order order) {
		return productAdapter.isValidProduct(order.getProductId());
	}


}
