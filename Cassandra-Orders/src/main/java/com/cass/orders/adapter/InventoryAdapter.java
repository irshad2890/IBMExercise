package com.cass.orders.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cass.orders.exception.InventoryServiceException;
import com.cass.orders.model.InventoryDemandDTO;

@Component
public class InventoryAdapter {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${inventory_host}")
	private String inventoryHost;
	
	@Value("${inventory_port}")
	private String inventoryPort;

	public String getAvailableQuantity(String productId) throws InventoryServiceException {
		
		try {
			StringBuilder url = new StringBuilder();
			url.append("http://"+inventoryHost+":");
			url.append(inventoryPort);
			url.append("/inventory/products/");
			url.append(productId);
			url.append("/quantity");
			return restTemplate.getForObject(url.toString(), String.class);
		} catch (Exception e) {
			throw new InventoryServiceException("There are some issues while fetching the available quantity");
		}

		
	}
	
	public InventoryDemandDTO createDemand(InventoryDemandDTO invDemandDto) throws InventoryServiceException{
		try {
			StringBuilder url = new StringBuilder();
			url.append("http://"+inventoryHost+":");
			url.append(inventoryPort);
			url.append("/inventory/demand/");
			return restTemplate.postForObject(url.toString(), invDemandDto, InventoryDemandDTO.class);
		} catch (Exception e) {
			throw new InventoryServiceException("There are some issues while fetching the available quantity");
		}
	}

}
