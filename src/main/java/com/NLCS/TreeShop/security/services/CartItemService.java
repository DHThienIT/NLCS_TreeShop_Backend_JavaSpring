package com.NLCS.TreeShop.security.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.CartItem;
import com.NLCS.TreeShop.payload.request.CartItemRequest;
import com.NLCS.TreeShop.payload.request.CartRequest;

@Component
public interface CartItemService {
	CartItem createCart(CartRequest cartItemRequest);
	
	void deteleCartItem(long cartItemId);
	
	List<CartItem> getCart(long userId);

	CartItem updateQuantityInCartItem(Long cartItemId, CartItemRequest cartItemRequest);
}
