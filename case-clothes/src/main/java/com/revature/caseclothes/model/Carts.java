package com.revature.caseclothes.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//import com.revature.caseclothes.dto.AddToCartDTO;

@Entity
public class Carts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

	@OneToOne
	private User user;

	@OneToMany
	private List<Quantities> quantities;

	public Carts() {
		super();
	}

	public Carts(List<Quantities> quantities) {
		super();
		this.quantities = quantities;
	}
	
	public Carts(User user) {
		super();
		this.user = user;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Quantities> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Quantities> quantities) {
		this.quantities = quantities;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, quantities, user);
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
		return cartId == other.cartId && Objects.equals(quantities, other.quantities)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Carts [cartId=" + cartId + ", user=" + user + ", quantities=" + quantities + "]";
	}


}
