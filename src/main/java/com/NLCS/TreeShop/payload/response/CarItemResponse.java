package com.NLCS.TreeShop.payload.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.NLCS.TreeShop.models.CartItem;
import com.NLCS.TreeShop.models.Tree;

public class CarItemResponse {
	@Autowired
	CartItem items;
	@Autowired
	Tree products;
	
	public Errors errors;
	public String message;

	public CarItemResponse(CartItem items, Tree products, Errors errors, String message) {
		super();
		this.items = items;
		this.products = products;
		this.errors = errors;
		this.message = message;
	}

	public CartItem getItems() {
		return items;
	}

	public void setItems(CartItem items) {
		this.items = items;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Tree getProducts() {
		return products;
	}

	public void setProducts(Tree products) {
		this.products = products;
	}

}
