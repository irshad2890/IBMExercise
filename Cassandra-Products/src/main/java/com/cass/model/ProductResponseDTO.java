package com.cass.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ProductResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3408786167114622327L;
	
	private Object responseBody;

	public Object getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}
	

}
