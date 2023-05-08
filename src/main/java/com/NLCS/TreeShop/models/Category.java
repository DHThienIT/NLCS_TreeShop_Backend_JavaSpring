package com.NLCS.TreeShop.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@NotBlank(message = "Name is required")
	private String symbol;

	@NotBlank(message = "Detail is required")
	private String detail;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Tree> trees = new HashSet<>();

	public Category() {

	}

	public Category(@NotBlank(message = "Name is required") String symbol, String detail) {
		super();
		this.symbol = symbol;
		this.detail = detail;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
