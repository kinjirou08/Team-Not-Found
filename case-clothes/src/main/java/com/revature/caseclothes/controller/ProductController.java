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

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.dto.AddToCartDTO;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.services.ProductsService;

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
	public List<Products> getAllProductThatContains(@RequestParam("name") String name) {
		return ps.getAllProductThatContains(name);
	}

	@GetMapping(path = "/products/{id}")
	public Products getProductById(@PathVariable("id") int id) {

		return ps.getProductById(id);

	}

	@PostMapping(path = "/products", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addNewUser(@RequestBody Products productToAdd) {

		Products p = ps.addNewProduct(productToAdd);

		return ResponseEntity.status(201).body(p); // Return the newly created object to the client as JSON
		// and also have a status code of 201
	}

	@PostMapping(path = "/carts")
	public ResponseEntity<Object> addProductsToCart(@RequestBody Products searchProduct) {

		Products p = ps.getProductById(searchProduct.getId());
		AddToCartDTO addProductToCart = new AddToCartDTO(p.getId(), 1);

		Carts carts = new Carts(addProductToCart);
		Carts insertedProduct = pd.insertProductToCart(carts);

		System.out.println(insertedProduct);

		return ResponseEntity.status(201).body(insertedProduct);
	}

	@GetMapping(path = "/carts/{id}")
	public ResponseEntity<Object> getCart(@PathVariable("id") int id) {

		Carts c = pd.selectACart(id);

		return ResponseEntity.status(201).body(c);
	}

	@DeleteMapping(path = "/products/{id}")
	public Object deleteAProductById(@PathVariable("id") int id) {

		pd.deleteProductById(id);

		return "Successfully delete the product with the id of " + id;
	}

	@PutMapping(path = "/products/{id}")
	public ResponseEntity<Object> updateAProduct(@PathVariable("id") int id,
			@RequestBody Products productToBeUpdated) {

		Products updateProduct = pd.updateAProduct(id, productToBeUpdated);
		
		System.out.println(productToBeUpdated);
		

		return ResponseEntity.status(200).body(updateProduct);
	}

}
