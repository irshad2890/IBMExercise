package com.cass.inventory.model;

import java.io.Serializable;

public class InventoryError implements Serializable {

	private static final long serialVersionUID = 3325649356397070182L;
	private String errorCode;
	private String errorDesc;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public InventoryError(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	

}
