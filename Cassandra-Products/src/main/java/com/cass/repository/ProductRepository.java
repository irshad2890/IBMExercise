package com.cass.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cass.model.Product;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Integer>{
	
	public Product findByProductId(String productId);

}
