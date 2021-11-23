package com.cass.orders.service.validation;

import java.util.List;

import com.cass.orders.model.Order;
import com.cass.orders.model.OrderError;

public interface Validation {

	public List<OrderError> validate(Order order);

}
