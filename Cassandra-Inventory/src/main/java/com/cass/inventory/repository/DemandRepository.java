package com.cass.inventory.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cass.inventory.model.InventoryDemand;

@Repository
public interface DemandRepository extends CassandraRepository<InventoryDemand, String>{
	
	@AllowFiltering
	public List<InventoryDemand> findByProductId(String productId);

}
