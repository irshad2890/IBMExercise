package com.cass.inventory.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.cass.inventory.constants.ErrorCodes;
import com.cass.inventory.model.InventoryError;
import com.cass.inventory.model.ProductDTO;

@Component
public class ProductAdapter {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${product_host}")
	private String productHost;
	
	@Value("${product_port}")
	private String productPort;

	public List<InventoryError> isValidProduct(String productId) {
		ProductDTO product = null;
		List<InventoryError> errors = new ArrayList<>();
		try {
			StringBuilder url = new StringBuilder();
			url.append("http://"+productHost+":");
			url.append(productPort);
			url.append("/products/");
			url.append(productId);
			product = restTemplate.getForObject(url.toString(), ProductDTO.class);
		} catch (Exception e) {
			InventoryError error = new InventoryError(ErrorCodes.ERROR500.getKey(), ErrorCodes.ERROR500.getValue());
			errors.add(error);
		}

		if (ObjectUtils.isEmpty(product)) {
			InventoryError error = new InventoryError(ErrorCodes.ERROR404.getKey(), ErrorCodes.ERROR404.getValue());
			errors.add(error);
		}

		return errors;
	}

}
