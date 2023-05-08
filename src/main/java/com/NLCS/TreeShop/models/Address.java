package com.NLCS.TreeShop.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	@NotNull
	private String name;

	@NotNull
	private String specificAddress;

	@NotNull
	private String phone;

	@NotNull
	@JsonIgnore
	private boolean status;

	@NotNull
	private boolean setDefault;

	@JoinColumn(name = "ward_id")
	@ManyToOne
	private Ward ward;

	@JoinColumn(name = "countryAndDistrict_id")
	@ManyToOne
	private CountryAndDistrict countryAndDistrict;

	@JoinColumn(name = "provinceAndCity_id")
	@ManyToOne
	private ProvinceAndCity provinceAndCity;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "address", orphanRemoval = true)
	private List<Invoice> invoices = new ArrayList<>();

	public Address() {
	}

	public Address(@NotNull String name, @NotNull String specificAddress, @NotNull String phone, Ward ward,
			CountryAndDistrict countryAndDistrict, ProvinceAndCity provinceAndCity, User user, boolean setDefault) {
		super();
		this.name = name;
		this.specificAddress = specificAddress;
		this.phone = phone;
		this.ward = ward;
		this.countryAndDistrict = countryAndDistrict;
		this.provinceAndCity = provinceAndCity;
		this.user = user;
		this.status = true;
		this.setDefault = setDefault;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public CountryAndDistrict getCountryAndDistrict() {
		return countryAndDistrict;
	}

	public void setCountryAndDistrict(CountryAndDistrict countryAndDistrict) {
		this.countryAndDistrict = countryAndDistrict;
	}

	public ProvinceAndCity getProvinceAndCity() {
		return provinceAndCity;
	}

	public void setProvinceAndCity(ProvinceAndCity provinceAndCity) {
		this.provinceAndCity = provinceAndCity;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isDefault() {
		return setDefault;
	}

	public void setDefault(boolean setDefault) {
		this.setDefault = setDefault;
	}

}
