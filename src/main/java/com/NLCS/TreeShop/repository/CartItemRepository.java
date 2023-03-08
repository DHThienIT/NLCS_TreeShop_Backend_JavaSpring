package com.NLCS.TreeShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByUser_UserId(long userId);

	CartItem findByUser_UserIdAndTree_TreeIdAndStatusPay(long user_id, long product_id, Boolean status);

	List<CartItem> findByUser_UserIdAndStatusPay(long userId, boolean b);
}
