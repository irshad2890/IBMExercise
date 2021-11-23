package com.cass.inventory.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class InventoryResponseDTO implements Serializable {

	private static final long serialVersionUID = -1167986730922036039L;

	private Object body;

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
}