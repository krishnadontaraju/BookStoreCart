package com.bookstore.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.cart.dto.CartDTO;
import com.bookstore.cart.response.Response;
import com.bookstore.cart.service.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cartService;
	
	//End point to add book to the cart
	@PostMapping("/addBook")
	public ResponseEntity<Response> addBookToTheCart(@RequestBody CartDTO cartDTO){
		
		Response addBookResponse = cartService.addBookToCart(cartDTO);
		return new ResponseEntity<Response> (addBookResponse , HttpStatus.OK);
	}
	
	//End point to remove book from the cart
	@DeleteMapping("/removeBook/{cartNumber}")
	public ResponseEntity<Response> removeBookFromCart(@PathVariable long cartNumber){
		
		Response removeBookResponse = cartService.removeBookFromCart(cartNumber);
		return new ResponseEntity<Response> (removeBookResponse , HttpStatus.OK);
	}
	
	//End point to change quantity of the cart
	@PutMapping("/changeQuantity/{cartNumber}")
	public ResponseEntity<Response> changeQuantityOfTheBook(@PathVariable long cartNumber , @RequestParam int quantity){
		
		Response changeQuantityResponse = cartService.changeQuantity(cartNumber , quantity);
		return new ResponseEntity<Response> (changeQuantityResponse , HttpStatus.OK);
		
	}
	//Get all books in cart
	@GetMapping("/viewFullCart")
	public ResponseEntity<Response> viewAllBooksInCart(@RequestHeader String token){
		
		Response viewAllBooksInCartResponse = cartService.viewAllBooksInCart(token);
		return new ResponseEntity<Response> (viewAllBooksInCartResponse , HttpStatus.OK);
		
	}
	
	//Get all books in cart for a specific user
	@GetMapping("/viewFullCart")
	public ResponseEntity<Response> viewAllBooksInCartForCustomer(@RequestHeader String token){
		
		Response viewAllBooksInCartResponse = cartService.viewAllBooksInCartForCustomer(token);
		return new ResponseEntity<Response> (viewAllBooksInCartResponse , HttpStatus.OK);
		
	}
}
