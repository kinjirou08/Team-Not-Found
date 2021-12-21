package com.revature.caseclothes.controller;

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

import com.revature.caseclothes.exception.CartNotFoundException;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Carts;

import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.service.ProductsService;

@RestController
public class ProductController {

	@Autowired
	private ProductsService ps;

	private final String PATTERN = "[0-9]+";

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
	public ResponseEntity<Object> getProductById(@PathVariable("id") String id) {

		try {
			if (id.matches(PATTERN)) {
				Products p = ps.getProductById(id);
				return ResponseEntity.status(200).body(p);
			} else {
				throw new NumberFormatException("id must be of type int!");
			}
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (NumberFormatException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PostMapping(path = "/products", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addNewProduct(@RequestBody Products productToAdd) {

		Products p = ps.addNewProduct(productToAdd);
		return ResponseEntity.status(201).body(p);
	}

	@DeleteMapping(path = "/products/{id}")
	public ResponseEntity<Object> deleteAProductById(@PathVariable("id") String id) {

		try {
			if (id.matches(PATTERN)) {
				ps.deleteProductById(id);
				return ResponseEntity.status(200).body("Successfully delete the product with the id of " + id);
			} else {
				throw new NumberFormatException("id must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@PutMapping(path = "/products/{id}")
	public ResponseEntity<Object> updateAProductByid(@PathVariable("id") String id,
			@RequestBody Products productToBeUpdated) throws ProductNotFoundException, NumberFormatException {

		try {
			if (id.matches(PATTERN)) {
				Products updateProduct = ps.updateAProduct(id, productToBeUpdated);
				return ResponseEntity.status(200).body(updateProduct);
			} else {
				throw new NumberFormatException("id must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}

	}

	@GetMapping(path = "/carts/{id}")
	public ResponseEntity<Object> getCartById(@PathVariable("id") String id) throws CartNotFoundException {

		try {
			if (id.matches(PATTERN)) {
				Carts c = ps.getACartById(id);
				return ResponseEntity.status(201).body(c);
			} else {
				throw new NumberFormatException("id must be of type int!");
			}
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (NumberFormatException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PostMapping(path = "/carts") // path will be changed to "/user/{id}/carts" once we have the User feature
	public ResponseEntity<Object> addProductsToCart(@RequestParam("productId") String productId,
			@RequestParam("quantity") String quantity) throws ProductNotFoundException {

		try {
			if (productId.matches(PATTERN) || quantity.matches(PATTERN)) {
				Carts c = ps.addProductsToCart(productId, quantity);
				return ResponseEntity.status(201).body(c);
			} else {
				throw new NumberFormatException("product id or quantity must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PostMapping(path = "/carts/{id}")
	public ResponseEntity<Object> addMoreProductToCart(@PathVariable("id") String cartId,
			@RequestParam("productId") String productId, @RequestParam("quantity") String quantity)
			throws ProductNotFoundException, CartNotFoundException {

		try {
			Carts currentCart = null;
			if (cartId.matches(PATTERN) || quantity.matches(PATTERN) || productId.matches(PATTERN)) {
				currentCart = ps.addMoreProductsToCart(currentCart, cartId, productId, quantity);
				return ResponseEntity.status(200).body(currentCart);
			} else {
				throw new NumberFormatException("product id or quantity must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PutMapping(path = "/carts/{id}")
	public ResponseEntity<Object> updateProductQuantityInCart(@PathVariable("id") String cartId,
			@RequestParam("productId") String productId, @RequestParam("quantity") String quantity)
			throws ProductNotFoundException, CartNotFoundException {

		try {
			Carts currentCart = null;
			if (cartId.matches(PATTERN) || quantity.matches(PATTERN) || productId.matches(PATTERN)) {
				currentCart = ps.updateProductQuantityInCart(currentCart, cartId, productId, quantity);
				return ResponseEntity.status(200).body(currentCart);
			} else {
				throw new NumberFormatException("cart id/product id/quantity must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}

	}

	@DeleteMapping(path = "/carts/{id}")
	public ResponseEntity<Object> delteteProductInCart(@PathVariable("id") String cartId,
			@RequestParam("productId") String productId) throws ProductNotFoundException, CartNotFoundException {

		try {
			Carts currentCart = null;
			if (cartId.matches(PATTERN) || productId.matches(PATTERN)) {
				currentCart = ps.delteteProductInCart(currentCart, cartId, productId);
				return ResponseEntity.status(200).body(currentCart);
			} else {
				throw new NumberFormatException("cart id/product id must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}

	}

}
