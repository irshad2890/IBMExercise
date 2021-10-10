package com.cass.inventory.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("inventorydemand")
@Data
@AllArgsConstructor
public class InventoryDemand {
	@Column("demandid")
	@PrimaryKey
	private String demandId;
	@Column("demandtype")
	private String demandType;
	@Column("orderno")
	private String orderNo;
	@Column("productid")
	private String productId;
	@Column("quantity")
	private String quantity;
}
