package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotBlank;

public class AvatarRequest {
	@NotBlank
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
