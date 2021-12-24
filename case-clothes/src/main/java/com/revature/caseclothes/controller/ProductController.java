package com.revature.caseclothes.controller;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.CartNotFoundException;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.model.Quantities;
import com.revature.caseclothes.model.TransactionKeeper;
import com.revature.caseclothes.service.ProductsService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductsService ps;

	@Autowired
	ProductsDAO pd;

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
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@PostMapping(path = "/products", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addNewProduct(@RequestBody Products productToAdd) {

		try {
			Products p = ps.addNewProduct(productToAdd);
			return ResponseEntity.status(201).body(p);
		} catch (InvalidParameterException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
		
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
			@RequestBody Products productToBeUpdated) throws NumberFormatException {

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
		} catch (InvalidParameterException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@GetMapping(path = "/carts/{id}")
	public ResponseEntity<Object> getCartById(@PathVariable("id") String id) throws CartNotFoundException {
		/*
		 * checkout button -> show list of products, in Angular side just get: Carts: {
		 * User: { first name, last name } Quantities: { Product: { name: price: }
		 * quantity: } }
		 * 
		 */
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

	@PostMapping(path = "/cart/{id}/checkout") // Pay Now button test
	public ResponseEntity<Object> checkoutTest(@PathVariable("id") String id, @RequestParam("amountPaid") String amount)
			throws CartNotFoundException {

		Carts getCartById = ps.getACartById(id);

		// computing for the total price for each product
		DecimalFormat df = new DecimalFormat("#.##");
		double price = 0.0;
		double totalPrice = 0.0;
		List<Quantities> q = getCartById.getQuantities();
		for (Quantities q1 : q) {
			price = q1.getProduct().getPrice() * q1.getQuantity();
			System.out.println(q1.getProduct().getId() + ": " + price);
			totalPrice = totalPrice + price;
			System.out.println(df.format(totalPrice));
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		String transactionTime = dtf.format(now);

		TransactionKeeper testing = new TransactionKeeper(transactionTime, getCartById,
				Double.parseDouble(df.format(totalPrice)), Double.parseDouble(amount));

		return ResponseEntity.status(200).body(testing);
	}
}
