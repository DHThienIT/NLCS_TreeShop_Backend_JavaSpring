package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotNull;

public class SupplierRequest {
	@NotNull
	private String supplierName;
	@NotNull
	private String addressId;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

}
