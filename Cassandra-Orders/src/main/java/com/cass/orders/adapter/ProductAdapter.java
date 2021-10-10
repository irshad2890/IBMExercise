package com.cass.orders.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.cass.orders.contants.ErrorCodes;
import com.cass.orders.model.OrderError;
import com.cass.orders.model.ProductDTO;



@Component
public class ProductAdapter {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${product_host}")
	private String productHost;
	
	@Value("${product_port}")
	private String productPort;

	public List<OrderError> isValidProduct(String productId) {
		ProductDTO product = null;
		List<OrderError> errors = new ArrayList<>();
		try {
			product = productCall(productId);
			if (ObjectUtils.isEmpty(product)) {
				OrderError error = new OrderError(ErrorCodes.ERR404.getKey(), ErrorCodes.ERR404.getValue());
				errors.add(error);
			}
		} catch (Exception e) {
			OrderError error = new OrderError(ErrorCodes.ERR500.getKey(), ErrorCodes.ERR500.getValue());
			errors.add(error);
		}

		return errors;
	}
	
	public ProductDTO productCall(String productId) {
		StringBuilder url = new StringBuilder();
		url.append("http://"+productHost+":");
		url.append(productPort);
		url.append("/products/");
		url.append(productId);
		return restTemplate.getForObject(url.toString(), ProductDTO.class);
	}

}
