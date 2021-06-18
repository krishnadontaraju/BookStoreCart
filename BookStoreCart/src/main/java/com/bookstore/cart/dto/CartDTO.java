package com.bookstore.cart.dto;

import lombok.Data;

@Data
public class CartDTO {

	private long customer;
	private long book;
	private int quantity;
	
}
