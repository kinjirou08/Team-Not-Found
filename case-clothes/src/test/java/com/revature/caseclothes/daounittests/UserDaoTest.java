package com.revature.caseclothes.daounittests;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.dao.UserDAO;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDaoTest {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserDAO sut;
	
	@Test
	@Transactional
	public void testGetUserByUsernameAndPassword() {
		
		//ARRANGE
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh","password1","Tanveer","Singh","tanveersingh@list.com","2607105094","220 West",customer);
		this.em.persist(user);
		
		this.em.flush();
		
		User actual = this.sut.getUsernameAndPassword("tanveer_singh", "password1");
		
		
		User expected = new User("tanveer_singh","password1","Tanveer","Singh","tanveersingh@list.com","2607105094","220 West",customer);
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	
//	@Test
//	@Transactional
//	public void testGetUsernameAndPassword_incorrectPassword() {
//		UserRole customer = new UserRole("customer");
//		this.em.persist(customer);
//		
//		User user = new User("tanveer_singh","password1","Tanveer","Singh","tanveersingh@list.com","2607105094","220 West",customer);
//		this.em.persist(user);
//		
//		this.em.flush();
//		
//		Assertions.assertThrows(DataAccessExcpetion.class, () ->{
//			this.sut.getUsernameAndPassword("tanveer_singh", "password123");
//		}
//	}

}
