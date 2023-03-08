package com.NLCS.TreeShop.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserInformationRequest {
	@NotBlank
	@Size(min = 2, max = 10)
	private String firstname;

	@NotBlank
	@Size(min = 2, max = 10)
	private String lastname;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	private String userPhoto;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

}
