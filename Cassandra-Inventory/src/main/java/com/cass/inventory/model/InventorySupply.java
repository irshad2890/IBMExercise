package com.cass.inventory.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("inventorysupply")
@Data
@AllArgsConstructor
public class InventorySupply {
	@Column("inventoryid")
	@PrimaryKey
	private String inventoryId;
	@Column("productId")
	private String productId;
	@Column("quantity")
	private String quantity;
	@Column("supplytype")
	private String supplyType;	
}
