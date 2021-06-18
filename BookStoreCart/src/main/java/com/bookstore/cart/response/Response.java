package com.bookstore.cart.response;

import lombok.Data;

@Data
public class Response {

	private String message;
	
	private Object information;

	public Response(String message, Object information) {
		this.message = message;
		this.information = information;
	}
	
}
