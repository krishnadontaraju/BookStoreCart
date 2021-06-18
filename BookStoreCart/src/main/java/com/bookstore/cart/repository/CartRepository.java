package com.bookstore.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.cart.model.CartModel;

public interface CartRepository extends JpaRepository<CartModel , Long>{

	@Query(value = "SELECT * FROM cart as c WHERE c.customer = ?1 AND c.book = ?2" , nativeQuery = true)
	Optional<CartModel> findByBookAndCustomer(long customer, long book);

}
