package com.NLCS.TreeShop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	@JoinColumn(name = "tree_id")
	@ManyToOne
	private Tree tree;

	@JoinColumn(name = "invoice_id")
	@ManyToOne
	private Invoice invoice;

	@NotNull
	@Min(0)
	private int quantity;

	@NotNull
	private boolean statusPay;

	public CartItem() {
		super();
	}

	public CartItem(User user, Tree tree, @NotNull @Min(0) int quantity) {
		super();
		this.user = user;
		this.tree = tree;
		this.quantity = quantity;
		this.statusPay = false;
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public boolean getStatusPay() {
		return statusPay;
	}

	public void setStatusPay(boolean statusPay) {
		this.statusPay = statusPay;
	}
}
