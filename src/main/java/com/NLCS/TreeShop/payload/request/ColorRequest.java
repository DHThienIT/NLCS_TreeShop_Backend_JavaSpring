package com.NLCS.TreeShop.payload.request;

import java.util.List;

import com.NLCS.TreeShop.models.EColor;

public class ColorRequest {
	private List<EColor> listColor;

	public List<EColor> getListColor() {
		return listColor;
	}

	public void setListColor(List<EColor> listColor) {
		this.listColor = listColor;
	}
}
