package com.cass.controller;

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

import com.cass.contants.ErrorCodes;
import com.cass.exception.DBDownException;
import com.cass.model.Product;
import com.cass.model.ProductError;
import com.cass.model.ProductResponseDTO;
import com.cass.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService prdSvc;
	
	@Autowired
	private ProductResponseDTO dto;
	
	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts(){
		try {
			dto.setResponseBody(prdSvc.getAllProducts());
		}catch(DBDownException dbEx) {
			List<ProductError> errorList = new ArrayList<>();
			ProductError error = new ProductError(ErrorCodes.ERR500.getKey(), ErrorCodes.ERR500.getValue());
			errorList.add(error);
			return ResponseEntity.internalServerError().body(errorList);
		}
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@RequestBody Product product){
		try {
			dto.setResponseBody(prdSvc.createProduct(product));
		}catch(DBDownException dbEx) {
			List<ProductError> errorList = new ArrayList<>();
			ProductError error = new ProductError(ErrorCodes.ERR500.getKey(), ErrorCodes.ERR500.getValue());
			errorList.add(error);
			return ResponseEntity.internalServerError().body(errorList);
		}
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping("/products/{productId}")
	public Product getProductById(@PathVariable String productId) {
		try {
			return prdSvc.getProductById(productId);
		}catch(DBDownException dbEx) {
			return null;
		}
	}

}
