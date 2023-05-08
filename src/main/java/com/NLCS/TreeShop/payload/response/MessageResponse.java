package com.NLCS.TreeShop.payload.response;

public class MessageResponse {
	private String content;
	private String message;

	public MessageResponse(String message) {
		this.message = message;
	}
	
	public MessageResponse(String content, String message) {
		this.content = content;
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
