package com.NLCS.TreeShop.payload.request;

import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.NLCS.TreeShop.models.EColor;

public class TreeRequest {
	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;
	
	@NotBlank(message = "Size is required")
	private String size;

	@NotBlank(message = "Image is required")
	private String imageUrl;

	@NotNull
	@Min(0)
	private int stock;

	@NotNull
	@Min(1000)
	private int price;

	@NotNull
	private Long supplier_id;
	
	@NotNull
	private Set<String> categories;
	
	@Enumerated(EnumType.STRING)
	private EColor color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public EColor getColor() {
		return color;
	}

	public void setColor(EColor color) {
		this.color = color;
	}

	
}
