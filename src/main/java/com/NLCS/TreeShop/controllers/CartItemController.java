package com.NLCS.TreeShop.controllers;

import java.util.List;

import javax.validation.Valid;

import com.NLCS.TreeShop.models.CartItem;
import com.NLCS.TreeShop.payload.request.CartItemRequest;
import com.NLCS.TreeShop.payload.request.CartRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.security.services.CartItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartItemController {
	@Autowired
	CartItemService cartService;

	@GetMapping("/getCart/{userId}")
	@PreAuthorize("hasRole('CARTITEM_NORMAL_ACCESS')")
	public ResponseEntity<?> getCart(@PathVariable("userId") Long userId) {
		try {
			List<CartItem> cartItems = cartService.getCart(userId);
			return new ResponseEntity<>(cartItems, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("đã vào CARTITEM");
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/", consumes = { "*/*" })
	@PreAuthorize("hasRole('CARTITEM_NORMAL_ACCESS')")
	public ResponseEntity<?> createCart(@Valid @RequestBody CartRequest cartRequest) {
		System.out.println("x123x");
		return new ResponseEntity<>(cartService.createCart(cartRequest), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{cartItemId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('CARTITEM_NORMAL_ACCESS')")
	public ResponseEntity<?> updateQuantityInCartItem(@PathVariable("cartItemId") Long cartItemId,
			@RequestBody @Valid CartItemRequest cartItemRequest) {
		return new ResponseEntity<>(cartService.updateQuantityInCartItem(cartItemId, cartItemRequest), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasRole('CARTITEM_NORMAL_ACCESS')")
	public ResponseEntity<MessageResponse> hardDeleteCartItem(@PathVariable("id") Long id) {
		try {
			cartService.deteleCartItem(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
