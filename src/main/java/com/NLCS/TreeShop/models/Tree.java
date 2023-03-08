package com.NLCS.TreeShop.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Tree {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long treeId;

	@NotBlank(message = "Name is required")
	private String treeName;

	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Image is required")
	private String imageUrl;

	@NotBlank(message = "Size is required")
	private String size;

	@NotNull
	@Min(0)
	private int stock;

	@NotNull
	@Min(0)
	private int price;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tree_category", joinColumns = @JoinColumn(name = "tree_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	@JoinColumn(name = "supplier_id")
	@ManyToOne
	private Supplier supplier;

	@NotNull
	private boolean status;

	public Tree() {

	}

	public Tree(@NotBlank(message = "Name is required") String treeName,
			@NotBlank(message = "Description is required") String description,
			@NotBlank(message = "Size is required") String size, @NotNull @Min(0) int stock, @NotNull @Min(0) int price,
			Set<Category> categories, Supplier supplier) {
		super();
		this.treeName = treeName;
		this.description = description;
		this.imageUrl = "img.jpg";
		this.size = size;
		this.stock = stock;
		this.price = price;
		this.categories = categories;
		this.supplier = supplier;
		this.status = true;
	}

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
