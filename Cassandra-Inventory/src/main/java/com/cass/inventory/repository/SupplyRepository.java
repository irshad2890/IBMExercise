package com.cass.inventory.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cass.inventory.model.InventorySupply;

@Repository
public interface SupplyRepository extends CassandraRepository<InventorySupply, String> {
	
	@AllowFiltering
	public List<InventorySupply> findByProductId(String productId);

}
