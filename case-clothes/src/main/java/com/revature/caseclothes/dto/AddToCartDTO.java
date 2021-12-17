package com.revature.caseclothes.dto;

import java.util.Objects;

public class AddToCartDTO {

	private int id;
	private int quantity;

	public AddToCartDTO() {
		super();
	}

	public AddToCartDTO(int id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddToCartDTO other = (AddToCartDTO) obj;
		return id == other.id && quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "AddToCartProductsDTO [id=" + id + ", quantity=" + quantity + "]";
	}

}
