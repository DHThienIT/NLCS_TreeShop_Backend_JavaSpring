package com.NLCS.TreeShop.payload.request;

public class SetDefaultAddressRequest {
	private Long addressIdWillSetDefault;
	private Long addressIdWasSetDefault;

	public Long getAddressIdWillSetDefault() {
		return addressIdWillSetDefault;
	}

	public void setAddressIdWillSetDefault(Long addressIdWillSetDefault) {
		this.addressIdWillSetDefault = addressIdWillSetDefault;
	}

	public Long getAddressIdWasSetDefault() {
		return addressIdWasSetDefault;
	}

	public void setAddressIdWasSetDefault(Long addressIdWasSetDefault) {
		this.addressIdWasSetDefault = addressIdWasSetDefault;
	}
}
