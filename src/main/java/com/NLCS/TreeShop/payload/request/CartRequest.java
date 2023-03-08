package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartRequest {
	@NotNull
	private Long user_id;

	@NotNull
	private Long tree_id;

	@NotNull
	@Min(1)
	private int quantity;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getTree_id() {
		return tree_id;
	}

	public void setTree_id(Long tree_id) {
		this.tree_id = tree_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
