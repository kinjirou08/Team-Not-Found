package com.revature.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserCheckout {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//@OneToOne
	//private User userId;
	
	private double total_price;
	
	private Date date;

	public UserCheckout() {
		super();
	}

	public UserCheckout(int id, double total_price, Date date) {
		super();
		this.id = id;
		this.total_price = total_price;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, total_price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCheckout other = (UserCheckout) obj;
		return Objects.equals(date, other.date) && id == other.id
				&& Double.doubleToLongBits(total_price) == Double.doubleToLongBits(other.total_price);
	}

	@Override
	public String toString() {
		return "UserCheckout [id=" + id + ", total_price=" + total_price + ", date=" + date + "]";
	}
	
	
	
	

}
