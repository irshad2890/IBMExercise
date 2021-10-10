package com.cass.orders.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cass.orders.contants.ErrorCodes;
import com.cass.orders.exception.DBDownException;
import com.cass.orders.exception.InventoryServiceException;
import com.cass.orders.model.Order;
import com.cass.orders.model.OrderError;
import com.cass.orders.model.OrderResponseDTO;
import com.cass.orders.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService ordSvc;
	
	@Autowired
	private OrderResponseDTO dto;
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<?> getAllOrders() throws DBDownException{
		try {
			dto.setBody(ordSvc.getAllOrders());
		}catch(DBDownException dbEx) {
			List<OrderError> errors = new ArrayList<>();
			OrderError error = new OrderError(ErrorCodes.ERR500.getKey(),
					ErrorCodes.ERR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/getByOrderNo/{orderNo}")
	public ResponseEntity<?> getByOrderNo(@PathVariable String orderNo) {
		try {
			dto.setBody(ordSvc.getOrderByOrderNo(orderNo));
		}catch(DBDownException dbEx) {
			List<OrderError> errors = new ArrayList<>();
			OrderError error = new OrderError(ErrorCodes.ERR500.getKey(),
					ErrorCodes.ERR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody Order order){
		try {
			dto.setBody(ordSvc.createOrder(order));
		}catch(InventoryServiceException dbEx) {
			List<OrderError> errors = new ArrayList<>();
			OrderError error = new OrderError(ErrorCodes.ERR500.getKey(),
					ErrorCodes.ERR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
	
}	
	
