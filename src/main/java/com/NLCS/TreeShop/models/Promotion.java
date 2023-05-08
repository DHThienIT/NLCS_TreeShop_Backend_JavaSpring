package com.NLCS.TreeShop.models;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Promotion {
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long promotionId;

	@NotNull
	private String promotionName;

	@NotNull
	@JsonIgnore
	@Column(length = 10)
	private String code;

	@NotNull
	private Calendar startTime;

	@NotNull
	private Calendar endTime;

	@NotNull
	private String promotionalPrice;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "promotion_tree", joinColumns = @JoinColumn(name = "promotion_id"), inverseJoinColumns = @JoinColumn(name = "tree_id"))
	private Set<Tree> trees = new HashSet<>();

	public Promotion() {
		super();
	}

	public Promotion(@NotNull String promotionName, @NotNull String code, @NotNull Calendar startTime,
			@NotNull Calendar endTime, @NotNull String promotionalPrice) {
		super();
		this.promotionName = promotionName;
		this.code = code;
		this.startTime = startTime;
		this.endTime = endTime;
		this.promotionalPrice = promotionalPrice;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getPromotionalPrice() {
		return promotionalPrice;
	}

	public void setPromotionalPrice(String promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}

	public Set<Tree> getTrees() {
		return trees;
	}

	public void setTrees(Set<Tree> trees) {
		this.trees = trees;
	}
}
