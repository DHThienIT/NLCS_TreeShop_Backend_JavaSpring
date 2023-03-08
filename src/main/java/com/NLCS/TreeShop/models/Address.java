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

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	@NotNull
	private String address;

	@NotNull
	private String phone;

	@NotNull
	private boolean status;

	@JoinColumn(name = "ward_id")
	@ManyToOne
	private Ward ward;

	@JoinColumn(name = "district_id")
	@ManyToOne
	private District district;

	@JoinColumn(name = "city_id")
	@ManyToOne
	private City city;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "address", orphanRemoval = true)
	private List<Invoice> invoices = new ArrayList<>();

	public Address() {
	}

	public Address(@NotNull String address, @NotNull String phone, Ward ward, District district, City city, User user) {
		super();
		this.address = address;
		this.phone = phone;
		this.ward = ward;
		this.district = district;
		this.city = city;
		this.user = user;
		this.status = true;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
