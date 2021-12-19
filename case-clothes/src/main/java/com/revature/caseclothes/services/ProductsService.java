package com.revature.caseclothes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Products;

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
			throw new ProductNotFoundException("There is/are no product/s that matches "+name);
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
			throw new ProductNotFoundException("No product with the id of " +id);
		} else {
			return pd.selectProductById(id);
		}

	}

}
