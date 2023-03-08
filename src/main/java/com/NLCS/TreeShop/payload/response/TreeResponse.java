package com.NLCS.TreeShop.payload.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.NLCS.TreeShop.models.Tree;

public class TreeResponse {
	@Autowired Tree product;
	public Errors errors;
	public String message;
	
	
	public TreeResponse(Tree product, Errors errors, String message) {
		super();
		this.product = product;
		this.errors = errors;
		this.message = message;
	}
	public Tree getProduct() {
		return product;
	}
	public void setProduct(Tree product) {
		this.product = product;
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
	
	
}
