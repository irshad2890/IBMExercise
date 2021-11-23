package com.cass.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cass.inventory.constants.ErrorCodes;
import com.cass.inventory.exception.DBDownException;
import com.cass.inventory.exception.InventoryServiceException;
import com.cass.inventory.exception.ProductServiceException;
import com.cass.inventory.model.InventoryDemand;
import com.cass.inventory.model.InventoryError;
import com.cass.inventory.model.InventorySupply;
import com.cass.inventory.model.InventoryResponseDTO;
import com.cass.inventory.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private InventoryResponseDTO dto;

	@GetMapping("/supplies")
	public ResponseEntity<?> getAllSupply() {
		
		try {
			dto.setBody(inventoryService.getAllSupply());
		} catch (DBDownException dbEx) {
			List<InventoryError> errors = new ArrayList<>();
			InventoryError error = new InventoryError(ErrorCodes.ERROR500.getKey(),
					ErrorCodes.ERROR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/demands")
	public ResponseEntity<?> getAllDemand() {
		try {
			dto.setBody(inventoryService.getAllDemand());
		} catch (DBDownException dbEx) {
			List<InventoryError> errors = new ArrayList<>();
			InventoryError error = new InventoryError(ErrorCodes.ERROR500.getKey(),
					ErrorCodes.ERROR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/supplies/products/{productId}")
	public ResponseEntity<?> getSupplyByProductId(@PathVariable String productId) {
		try {
			dto.setBody(inventoryService.getSupplyByProductId(productId));
		} catch (DBDownException dbEx) {
			List<InventoryError> errors = new ArrayList<>();
			InventoryError error = new InventoryError(ErrorCodes.ERROR500.getKey(),
					ErrorCodes.ERROR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/demands/products/{productId}")
	public ResponseEntity<?> getDemandByProductId(@PathVariable String productId) {
		try {
			dto.setBody(inventoryService.getDemandByProductId(productId));
		} catch (DBDownException dbEx) {
			List<InventoryError> errors = new ArrayList<>();
			InventoryError error = new InventoryError(ErrorCodes.ERROR500.getKey(),
					ErrorCodes.ERROR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/supply")
	public ResponseEntity<?> createSupply(@RequestBody InventorySupply supply) {
		try {
			dto.setBody(inventoryService.createSupply(supply));
		} catch (ProductServiceException pdEx) {
			List<InventoryError> errors = new ArrayList<>();
			InventoryError error = new InventoryError(ErrorCodes.ERROR404.getKey(),
					ErrorCodes.ERROR404.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return new ResponseEntity<InventoryResponseDTO>(dto, HttpStatus.CREATED);
	}

	@PostMapping("/demand")
	public ResponseEntity<?> createDemand(@RequestBody InventoryDemand demand) {
		try {
			dto.setBody(inventoryService.createDemand(demand));
		} catch (DBDownException dbEx) {
			List<InventoryError> errors = new ArrayList<>();
			InventoryError error = new InventoryError(ErrorCodes.ERROR500.getKey(),
					ErrorCodes.ERROR500.getValue());
			errors.add(error);
			return ResponseEntity.internalServerError().body(errors);
		}
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("/products/{productId}/quantity")
	public String getAvailableQty(@PathVariable String productId) {
		try {
			return inventoryService.getAvailableQty(productId);
		}catch(InventoryServiceException invEx) {
			return null;
		}
		
	}

}
