package com.revature.caseclothes.serviceunittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.service.ProductsService;

@ActiveProfiles("ProductService-test")
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	ProductsService productsService;

	@Autowired
	ProductsDAO productsDao;

	@Test
	void getProductById() throws ProductNotFoundException {

		Mockito.when(productsDao.selectProductById(1)).thenReturn(new Products("tshirt",
				"Your perfect pack for everyday", 109.95, new Category("Men's Clothing"), " ", 100));

		Products actual = productsService.getProductById("1");

		Assertions.assertEquals(new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), " ", 100), actual);
	}
	
	@Test
	void getProductById_Negative() {
		
		Assertions.assertThrows(ProductNotFoundException.class, () -> {

			productsService.getProductById("1");	
			
		});
	}

}
