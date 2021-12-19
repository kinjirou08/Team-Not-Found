package com.revature.caseclothes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.model.Quantities;

@Service
public class ProductsService {

	@Autowired
	private ProductsDAO pd;

	public List<Products> getAllProducts() {
		return pd.getAllProducts();
	}

	public List<Products> getAllProductThatContains(String name) throws ProductNotFoundException {
		List<Products> products = pd.getAllProductThatContains(name);

		if (products.isEmpty()) {
			throw new ProductNotFoundException("There is/are no product/s that matches " + name);
		} else {
			return pd.getAllProductThatContains(name);
		}
	}

	public Products addNewProduct(Products productToAdd) {
		Products p = pd.insertNewProduct(productToAdd);

		return p;
	}

	public Products getProductById(int id) throws ProductNotFoundException {
		Products p = pd.selectProductById(id);

		if (p == null) {
			throw new ProductNotFoundException("No product with the id of " + id);
		} else {
			return pd.selectProductById(id);
		}

	}

	public Carts addProductsToCart(int productId, int quantity) throws ProductNotFoundException {

		Carts c = new Carts();

		Products p = this.getProductById(productId);

		Quantities q = new Quantities(p, quantity);

		List<Quantities> q1 = new ArrayList<>();

		q1.add(q);

		c.setQuantities(q1);

		c = pd.insertToCart(c, q);

		return c;
	}

	public Carts getACart(int id) {

		Carts selectedCart = pd.selectACart(id);

		return selectedCart;
	}

	public Carts addMoreProductsToCart(Carts currentCart, int productId, int quantity) throws ProductNotFoundException {

		Products p = this.getProductById(productId);
		Quantities q = new Quantities(p, quantity);

		List<Quantities> currentQuantitiesInTheCart = currentCart.getQuantities();
		currentQuantitiesInTheCart.add(q);

		currentCart.setQuantities(currentQuantitiesInTheCart);

		currentCart = pd.insertToCart(currentCart, q);

		return currentCart;
	}

}
