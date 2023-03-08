package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotNull;

public class SupplierRequest {
	@NotNull
	private String supplierName;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
