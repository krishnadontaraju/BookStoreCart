package com.bookstore.cart.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookstore.cart.dto.CartDTO;
import com.bookstore.cart.exception.CartException;
import com.bookstore.cart.model.CartModel;
import com.bookstore.cart.repository.CartRepository;
import com.bookstore.cart.response.Response;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService implements ICartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Response addBookToCart(CartDTO cartDTO) {
		log.info("Book Creation Accessed");
		
		boolean doesCustomerExist = restTemplate.getForObject("http://localhost:6001/customer/checkCustomer/"+cartDTO.getCustomer() , boolean.class);
		Optional<CartModel> doesCustomerHaveTheBook = cartRepository.findByBookAndCustomer(cartDTO.getCustomer(), cartDTO.getBook());
		
		if (doesCustomerExist == true) {
			//Checking if duplicates exist if not then proceeding
			if (doesCustomerHaveTheBook.isPresent()) {
				log.info("Customer has already added the book to the cart , now updating the quantity");
				//Adding the quantity of the books , if the book is already present
				doesCustomerHaveTheBook.get().setQuantity(doesCustomerHaveTheBook.get().getQuantity() + cartDTO.getQuantity());
				cartRepository.save(doesCustomerHaveTheBook.get());
				log.info("adding the book to the cart was succesful , updated the quantity");
				return new Response("Added the Book to the Cart " ,"Thanks");
				
			}else {
				//If the customer does not have the book in cart hen adding the book to the cart
				log.info("Customer does not have the book in the cart and Adding it to the cart now");
				CartModel cart = mapper.map(cartDTO , CartModel.class);
				cartRepository.save(cart);
				log.info("Added the book to the cart");
				return new Response("Successfully added the book to the Cart" , "Enjoy Shopping");
			}
		}else {
			log.error("Customer could not be verified " +cartDTO.getCustomer() );
			throw new CartException(601 , "Customer could not veriifed");
		}
		
	}

	@Override
	public Response removeBookFromCart(long cartNumber) {
		log.info("Accessed Book removal from cart");
		
		Optional<CartModel> doesBookExistInCart = cartRepository.findById(cartNumber);
		//Checking if the Book is present in the cart or not
		if (doesBookExistInCart.isPresent()) {
			log.info("Book was found in the cart now starting the removal from cart");
			cartRepository.delete(doesBookExistInCart.get());
			log.info("Book Removed from Cart");
			return new Response("Book removed from Cart" ,HttpStatus.FOUND);
		}else {
			log.error("Book was not found for removing from the cart "+cartNumber);
			throw new CartException(511 , "Book was not present to remove from the cart");
		}
	}

	@Override
	public Response changeQuantity(long cartNumber , int quantity) {
		
		log.info("Accessed Cart updation");
		
		Optional<CartModel> doesBookExistInCart = cartRepository.findById(cartNumber);
		//Checking if the Book is added to the cart or not
		if (doesBookExistInCart.isPresent()) {
			log.info("Customer has already added the book to the cart , now updating the quantity");
			//Adding the quantity of the books , if the book is already present
			doesBookExistInCart.get().setQuantity(doesBookExistInCart.get().getQuantity() + quantity);
			cartRepository.save(doesBookExistInCart.get());
			log.info("Cart updated ");
			return new Response("Updated the Books in the Cart " ,"Thanks");
		}else {
			log.error("Book was not found for removing from the cart "+cartNumber);
			throw new CartException(501 , "Book not found with given details");
		}
	}

}
