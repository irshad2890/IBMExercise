package com.cass.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cass.inventory.exception.DBDownException;
import com.cass.inventory.exception.InventoryServiceException;
import com.cass.inventory.exception.ProductServiceException;
import com.cass.inventory.model.InventoryError;
import com.cass.inventory.model.InventoryDemand;
import com.cass.inventory.model.InventorySupply;
import com.cass.inventory.repository.DemandRepository;
import com.cass.inventory.repository.SupplyRepository;
import com.cass.inventory.services.validation.Validation;

@Service
public class InventoryService {

	@Autowired
	private SupplyRepository supplyRepo;

	@Autowired
	private DemandRepository demandRepo;

	@Autowired
	private Validation validation;

	public List<InventorySupply> getAllSupply() throws DBDownException {
		try {
			return supplyRepo.findAll();
		} catch (Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public List<InventoryDemand> getAllDemand() throws DBDownException{
		try {
			return demandRepo.findAll();
		} catch (Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public InventorySupply createSupply(InventorySupply supply) throws ProductServiceException {
		List<InventoryError> error = validation.validate(supply.getProductId());

		if (error.isEmpty()) {
			return supplyRepo.save(supply);
		} else
			throw new ProductServiceException("There are some issues while validating the product");
	}

	public InventoryDemand createDemand(InventoryDemand demand) throws DBDownException{
		try {
			return demandRepo.save(demand);
		} catch (Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public List<InventorySupply> getSupplyByProductId(String productId) throws DBDownException{
		try {
			return supplyRepo.findByProductId(productId);
		} catch (Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public List<InventoryDemand> getDemandByProductId(String productId) throws DBDownException {
		try {
			return demandRepo.findByProductId(productId);
		} catch (Exception ex) {
			throw new DBDownException("Cassandra DB is down");
		}
	}

	public String getAvailableQty(String productId) throws InventoryServiceException {
		Integer avlQty = 0;
		Integer totSupQty = 0;
		Integer totDemQty = 0;
		
		try {
			List<InventorySupply> invSupplyList = getSupplyByProductId(productId);
			List<InventoryDemand> invDemandList = getDemandByProductId(productId);

			if (!invSupplyList.isEmpty())
				totSupQty = invSupplyList.stream().mapToInt(sl -> Integer.valueOf(sl.getQuantity())).sum();
			if (!invDemandList.isEmpty())
				totDemQty = invDemandList.stream().mapToInt(dl -> Integer.valueOf(dl.getQuantity())).sum();
			avlQty = totSupQty - totDemQty;
			return String.valueOf(avlQty);
		}catch(Exception ex) {
			throw new InventoryServiceException("Some issues while calculating available quantity");
		}
		
		
	}
}
