package com.NLCS.TreeShop.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProvinceAndCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long provinceAndCityId;

    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ward", orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public ProvinceAndCity() {
		super();
	}
    
	public ProvinceAndCity(String name) {
		super();
		this.name = name;
	}

	public Long getProvinceAndCityId() {
		return provinceAndCityId;
	}

	public void setProvinceAndCityId(Long provinceAndCityId) {
		this.provinceAndCityId = provinceAndCityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
