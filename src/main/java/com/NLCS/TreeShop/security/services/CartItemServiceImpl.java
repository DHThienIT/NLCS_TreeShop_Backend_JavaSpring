package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.CartItem;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.CartItemRequest;
import com.NLCS.TreeShop.payload.request.CartRequest;
import com.NLCS.TreeShop.repository.CartItemRepository;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.repository.UserRepository;

@Service
public class CartItemServiceImpl implements CartItemService {
	@Autowired
	TreeRepository treeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartItemRepository cartItemRepository;

	@Override
	public List<CartItem> getCart(long userId) {
		return cartItemRepository.findByUser_UserIdAndStatusPay(userId, false);
	}

	@Override
	public CartItem createCart(CartRequest cartItemRequest) {
		CartItem cartItem = cartItemRepository.findByUser_UserIdAndTree_TreeIdAndStatusPay(cartItemRequest.getUser_id(),
				cartItemRequest.getTree_id(), false);

		if (cartItem != null) {
			if (cartItem.getQuantity() + cartItemRequest.getQuantity() <= cartItem.getTree().getStock())
				cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
			else
				throw new InvalidConfigurationPropertyValueException("Tràn kho!", cartItem.getTree().getStock(),
						"Số lượng đặt hàng vượt quá số lượng trong kho dự trữ của sản phẩm");

			return cartItemRepository.save(cartItem);
		} else {
			User user = userRepository.findById(cartItemRequest.getUser_id()).orElseThrow();
			Tree product = treeRepository.findById(cartItemRequest.getTree_id()).orElseThrow();
			CartItem cartItem0 = new CartItem(user, product, cartItemRequest.getQuantity());

			return cartItemRepository.save(cartItem0);
		}
	}

	@Override
	public CartItem updateQuantityInCartItem(Long cartItemId, CartItemRequest cartItemRequest) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("cartItemId", cartItemId, "Not Found"));
		
		if(cartItemRequest.getQuantity() == 0) {
			cartItemRepository.deleteById(cartItemId);
			return null;
		}
		if(cartItemRequest.getQuantity() > cartItem.getTree().getStock())
			throw new InvalidConfigurationPropertyValueException("Tràn kho!", cartItem.getTree().getStock(),
					"Số lượng đặt hàng vượt quá số lượng trong kho dự trữ của sản phẩm");
		
		cartItem.setQuantity(cartItemRequest.getQuantity());
		return cartItemRepository.save(cartItem);
	}

	@Override
	public void deteleCartItem(long cartItemId) {
		// TODO Auto-generated method stub
		if (cartItemRepository.findById(cartItemId).get().getCartItemId().equals(cartItemId)) {
			cartItemRepository.deleteById(cartItemId);
		} else
			throw new InvalidConfigurationPropertyValueException("cartItemId", cartItemId, "Not Found");
	}
}
