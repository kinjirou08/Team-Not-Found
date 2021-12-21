package com.revature.caseclothes.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quantities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int quantityId;

	@ManyToOne
	private Products product;

	private int quantity;

	public Quantities() {
		super();
		}

	public Quantities(Products product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public int getQuantityId() {
		return quantityId;
	}

	public void setQuantityId(int quantityId) {
		this.quantityId = quantityId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(product, quantity, quantityId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quantities other = (Quantities) obj;
		return Objects.equals(product, other.product) && quantity == other.quantity && quantityId == other.quantityId;
	}

	@Override
	public String toString() {
		return "Quantities [quantityId=" + quantityId + ", product=" + product + ", quantity=" + quantity + "]";
	}
}

