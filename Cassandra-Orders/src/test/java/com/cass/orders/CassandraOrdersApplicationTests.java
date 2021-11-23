package com.cass.orders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.cass.orders.adapter.InventoryAdapter;
import com.cass.orders.adapter.ProductAdapter;
import com.cass.orders.contants.ErrorCodes;
import com.cass.orders.model.InventoryDemandDTO;
import com.cass.orders.model.Order;
import com.cass.orders.model.OrderError;
import com.cass.orders.model.ProductDTO;
import com.cass.orders.repository.OrderRepository;
import com.cass.orders.service.OrderService;
import com.cass.orders.service.validation.Validation;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CassandraOrdersApplicationTests {
	
	@Mock
	private OrderRepository orderRepo;
	
	@Mock
	private InventoryAdapter inventoryAdapter;
	
	@Mock
	private ProductAdapter productAdapter;
	
	@Mock
	private Validation validation;
	
	@InjectMocks
	private OrderService ordSvc;
	
	@Test
	void getAllOrdersTest() {
		Order order1 = new Order("9001", "US", "Irshad", "1234", "BT04", "1", "100", "Created", "No");
		Order order2 = new Order("9002", "US", "Suresh", "1234", "BT06", "1", "150", "Created", "No");
		List<Order> ordList = new ArrayList<>();
		ordList.add(order1);
		ordList.add(order2);
		when(orderRepo.findAll()).thenReturn(ordList);
		try {
			assertEquals(2, ordSvc.getAllOrders().size());
		}catch(Exception ex) {
		}
				
	}
	
	@Test
	void getOrderByOrderNoTest() {
		Order order = new Order("9001", "US", "Irshad", "1234", "BT04", "1", "100", "Created", "No");
		when(orderRepo.findByOrderNo("9001")).thenReturn(order);
		try {
			assertEquals("9001", ordSvc.getOrderByOrderNo("9001").getOrderNo());
		}catch(Exception ex) {
			System.out.println("exception");
		}
	}
	
	@Test
	void calculateLineTotalTest() {
		ProductDTO product = new ProductDTO("BT06", "6 Seater Dining Tabl", "150");
		when(productAdapter.productCall("BT06")).thenReturn(product);
		assertEquals("300", ordSvc.calculateLineTotal(product.getProductId(), "2"));
	}
	
	@Test
	void setPublishStatusPos() {
		String producId = "BB01";
		assertEquals("Yes", ordSvc.setPublishStatus(producId));
	}
	
	@Test
	void setPublishStatusNeg() {
		String producId = "BT04";
		assertEquals("No", ordSvc.setPublishStatus(producId));
	}
	
	@Test
	void createOrderPos() {
		
		try {
			Order order = new Order("9001", "US", "Irshad", "1234", "BT06", "1", "100", "Created", "No");
			InventoryDemandDTO invDemandDto = new InventoryDemandDTO("9011-BT06", "Reserved", "9011", "BT06", "2");
			ProductDTO product = new ProductDTO("BT06", "6 Seater Dining Tabl", "150");
			when(productAdapter.productCall("BT06")).thenReturn(product);
			List<OrderError> errors = new ArrayList<>();
			when(orderRepo.save(order)).thenReturn(order);
			when(productAdapter.isValidProduct(order.getProductId())).thenReturn(errors);
			when(inventoryAdapter.getAvailableQuantity(order.getProductId())).thenReturn("10");
			when(inventoryAdapter.createDemand(invDemandDto)).thenReturn(invDemandDto);
			assertEquals("9001", ordSvc.createOrder(order).getOrderNo());
		}catch(Exception ex) {
		}
	}
	
	@Test
	void createOrderTestProductNA() {
		Order order = new Order("9001", "US", "Irshad", "1234", "BT16", "1", null, null, null);
		InventoryDemandDTO invDemandDto = new InventoryDemandDTO("9011-BT16", "Reserved", "9011", "BT16", "2");
		List<OrderError> errors = new ArrayList<>();
		errors.add(new OrderError(ErrorCodes.ERR404.getKey(), ErrorCodes.ERR404.getValue()));
		try {
			when(orderRepo.save(order)).thenReturn(order);
			when(validation.validate(order)).thenReturn(errors);
			when(inventoryAdapter.createDemand(invDemandDto)).thenReturn(invDemandDto);
			assertEquals(null, ordSvc.createOrder(order).getStatus());
		}catch(Exception ex) {
			
		}
	}
	
	@Test
	void createOrderTestQtyNA() {
		Order order = new Order("9001", "US", "Irshad", "1234", "BT06", "1", "0", "BackOrdered", "NA");
		InventoryDemandDTO invDemandDto = new InventoryDemandDTO("9011-BT06", "Reserved", "9011", "BT06", "2");
		List<OrderError> errors = new ArrayList<>();
		errors.add(new OrderError(ErrorCodes.ERR410.getKey(), ErrorCodes.ERR410.getValue()));
		try {
			when(orderRepo.save(order)).thenReturn(order);
			when(validation.validate(order)).thenReturn(errors);
			when(inventoryAdapter.createDemand(invDemandDto)).thenReturn(invDemandDto);
			assertEquals("BackOrdered", ordSvc.createOrder(order).getStatus());
		}catch(Exception ex) {
			
		}
	}
	
}
