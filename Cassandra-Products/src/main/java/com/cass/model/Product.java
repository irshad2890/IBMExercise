package com.cass.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Column("product_id")
	@PrimaryKey
	private String productId;
	@Column("product_desc")
	private String product_desc;
	@Column("unit_price")
	private String unit_price;
}
