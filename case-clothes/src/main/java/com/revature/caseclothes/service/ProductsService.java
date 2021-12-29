package com.revature.caseclothes.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.CartNotFoundException;
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

		String convertPriceToString = String.valueOf(productToAdd.getPrice());
		if (productToAdd.getName().equals("")) {
			throw new InvalidParameterException("Name of the product must be included!");
		} else if (productToAdd.getDescription().equals("")) {
			throw new InvalidParameterException("Description of the product must be included!");
		} else if (convertPriceToString.trim().matches("^[a-zA-Z]*$")) {
			throw new InvalidParameterException("Price of the product cannot contain alphabets!");
		} else if (productToAdd.getPrice() <= 0) {
			throw new InvalidParameterException("Price of the product cannot be less than zero");
		} else {
			return pd.insertNewProduct(productToAdd);
		}
	}

	public Products getProductById(String id) throws ProductNotFoundException {

		int productId = Integer.parseInt(id);
		Products p = pd.selectProductById(productId);

		if (p == null) {
			throw new ProductNotFoundException("No product with the id of " + id);
		} else {
			return pd.selectProductById(productId);
		}

	}

	public Carts addMoreProductsToCart(Carts currentCart, String cartId, String productId, String quantity)
			throws ProductNotFoundException, CartNotFoundException {

		currentCart = this.getACartById(cartId);
		Products p = this.getProductById(productId);
		int quantityToBuy = Integer.parseInt(quantity);
		Quantities q = new Quantities(p, quantityToBuy);

		List<Quantities> currentQuantitiesInTheCart = currentCart.getQuantities();
		
		//List<Quantities> addToCart = new ArrayList<>();
		//currentQuantitiesInTheCart.add(q);
		
		boolean checkProduct = checkProductInTheCart(currentQuantitiesInTheCart, p);
		
		if (checkProduct == false) {
			currentQuantitiesInTheCart.add(q);
			currentCart.setQuantities(currentQuantitiesInTheCart);
		} else if (checkProduct == true) {
			for (Quantities q1 : currentQuantitiesInTheCart) {
				if (q1.getProduct() == p) {
					q1.setQuantity(q1.getQuantity() + quantityToBuy);
					q = q1;
				}
			}
		}
		currentCart = pd.insertToCart(currentCart, q);
		return currentCart;
	}

	private boolean checkProductInTheCart(List<Quantities> currentQuantitiesInTheCart, Products p) {
		boolean result = false;
		for (Quantities q1 : currentQuantitiesInTheCart) {
			if (q1.getProduct() == p) {
				result = true;
			}
		}
		return result;
	}

	public Carts getACartById(String id) throws CartNotFoundException {

		int cartId = Integer.parseInt(id);
		Carts selectedCart = pd.selectACartById(cartId);

		if (selectedCart == null) {
			throw new CartNotFoundException("No Cart with the id of " + id);
		} else {
			return pd.selectACartById(cartId);
		}
	}

	public void deleteProductById(String id) {

		int productId = Integer.parseInt(id);
		pd.deleteProductById(productId);

	}

	public Products updateAProduct(String id, Products productToBeUpdated)
			throws ProductNotFoundException, InvalidParameterException {

		int productId = Integer.parseInt(id);
		Products checkProductIfExist = pd.selectProductById(productId);

		if (checkProductIfExist == null) {
			throw new ProductNotFoundException("No product with the id of " + id);
		}
		String convertPriceToString = String.valueOf(productToBeUpdated.getPrice());
		if (productToBeUpdated.getName().equals("")) {
			throw new InvalidParameterException("Name of the product must be included!");
		} else if (productToBeUpdated.getDescription().equals("")) {
			throw new InvalidParameterException("Description of the product must be included!");
		} else if (convertPriceToString.trim().matches("^[a-zA-Z]*$")) {
			throw new InvalidParameterException("Price of the product cannot contain alphabets!");
		} else if (productToBeUpdated.getPrice() <= 0) {
			throw new InvalidParameterException("Price of the product cannot be less than zero");
		} else {
			productToBeUpdated.setId(productId);
		}
		return pd.updateAProduct(productToBeUpdated);

	}

	public Carts updateProductQuantityInCart(Carts currentCart, String cartId, String productId, String quantity)
			throws CartNotFoundException, ProductNotFoundException {

		currentCart = this.getACartById(cartId);
		int prodId = Integer.parseInt(productId);
		int quantityToBuy = Integer.parseInt(quantity);
		List<Quantities> currentProductList = currentCart.getQuantities();
		for (Quantities q : currentProductList) {
			if (q.getProduct().getId() == prodId) {
				q.setQuantity(quantityToBuy);
			} else {
				throw new ProductNotFoundException("Product not found on this cart");
			}
		}
		currentCart.setQuantities(currentProductList);

		currentCart = pd.updateProductsInTheCart(currentCart);

		return currentCart;
	}

	public Carts delteteProductInCart(Carts currentCart, String cartId, String productId)
			throws CartNotFoundException, ProductNotFoundException {

		currentCart = this.getACartById(cartId);
		int prodId = Integer.parseInt(productId);

		List<Quantities> currentProductList = currentCart.getQuantities();
		int quantityToDelete = 0;

		Iterator<Quantities> iter = currentProductList.iterator();
		Quantities q1 = null;
		while (iter.hasNext()) {
			q1 = iter.next();
			if (q1.getProduct().getId() == prodId) {
				iter.remove();
				quantityToDelete = q1.getQuantityId();
				break;
			} else {
				throw new ProductNotFoundException("Product not found on this cart");
			}
		}
		currentCart.setQuantities(currentProductList);

		currentCart = pd.deleteAProductInTheCart(currentCart, quantityToDelete);

		return currentCart;
	}

}
