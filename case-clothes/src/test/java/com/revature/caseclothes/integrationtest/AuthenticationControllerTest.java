package com.revature.caseclothes.integrationtest;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.caseclothes.dto.LoginDTO;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;

@SpringBootTest // loads up the application context
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthenticationControllerTest {

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

		UserRole customer = new UserRole("customer");
		session.persist(customer);

		UserRole admin = new UserRole("admin");
		session.persist(admin);

		User jymmAdmin = new User("jymm.enriquez", "password", "Jymm", "Enriquez", "jymm.enriquez@gmail.com",
				"3615491234", "123 Main Street", admin);
		session.persist(jymmAdmin);

		User jymmCustomer = new User("jymm.customer", "password", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"3614598753", "321 Other Street", customer);
		session.persist(jymmCustomer);

		tx.commit();

		session.close();
	}

	@Test
	public void testLogin_admin() throws Exception {

		LoginDTO loginCred = new LoginDTO("jymm.enriquez", "password");
		String jsonToSend = mapper.writeValueAsString(loginCred);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login").content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);

		UserRole expectedUserRole = new UserRole("admin");
		expectedUserRole.setId(2);

		User expectedUser = new User("jymm.enriquez", "password", "Jymm", "Enriquez", "jymm.enriquez@gmail.com",
				"3615491234", "123 Main Street", expectedUserRole);
		expectedUser.setId(1);

		String expectedJson = mapper.writeValueAsString(expectedUser);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}
	
	@Test
	public void testLogin_customer() throws Exception {

		LoginDTO loginCred = new LoginDTO("jymm.customer", "password");
		String jsonToSend = mapper.writeValueAsString(loginCred);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login").content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);

		UserRole expectedUserRole = new UserRole("customer");
		expectedUserRole.setId(1);

		User expectedUser = new User("jymm.customer", "password", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"3614598753", "321 Other Street", expectedUserRole);
		expectedUser.setId(2);

		String expectedJson = mapper.writeValueAsString(expectedUser);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}
	
	@Test
	public void testLogin_correctUsername_invalidPassword() throws Exception {
		/*
		 * Arrange
		 */
		LoginDTO dto = new LoginDTO("jymm.customer", "obviouslyincorrectpassword");
		String jsonToSend = mapper.writeValueAsString(dto);
		
		/*
		 * Act and Assert
		 */
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login").content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);
		
		
		this.mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400))
			.andExpect(MockMvcResultMatchers.content().string("Username and/or password is incorrect"));
	}

	@Test
	public void testLoginStatus_loggedIn() throws Exception {
		/*
		 * Arrange
		 */
		UserRole fakeUserRole = new UserRole("admin");
		fakeUserRole.setId(1);
		
		User fakeUser = new User("jymm.enriquez", "password", "Jymm", "Enriquez", "jymm.enriquez@gmail.com",
				"3615491234", "123 Main Street", fakeUserRole);
		fakeUser.setId(1);
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("currentuser", fakeUser);
		
		/*
		 * ACT
		 */
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/loginstatus").session(session);
		
		String expectedJsonUser = mapper.writeValueAsString(fakeUser);
		
		this.mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(200))
			.andExpect(MockMvcResultMatchers.content().json(expectedJsonUser));
		
	}
}
