package com.cass.orders.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cass.orders.model.Order;

@Repository
public interface OrderRepository extends CassandraRepository<Order, String> {
	
	public Order findByOrderNo(String orderNo);
	
}
