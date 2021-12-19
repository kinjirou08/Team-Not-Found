package com.revature.caseclothes.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//import com.revature.caseclothes.dto.AddToCartDTO;

@Entity
public class Carts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

	//OneToOne -> User/Customer
	
	@ManyToOne // should be @ManyToMany
	private Products product;
	// private AddToCartDTO product;

//	private int quantity;

	public Carts() {
		super();
	}

	public Carts(Products product/* , int quantity */) {
		super();
		this.product = product;
//		this.quantity = quantity;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carts other = (Carts) obj;
		return cartId == other.cartId && Objects.equals(product, other.product);
	}

	@Override
	public String toString() {
		return "Carts [cartId=" + cartId + ", product=" + product + "]";
	}
	
	

//	public int getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(int quantity) {
//		this.quantity = quantity;
//	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(cartId, product, quantity);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Carts other = (Carts) obj;
//		return cartId == other.cartId && Objects.equals(product, other.product) && quantity == other.quantity;
//	}
//
//	@Override
//	public String toString() {
//		return "Carts [cartId=" + cartId + ", product=" + product + ", quantity=" + quantity + "]";
//	}

}
