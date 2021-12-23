package com.revature.caseclothes.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TransactionKeeper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int checkoutId;

	private String localDateTime;

	@OneToOne
	private Carts carts;

	private double totalPrice;

	private double amountPaid;

	private double change;

	public TransactionKeeper() {
		super();
	}

	public TransactionKeeper(String localDateTime, Carts carts, double totalPrice, double amountPaid) {
		super();
		this.localDateTime = localDateTime;
		this.carts = carts;
		this.totalPrice = totalPrice;
		this.amountPaid = amountPaid;
	}

	public int getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(int checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
	}

	public Carts getCarts() {
		return carts;
	}

	public void setCarts(Carts carts) {
		this.carts = carts;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountPaid, carts, change, checkoutId, localDateTime, totalPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionKeeper other = (TransactionKeeper) obj;
		return Double.doubleToLongBits(amountPaid) == Double.doubleToLongBits(other.amountPaid)
				&& Objects.equals(carts, other.carts)
				&& Double.doubleToLongBits(change) == Double.doubleToLongBits(other.change)
				&& checkoutId == other.checkoutId && Objects.equals(localDateTime, other.localDateTime)
				&& Double.doubleToLongBits(totalPrice) == Double.doubleToLongBits(other.totalPrice);
	}

	@Override
	public String toString() {
		return "TransactionKeeper [checkoutId=" + checkoutId + ", localDateTime=" + localDateTime + ", carts=" + carts
				+ ", totalPrice=" + totalPrice + ", amountPaid=" + amountPaid + ", change=" + change + "]";
	}

}
