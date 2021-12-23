package com.revature.caseclothes.serviceunittest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.service.ProductsService;

@ActiveProfiles("ProductService-test")
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTest {

	@Autowired
	ProductsService productsService;

	@Autowired
	ProductsDAO productsDao;

	/*
	 * ProductService's getProductById() test
	 */

	@Test // Happy Path
	void getProductById_PositiveTest() throws ProductNotFoundException {

		Mockito.when(productsDao.selectProductById(1)).thenReturn(new Products("tshirt",
				"Your perfect pack for everyday", 109.95, new Category("Men's Clothing"), " ", 100));

		Products actual = productsService.getProductById("1");

		Assertions.assertEquals(new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), " ", 100), actual);
	}

	@Test // Sad Path
	void getProductById_Negative() {

		Assertions.assertThrows(ProductNotFoundException.class, () -> {

			productsService.getProductById("1");

		});
	}

	/*
	 * ProductService's getAllProducts() test
	 */

	@Test // Happy Path
	void getAllProducts_PositiveTest() {

		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 109.95, new Category("Men's Clothing"),
				"imageURL", 100);
		p1.setId(1);
		Products p2 = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		p2.setId(2);

		List<Products> productList = new ArrayList<>();
		productList.add(p1);
		productList.add(p2);

		Mockito.when(productsDao.getAllProducts()).thenReturn(productList);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), "imageURL", 100);
		expectedp1.setId(1);
		Products expectedp2 = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		expectedp2.setId(2);

		List<Products> expectedList = new ArrayList<>();
		expectedList.add(expectedp1);
		expectedList.add(expectedp2);

		List<Products> actual = productsService.getAllProducts();

		Assertions.assertEquals(expectedList, actual);
	}

	/*
	 * ProductsService's getAllProductThatContains() test
	 */

	@Test // Happy Path
	void getAllProductThatContains_PositiveTest() throws ProductNotFoundException {

		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 109.95, new Category("Men's Clothing"),
				"imageURL", 100);
		p1.setId(1);
		Products p2 = new Products("tshirt", "Perfect clothing for warm season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		p2.setId(2);

		List<Products> productList = new ArrayList<>();
		productList.add(p1);
		productList.add(p2);

		Mockito.when(productsDao.getAllProductThatContains("shirt")).thenReturn(productList);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), "imageURL", 100);
		expectedp1.setId(1);
		Products expectedp2 = new Products("tshirt", "Perfect clothing for warm season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		expectedp2.setId(2);

		List<Products> expectedList = new ArrayList<>();
		expectedList.add(expectedp1);
		expectedList.add(expectedp2);

		List<Products> actual = productsService.getAllProductThatContains("shirt");

		Assertions.assertEquals(expectedList, actual);
	}
	
	@Test // Sad Path
	void getAllProductThatContains_NoValidProduct() {
		
		Assertions.assertThrows(ProductNotFoundException.class, () -> {
			
			productsService.getAllProductThatContains("shirt");
			
		});	
	}
	
	

}
