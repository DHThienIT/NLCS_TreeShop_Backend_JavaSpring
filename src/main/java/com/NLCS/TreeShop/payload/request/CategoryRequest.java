package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotBlank;

public class CategoryRequest {
	private String symbol;

	@NotBlank
	private String details;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
