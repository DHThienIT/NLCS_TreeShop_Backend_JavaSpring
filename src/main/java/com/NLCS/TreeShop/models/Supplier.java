package com.NLCS.TreeShop.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long supplierId;

	@NotBlank(message = "Name is required")
	private String supplierName;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier", orphanRemoval = true)
	private List<Tree> trees = new ArrayList<>();
	
	private Long address_id;
	
	@NotNull
	@JsonIgnore
	private boolean status;
	
	public Supplier() {

	}

	public Supplier(@NotBlank(message = "Name is required") String supplierName) {
		super();
		this.supplierName = supplierName;
		this.status = true;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public Long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<Tree> getTrees() {
		return trees;
	}

	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
