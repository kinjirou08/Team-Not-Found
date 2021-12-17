package com.revature.caseclothes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.model.Products;

@Service
public class ProductsService {

	@Autowired
	private ProductsDAO pd;

	public List<Products> getAllProducts() {
		return pd.getAllProducts();
	}

	public List<Products> getAllProductThatContains(String name) {
		return pd.getAllProductThatContains(name);
	}


	public Products addNewProduct(Products productToAdd) {	
		Products p = pd.insertNewProduct(productToAdd);
		
		return p;
	}

	public Products getProductById(int id) {
		Products p = pd.selectProductById(id);
		
		return p;
	}

}
