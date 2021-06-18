package com.bookstore.cart.service;

import com.bookstore.cart.dto.CartDTO;
import com.bookstore.cart.response.Response;

public interface ICartService {

	Response addBookToCart(CartDTO cartDTO);

	Response removeBookFromCart(long cartNumber);

	Response changeQuantity(long cartNumber , int quantity);

}
