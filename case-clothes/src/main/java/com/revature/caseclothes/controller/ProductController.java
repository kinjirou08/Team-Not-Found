package com.revature.caseclothes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.service.ProductsService;

@RestController
public class ProductController {

	@Autowired
	private ProductsService ps;

	@Autowired
	private ProductsDAO pd;

	@GetMapping(path = "/products")
	public List<Products> getAllProducts() {
		return ps.getAllProducts();
	}

	@GetMapping(path = "/products/")
	public ResponseEntity<Object> getAllProductThatContains(@RequestParam("name") String name)
			throws ProductNotFoundException {
		try {
			List<Products> p = ps.getAllProductThatContains(name);

			return ResponseEntity.status(200).body(p);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}

	}

	@GetMapping(path = "/products/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable("id") int id) {
		try {

			Products p = ps.getProductById(id);

			return ResponseEntity.status(200).body(p);

		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}

	}

	@PostMapping(path = "/products", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addNewProduct(@RequestBody Products productToAdd) {

		Products p = ps.addNewProduct(productToAdd);

		return ResponseEntity.status(201).body(p); // Return the newly created object to the client as JSON
		// and also have a status code of 201
	}

	@GetMapping(path = "/carts/{id}")
	public ResponseEntity<Object> getCart(@PathVariable("id") int id) {

		Carts c = ps.getACart(id);

		return ResponseEntity.status(201).body(c);
	}

	@DeleteMapping(path = "/products/{id}")
	public Object deleteAProductById(@PathVariable("id") int id) {

		pd.deleteProductById(id);

		return "Successfully delete the product with the id of " + id;
	}

	@PutMapping(path = "/products/{id}")
	public ResponseEntity<Object> updateAProduct(@PathVariable("id") int id, @RequestBody Products productToBeUpdated) {

		Products updateProduct = pd.updateAProduct(id, productToBeUpdated);

		System.out.println(productToBeUpdated);

		return ResponseEntity.status(200).body(updateProduct);
	}

	@PostMapping(path = "/carts") // path will be changed to "/user/{id}/carts" once we have the User feature
	public ResponseEntity<Object> addProductsToCart(@RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity) throws ProductNotFoundException {

		Carts c = ps.addProductsToCart(productId, quantity);

		return ResponseEntity.status(201).body(c);
	}

	@PutMapping(path = "/carts/{id}")
	public ResponseEntity<Object> addMoreProductToCart(@PathVariable("id") int id,
			@RequestParam("productId") int productId, @RequestParam("quantity") int quantity)
			throws ProductNotFoundException {
		
		Carts currentCart = ps.getACart(id);
		
		currentCart = ps.addMoreProductsToCart(currentCart, productId, quantity);
		
		return ResponseEntity.status(200).body(currentCart);

	}

}
