package com.cass.orders.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("orders")
@Data
@AllArgsConstructor
public class Order {
	@Column("orderno")
	@PrimaryKey
	private String orderNo;
	@Column("custaddress")
	private String custAddress;
	@Column("custname")
	private String custName;
	@Column("custphone")
	private String custPhone;
	@Column("productid")
	private String productId;
	@Column("quantity")
	private String quantity;
	@Column("linetotal")
	private String lineTotal;
	@Column("status")
	private String status;
	@Column("published")
	private String published;
}
