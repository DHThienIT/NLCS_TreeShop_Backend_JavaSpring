package com.NLCS.TreeShop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class DeliveryMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliveryMethodId;

	@NotBlank(message = "Details is required")
	private String details;
	
	@NotBlank
	private Double price;
	
	@NotBlank
	private String estimatedTime;

	public DeliveryMethod() {

	}

	public DeliveryMethod(@NotBlank(message = "Details is required") String details,
			@NotBlank Double price, @NotBlank String estimatedTime) {
		super();
		this.details = details;
		this.price = price;
		this.estimatedTime = estimatedTime;
	}

	public Long getDeliveryMethodId() {
		return deliveryMethodId;
	}

	public void setDeliveryMethodId(Long deliveryMethodId) {
		this.deliveryMethodId = deliveryMethodId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
}
