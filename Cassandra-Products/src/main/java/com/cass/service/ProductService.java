package com.cass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cass.exception.DBDownException;
import com.cass.model.Product;
import com.cass.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	public List<Product> getAllProducts() throws DBDownException{
		try {
			return productRepo.findAll();
		}catch(Exception ex) {
			throw new DBDownException("Cassandra DB Service is Down");
		}
		
	}
	
	public Product createProduct(Product product) throws DBDownException {
		try {
			return productRepo.save(product);
		}catch(Exception ex) {
			throw new DBDownException("Cassandra DB service is down");
		}
		
	}
	
	public Product getProductById(String productId) throws DBDownException {
		try {
			return productRepo.findByProductId(productId);
		}catch(Exception ex) {
			throw new DBDownException("Cassandra DB service is down");
		}
		
	}

}
