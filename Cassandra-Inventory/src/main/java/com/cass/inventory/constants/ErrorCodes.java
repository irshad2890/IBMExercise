package com.cass.inventory.constants;

public enum ErrorCodes {

	ERROR500("500", "INTERNAL_SERVER_ERROR"), ERROR404("404", "NO PRODUCT FOUND");

	private final String key;
	private final String value;

	ErrorCodes(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
