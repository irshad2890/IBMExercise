package com.cass.orders.contants;

public enum ErrorCodes {
	
	ERR500("500", "INTERNAL SERVER ERROR"), ERR404("404", "PRODUCT NOT FOUND"), 
	ERR410("410", "INVENTORY NOT AVAILABLE");
	
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	private ErrorCodes(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	

}
