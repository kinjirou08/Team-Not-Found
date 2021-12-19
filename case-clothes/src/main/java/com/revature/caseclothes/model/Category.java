package com.revature.caseclothes.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	private String categories;
	
	public Category() {
		super();
	}

	public Category(String categories) {
		super();
		this.categories = categories;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return categories;
	}

	public void setCategory(String categories) {
		this.categories = categories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categories, categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(categories, other.categories) && categoryId == other.categoryId;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", category=" + categories + "]";
	}	
}
