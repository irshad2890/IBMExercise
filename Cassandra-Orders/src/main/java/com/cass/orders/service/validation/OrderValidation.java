package com.cass.orders.service.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cass.orders.adapter.InventoryAdapter;
import com.cass.orders.contants.ErrorCodes;
import com.cass.orders.exception.InventoryServiceException;
import com.cass.orders.model.Order;
import com.cass.orders.model.OrderError;

@Component("OrderValidation")
public class OrderValidation implements Validation {

	@Autowired
	@Qualifier("ProductValidation")
	private Validation productValidation;
	
	@Autowired
	private InventoryAdapter inventoryAdapter;

	@Override
	public List<OrderError> validate(Order order) {
		List<OrderError> errorList = productValidation.validate(order);
		if(errorList.isEmpty()) {
			try {
				String avlQty = inventoryAdapter.getAvailableQuantity(order.getProductId());
				if(Integer.valueOf(avlQty) < Integer.valueOf(order.getQuantity())) {
					errorList.add(new OrderError(ErrorCodes.ERR404.getKey(), ErrorCodes.ERR404.getValue()));
				}
			}catch(InventoryServiceException invEx) {
				errorList.add(new OrderError(ErrorCodes.ERR500.getKey(), ErrorCodes.ERR500.getValue()));
			}
			
		}
		return errorList;
	}

}
