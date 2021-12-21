package com.revature.caseclothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caseclothes.dao.ProductsDAO;
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
	public List<Products> getAllProductThatContains(@RequestParam("name") String name) {
		return ps.getAllProductThatContains(name); 
	}
		
}
