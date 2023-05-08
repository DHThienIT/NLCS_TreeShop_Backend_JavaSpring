package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotNull;

public class AddressRequest {
	@NotNull
	private Long user_id;
	
	@NotNull
	private String recipientName;

	@NotNull
	private String specificAddress;

	@NotNull
	private String phone;

	@NotNull
	private Long ward_id;

	@NotNull
	private Long countryAndDistrict_id;

	@NotNull
	private Long provinceAndCity_id;
	
	@NotNull
	private boolean setDefault;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getSpecificAddress() {
		return specificAddress;
	}

	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getWard_id() {
		return ward_id;
	}

	public void setWard_id(Long ward_id) {
		this.ward_id = ward_id;
	}

	public Long getCountryAndDistrict_id() {
		return countryAndDistrict_id;
	}

	public void setCountryAndDistrict_id(Long countryAndDistrict_id) {
		this.countryAndDistrict_id = countryAndDistrict_id;
	}

	public Long getProvinceAndCity_id() {
		return provinceAndCity_id;
	}

	public void setProvinceAndCity_id(Long provinceAndCity_id) {
		this.provinceAndCity_id = provinceAndCity_id;
	}

	public boolean getSetDefault() {
		return setDefault;
	}

	public void setSetDefault(boolean setDefault) {
		this.setDefault = setDefault;
	}
}
