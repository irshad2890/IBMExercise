package com.cass.orders.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cass.orders.adapter.InventoryAdapter;
import com.cass.orders.adapter.ProductAdapter;
import com.cass.orders.exception.DBDownException;
import com.cass.orders.exception.InventoryServiceException;
import com.cass.orders.model.InventoryDemandDTO;
import com.cass.orders.model.Order;
import com.cass.orders.model.OrderError;
import com.cass.orders.model.ProductDTO;
import com.cass.orders.repository.OrderRepository;
import com.cass.orders.service.validation.Validation;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	@Qualifier("OrderValidation")
	private Validation orderValidation;

	@Autowired
	private ProductAdapter productAdapter;
	
	@Autowired
	private InventoryAdapter inventoryAdapter;

	public List<Order> getAllOrders() throws DBDownException{
		try {
			return orderRepo.findAll();
		}catch(Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public Order getOrderByOrderNo(String orderNo)throws DBDownException {
		try {
			return orderRepo.findByOrderNo(orderNo);
		}catch(Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public String calculateLineTotal(String productId, String quantity) {
		ProductDTO product = productAdapter.productCall(productId);
		int unitPrice = Integer.parseInt(product.getUnit_price());
		int lineTotal = unitPrice * Integer.valueOf(quantity);
		return String.valueOf(lineTotal);
	}
	
	//Testing purpose
	public String getAvailableQty(String productId) throws InventoryServiceException {
		return inventoryAdapter.getAvailableQuantity(productId);
	}

	public InventoryDemandDTO createDemand(Order order) throws InventoryServiceException {
		InventoryDemandDTO invDemand = new InventoryDemandDTO();
		String demandId = order.getOrderNo() + "-" + order.getProductId();
		invDemand.setDemandId(demandId);
		invDemand.setDemandType("Reserved");
		invDemand.setOrderNo(order.getOrderNo());
		invDemand.setProductId(order.getProductId());
		invDemand.setQuantity(order.getQuantity());
		return inventoryAdapter.createDemand(invDemand);
	}
	
	public String setPublishStatus(String productId) {
		return productId.startsWith("BB") ? "Yes" : "No";
	}

	public Order createOrder(Order order) throws InventoryServiceException {
		String productId = order.getProductId();
		String ordQty = order.getQuantity();
		try {
			List<OrderError> errorList = orderValidation.validate(order);
			if(errorList.isEmpty()) {
				order.setStatus("Created");
				order.setLineTotal(calculateLineTotal(productId, ordQty));
				order.setPublished(setPublishStatus(productId));
				orderRepo.save(order);
			} 
			else if(errorList.stream()
					.map(OrderError::getErrorCode)
					.collect(Collectors.toList()).contains("500")) {
				throw new InventoryServiceException("There are some issues connecting to either product service or "
						+ "or Inventory Service");
			}
			else if(errorList.stream()
					.map(OrderError::getErrorCode)
					.collect(Collectors.toList()).contains("410")) {
				order.setStatus("BackOrdered");
				order.setLineTotal("0");
				order.setPublished("NA");
				orderRepo.save(order);
			}
			return order;
		}catch(Exception ex) {
			throw new InventoryServiceException("There are some issues while creating order");
		}
		
	}

}
