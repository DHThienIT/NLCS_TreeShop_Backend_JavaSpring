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
public class CountryAndDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryAndDistrictId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ward", orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public CountryAndDistrict() {
    	super();
    }

    public CountryAndDistrict(String name) {
    	super();
        this.name = name;
    }

	public Long getCountryAndDistrictId() {
		return countryAndDistrictId;
	}

	public void setCountryAndDistrictId(Long countryAndDistrictId) {
		this.countryAndDistrictId = countryAndDistrictId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
