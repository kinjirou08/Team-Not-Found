package com.revature.caseclothes.integrationtest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.Products;

@SpringBootTest // loads up the application context
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerTest {

	@Autowired
	private MockMvc mvc; // MockMvc allows for you to send "fake" http requests to the server

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	public void setUp() {

		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Category c1 = new Category("Men's Clothing");
		session.persist(c1);

		Category c2 = new Category("Women's Clothing");
		session.persist(c2);

		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 20.99, c1, "imageURL", 100);
		session.persist(p1);
		Products p2 = new Products("tshirt", "Perfect clothing for warm season", 35.99, c2, "imageURL", 100);
		session.persist(p2);
		Products p3 = new Products("Jacket", "Perfect clothing for cold season", 109.95, c2, "imageURL", 100);
		session.persist(p3);

		tx.commit();

		session.close();
	}

	@Test
	void getAllProducts_PostiveTest() throws Exception {
		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);
		Category c2 = new Category("Women's Clothing");
		c2.setCategoryId(2);

		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 20.99, c1, "imageURL", 100);
		p1.setId(1);
		Products p2 = new Products("tshirt", "Perfect clothing for warm season", 35.99, c2, "imageURL", 100);
		p2.setId(2);
		Products p3 = new Products("Jacket", "Perfect clothing for cold season", 109.95, c2, "imageURL", 100);
		p3.setId(3);

		List<Products> productsList = new ArrayList<>();
		productsList.add(p1);
		productsList.add(p2);
		productsList.add(p3);
		String jsonToSend = mapper.writeValueAsString(productsList);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products").content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 20.99, c1, "imageURL", 100);
		expectedp1.setId(1);
		Products expectedp2 = new Products("tshirt", "Perfect clothing for warm season", 35.99, c2, "imageURL", 100);
		expectedp2.setId(2);
		Products expectedp3 = new Products("Jacket", "Perfect clothing for cold season", 109.95, c2, "imageURL", 100);
		expectedp3.setId(3);

		List<Products> expectedProductsList = new ArrayList<>();
		expectedProductsList.add(expectedp1);
		expectedProductsList.add(expectedp2);
		expectedProductsList.add(expectedp3);
		String expectedJson = mapper.writeValueAsString(expectedProductsList);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}

	@Test
	void getAllProductsThatContains_PostiveTest() throws Exception {

		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);
		Category c2 = new Category("Women's Clothing");
		c2.setCategoryId(2);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products/").param("name", "shirt")
				.contentType(MediaType.APPLICATION_JSON);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 20.99, c1, "imageURL", 100);
		expectedp1.setId(1);
		Products expectedp2 = new Products("tshirt", "Perfect clothing for warm season", 35.99, c2, "imageURL", 100);
		expectedp2.setId(2);

		List<Products> expectedProductsList = new ArrayList<>();
		expectedProductsList.add(expectedp1);
		expectedProductsList.add(expectedp2);

		String expectedJson = mapper.writeValueAsString(expectedProductsList);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}

	@Test
	void getAllProductsThatContains_NoMatch_NegativeTest() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products/").param("name", "asd")
				.contentType(MediaType.APPLICATION_JSON);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(MockMvcResultMatchers.content().string("There is/are no product/s that matches asd"));
	}

	@Test
	void getProductById_PositiveTest() throws Exception {
		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);
		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 20.99, c1, "imageURL", 100);
		p1.setId(1);

		String jsonToSend = mapper.writeValueAsString(p1);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products/{id}", 1).content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 20.99, c1, "imageURL", 100);
		expectedp1.setId(1);

		String expectedJson = mapper.writeValueAsString(expectedp1);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}

	@Test
	void getProductById_NoProductFound_NegativeTest() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products/{id}", 11)
				.contentType(MediaType.APPLICATION_JSON);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(MockMvcResultMatchers.content().string("No product with the id of 11"));
	}

}
