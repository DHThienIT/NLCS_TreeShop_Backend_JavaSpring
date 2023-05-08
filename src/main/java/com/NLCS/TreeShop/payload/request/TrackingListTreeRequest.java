package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.NotNull;

public class TrackingListTreeRequest {
	@NotNull
	private Long user_id;
	@NotNull
	private Long tree_id;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getTree_id() {
		return tree_id;
	}

	public void setTree_id(Long tree_id) {
		this.tree_id = tree_id;
	}
}
