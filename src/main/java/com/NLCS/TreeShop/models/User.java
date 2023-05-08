package com.NLCS.TreeShop.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") }) // username, email là 2 cột có giá trị 0 được trùng lặp
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

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
	@Size(max = 13)
	private String phone;

	@NotBlank
	@Size(max = 20)
	private String username;

	@JsonIgnore
	@NotBlank
	@Size(max = 120)
	private String password;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	private String userPhoto;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_trackingListTree", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "favoriteTree_id"))
	private List<Tree> trackingListTree = new ArrayList<>();

	@NotNull
	@JsonIgnore
	private boolean status;

	public User() {
	}

	public User(@NotBlank @Size(min = 2, max = 10) String firstname, @NotBlank @Size(min = 2, max = 10) String lastname,
			@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 13) String phone, @NotBlank @Size(max = 20) String username,
			@NotBlank @Size(max = 120) String password, List<Tree> trackingListTree) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.userPhoto = "img.jpg";
		this.status = true;
		this.trackingListTree = trackingListTree;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Tree> getTrackingListTree() {
		return trackingListTree;
	}

	public void setTrackingListTree(List<Tree> trackingListTree) {
		this.trackingListTree = trackingListTree;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}