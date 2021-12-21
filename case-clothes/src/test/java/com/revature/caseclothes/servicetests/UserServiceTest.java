package com.revature.caseclothes.servicetests;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.exception.UnAuthorizedException;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;
import com.revature.caseclothes.service.UserService;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserService us;
	
	@Test
	@Transactional
	public void testAddAdminPositive() throws UnAuthorizedException {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("j_doe", "password1", "John", "Doe", "j_doe@gmail.com", "7367486273", "4042 Blvd", admin);
		this.em.persist(user);
		
		AddUserDTO dto = new AddUserDTO("jane_d", "password2", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Ave");
		this.em.persist(dto);
		
		User actual = this.us.addAdmin(user, dto);
		
		User expected = us.addAdmin(new User("j_doe", "password1", "John", "Doe", "j_doe@gmail.com", "7367486273", "4042 Blvd", admin), new AddUserDTO("jane_d", "password2", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Ave"));
		
		Assertions.assertEquals(expected, actual);
	}
	
//	@Test
//	@Transactional
//	public void testAddAdminNegative() throws UnAuthorizedException {
//		UserRole customer = new UserRole("customer");
//		this.em.persist(customer);
//		
//		User user = new User("j_doe", "password1", "John", "Doe", "j_doe@gmail.com", "7367486273", "4042 Blvd", customer);
//		this.em.persist(user);
//		
//		AddUserDTO dto = new AddUserDTO("jane_d", "password2", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Ave");
//		this.em.persist(dto);
//		
//		this.us.addAdmin(user, dto);
//		
//		//expected throw
//	}
	
	@Test
	@Transactional
	public void testAddCustomer() throws UnAuthorizedException {
		
		AddUserDTO dto = new AddUserDTO("jane_d", "password2", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Ave");
		this.em.persist(dto);
		
		User actual = this.us.addCustomer(dto);
		
		User expected = us.addCustomer(new AddUserDTO("jane_d", "password2", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Ave"));
		
		Assertions.assertEquals(expected, actual);
	}
}
