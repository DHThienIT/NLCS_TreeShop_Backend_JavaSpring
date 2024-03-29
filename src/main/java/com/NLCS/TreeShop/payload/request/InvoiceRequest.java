package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotNull;

public class InvoiceRequest {
	@NotNull
	private Long user_id;
	
	@NotNull
	private Long address_id;
	
	@NotNull
	private double shipmentFee;

	@NotNull
	private double promotionPrice;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	public double getShipmentFee() {
		return shipmentFee;
	}

	public void setShipmentFee(double shipmentFee) {
		this.shipmentFee = shipmentFee;
	}

	public double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
}
