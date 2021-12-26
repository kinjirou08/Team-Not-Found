package com.revature.caseclothes.daounittests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.model.Quantities;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductDaoTest {
	
	@Autowired
	private EntityManager em;
	
	
	@Autowired
	private ProductsDAO sut;
	
	@Test
	@Transactional
	public void testGetAllProducts_positive() {
		
		
		Category c1 = new Category("Men's clothing");
		this.em.persist(c1);
		Category c2 = new Category("Mens Casual");
		this.em.persist(c2);
		
		Products p1 = new Products("Backpack","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(p1);
		Products p2 = new Products("T-Shirts","Slim-fitting style",22.3,c2," ",100);
		this.em.persist(p2);
		
		
		List<Products> actual = this.sut.getAllProducts();
		
		Products expected1 = new Products("Backpack","Your perfect pack for everyday",109.95,c1," ",100);
		expected1.setId(1);
		Products expected2 = new Products("T-Shirts","Slim-fitting style",22.3,c2," ",100);
		expected2.setId(2);
		
		List<Products> expectedProducts = new ArrayList<>();
		expectedProducts.add(expected1);
		expectedProducts.add(expected2);
		
		Assertions.assertEquals(expectedProducts, actual);	
	}
	
	@Test
	@Transactional
	public void getAllProductsByName_nameDoesNotExists() {
		
		List<Products> actual = this.sut.getAllProductThatContains("TSHIRT");
		
		List<Products> expected = new ArrayList<>();
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	public void getAllProductsByName_positive() {
		
		//ARRANGE
		
		UserRole ur = new UserRole("customer");
		this.em.persist(ur);
		
		User u = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", ur);
		this.em.persist(u);
		
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		Products p1 = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(p1);
		
		Products p2 = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(p2);
		
		this.em.flush();
		
		//Act
		Products expected1= new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		expected1.setId(1);
		
		Products expected2= new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		expected2.setId(2);
		
		List<Products> expectedList = new ArrayList<>();
		expectedList.add(expected1);
		expectedList.add(expected2);
		
		List<Products> actualList = this.sut.getAllProductThatContains("tshirt");
		
		
		Assertions.assertEquals(expectedList, actualList);	
	}
	
	@Test
	@Transactional
	public void testInsertProducts_positive() {
		
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", admin);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		
		Products productToAdded = this.sut.insertNewProduct(product);
		
		Products expected = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		expected.setId(1);
		
		Assertions.assertEquals(expected, productToAdded);
		
	}
	
	@Test
	@Transactional
	public void getProductById_positive() {
		
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", customer);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		this.em.flush();
		
		Products expected = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		expected.setId(1);
		
		Products actual = this.sut.selectProductById(1);
		
		Assertions.assertEquals(expected, actual);
		
	}
	
	@Test
	@Transactional
	public void updateProduct_positive() {
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", admin);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		this.em.flush();
		
		Products productToBeUpdated = new Products("Jacket","Your perfect pack for everyday",109.95,c1," ",100);
		productToBeUpdated.setId(1);
		
		Products actual = this.sut.updateAProduct(productToBeUpdated);

		Assertions.assertEquals(productToBeUpdated, actual);
	}
	
	@Test
	@Transactional
	public void deleteProductById_positive() {
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", admin);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		this.em.flush();
		this.sut.deleteProductById(1);
		
		Products productToBeDeleted = em.find(Products.class,1 );
		
		assertThat(productToBeDeleted).isNull();
		
	}
	
	@Test
	@Transactional //Happy path
	public void testSelectCartById() {
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", customer);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		this.em.flush();
		
		Quantities q1 = new Quantities(product,4);
		Quantities q2 = new Quantities(product,7);
		Quantities q3 = new Quantities(product,10);
		
		List<Quantities> listOfQuantities = new ArrayList<>();
		listOfQuantities.add(q1);
		listOfQuantities.add(q2);
		listOfQuantities.add(q3);
		
		Carts cart = new Carts(listOfQuantities);
		this.em.persist(cart);
		
		Carts expected = new Carts(listOfQuantities);
		expected.setCartId(1);
		
		Carts actual = this.sut.selectACartById(1);
		
		Assertions.assertEquals(expected, actual);
		
	}
	
	@Test
	@Transactional //Happy path
	public void testInsertToCart() {
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", customer);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		this.em.flush();
		
		Quantities q1 = new Quantities(product,4);
		Quantities q2 = new Quantities(product,7);
		Quantities q3 = new Quantities(product,10);
		
		List<Quantities> listOfQuantities = new ArrayList<>();
		listOfQuantities.add(q1);
		listOfQuantities.add(q2);
		listOfQuantities.add(q3);
		
		Carts cart = new Carts(listOfQuantities);
		this.em.persist(cart);
		
		Carts expected = new Carts(listOfQuantities);
		expected.setCartId(1);
		
		Carts actual = this.sut.insertToCart(cart, q1);
		
		Assertions.assertEquals(expected, actual);
	
	}
	
	@Test
	@Transactional //Happy path
	public void testUpdateProductsInTheCart() {
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", customer);
		this.em.persist(user);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		this.em.flush();
		
		Quantities q1 = new Quantities(product,4);
		Quantities q2 = new Quantities(product,7);
		Quantities q3 = new Quantities(product,10);
		
		List<Quantities> listOfQuantities = new ArrayList<>();
		listOfQuantities.add(q1);
		listOfQuantities.add(q2);
		listOfQuantities.add(q3);
		
		Carts cart = new Carts(listOfQuantities);
		this.em.persist(cart);
		
		Carts expected = new Carts(listOfQuantities);
		expected.setCartId(1);
		
		Carts actual = this.sut.updateProductsInTheCart(cart);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	public void testDeleteAProductInTheCart() {
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh", "password1","tanveer","singh","singhtanveer67@list.com",
				"2608979876","232 West", customer);
		this.em.persist(user);
		
		Carts cart = new Carts(user);
		this.em.persist(cart);
		
		Products product = new Products("tshirt","Your perfect pack for everyday",109.95,c1," ",100);
		this.em.persist(product);
		
		
		
		Quantities q1 = new Quantities(product,4);
		this.em.persist(q1);
		Quantities q2 = new Quantities(product,7);
		this.em.persist(q2);
		Quantities q3 = new Quantities(product,10);
		this.em.persist(q3);
		
		List<Quantities> listOfQuantities = new ArrayList<>();
		listOfQuantities.add(q1);
		listOfQuantities.add(q2);
		listOfQuantities.add(q3);
		
		cart = new Carts(listOfQuantities);
		
		this.em.persist(cart);
		
	
		Quantities quantitiesToBeDeleted = em.find(Quantities.class,1);
		quantitiesToBeDeleted.setQuantityId(1);
		
		//this.em.remove(quantitiesToBeDeleted);
		
		Carts expected = new Carts(listOfQuantities);
		expected.setCartId(1);
		
		System.out.println(expected);
		
		this.em.merge(cart);
		
		Carts actual = this.sut.deleteAProductInTheCart(expected, 1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	
}
