package com.revature.caseclothes.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//import com.revature.caseclothes.dto.AddToCartDTO;

@Entity
public class Carts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

	// OneToOne -> User/Customer

	@OneToMany
	private List<Quantities> quantities;

	public Carts() {
		super();
	}

	public Carts(List<Quantities> quantities) {
		super();
		this.quantities = quantities;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public List<Quantities> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Quantities> quantities) {
		this.quantities = quantities;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, quantities);
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
		return cartId == other.cartId && Objects.equals(quantities, other.quantities);
	}

	@Override
	public String toString() {
		return "Carts [cartId=" + cartId + ", quantities=" + quantities + "]";
	}
	
}
