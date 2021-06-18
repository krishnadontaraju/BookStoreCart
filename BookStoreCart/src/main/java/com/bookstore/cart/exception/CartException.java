package com.bookstore.cart.exception;

public class CartException extends RuntimeException{

	@SuppressWarnings("unused")
	private int exceptionCode;
	@SuppressWarnings("unused")
	private String message;
	
	public CartException(int exceptionCode ,String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}
	
}
