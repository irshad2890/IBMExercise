package com.cass.contants;

public enum ErrorCodes {
	
	ERR500("500", "INTERNAL SERVER ERROR");
	
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	void setValue(String value) {
		this.value = value;
	}
	private ErrorCodes(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	

}
