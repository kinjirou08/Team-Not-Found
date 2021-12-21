package com.revature.caseclothes.daounittest;

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
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.Products;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductsDAOTest {

	@Autowired
	private EntityManager em; // we will use this to populate the initial data for each test

	@Autowired
	private ProductsDAO sut; // system under test

	@Test
	@Transactional
	void testGetAllProducts() {

		Category c = new Category("Men's Clothing");
		this.em.persist(c);
		Category c2 = new Category("Women's Clothing");
		this.em.persist(c2);

		Products p = new Products("Louis Vuitton", "Designer Bag", 269.99, c2,
				"https://themariemarcele.com/wp-content/uploads/2021/06/Q98UbTEU-600x600.jpg", 100);
		this.em.persist(p);
		Products p2 = new Products("Louis Vuitton", "Designer Bag", 269.99, c2,
				"https://themariemarcele.com/wp-content/uploads/2021/06/Q98UbTEU-600x600.jpg", 100);
		this.em.persist(p2);
		
		this.em.flush();
		
		Products expected1 = new Products("Louis Vuitton", "Designer Bag", 269.99, c2,
				"https://themariemarcele.com/wp-content/uploads/2021/06/Q98UbTEU-600x600.jpg", 100);
		expected1.setId(1);
		Products expected2 = new Products("Louis Vuitton", "Designer Bag", 269.99, c2,
				"https://themariemarcele.com/wp-content/uploads/2021/06/Q98UbTEU-600x600.jpg", 100);
		expected2.setId(2);
		
		List<Products> expectedList = List.of(expected1, expected2);
		
		List<Products> actualList = this.sut.getAllProducts();

		Assertions.assertEquals(expectedList, actualList);

	}

}
