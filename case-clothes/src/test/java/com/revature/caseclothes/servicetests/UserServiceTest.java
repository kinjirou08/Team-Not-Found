package com.revature.caseclothes.servicetests;

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

import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.exception.InvalidLoginException;
import com.revature.caseclothes.exception.InvalidParametersException;
import com.revature.caseclothes.exception.UnAuthorizedException;
import com.revature.caseclothes.exception.UserNotFoundException;
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
		expected.setId(2);
		
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
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	public void testGetAllUsers_positive() throws UnAuthorizedException {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		this.em.persist(user);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
		User user2 = new User("j_doe", "password2", "John", "Doe", "j_doe@gmail.com", "6542347754", "4043 Ave", customer);
		User user3 = new User("leah_d", "password3", "Leah", "Doe", "leah_d@gmail.com", "9762436313", "4043 Street", customer);
		
		this.em.persist(user1);
		this.em.persist(user2);
		this.em.persist(user3);
		
		List<User> actual = this.us.getAllUsers(user);
		
		User expected0 = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		expected0.setId(1);
		
		User expected1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
		expected1.setId(2);
		
		User expected2 = new User("j_doe", "password2", "John", "Doe", "j_doe@gmail.com", "6542347754", "4043 Ave", customer);
		expected2.setId(3);
		
		User expected3 = new User("leah_d", "password3", "Leah", "Doe", "leah_d@gmail.com", "9762436313", "4043 Street", customer);
		expected3.setId(4);
		
		List<User> expected = List.of(expected0, expected1, expected2, expected3);
		
		Assertions.assertEquals(expected, actual);
	}
	
//	@Test
//	@Transactional
//	public void testGetAllUsers_negative() throws UnAuthorizedException {
//		UserRole customer = new UserRole("customer");
//		this.em.persist(customer);
//		
//		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
//		this.em.persist(dtsero);
//		
//		User user1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
//		User user2 = new User("j_doe", "password2", "John", "Doe", "j_doe@gmail.com", "6542347754", "4043 Ave", customer);
//		User user3 = new User("leah_d", "password3", "Leah", "Doe", "leah_d@gmail.com", "9762436313", "4043 Street", customer);
//		
//		this.em.persist(user1);
//		this.em.persist(user2);
//		this.em.persist(user3);
//		
//		List<User> actual = this.us.getAllUsers(user);
//		
//		//assert throw
//	}
	
	@Test
	@Transactional
	public void testGetUserByID_positive() throws UnAuthorizedException, UserNotFoundException, InvalidParametersException {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		this.em.persist(user);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
		this.em.persist(user1);
		
		User actual = this.us.getUserByID(user, 2);
		
		User expected = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
		expected.setId(2);
		
		Assertions.assertEquals(expected, actual);
	}
	
//	@Test
//	@Transactional
//	public void testGetUserByID_negative() throws UserNotFoundException, UnAuthorizedException, InvalidParametersException {
//		
//		UserRole customer = new UserRole("customer");
//		this.em.persist(customer);
//		
//		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
//		this.em.persist(user);
//		
//		User user1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
//		
//		this.em.persist(user1);
//		
//		User actual = this.us.getUserByID(user, 2);
//		
//		//assert throw
//	}
	
	@Test
	@Transactional
	public void testGetUserByUsername_positive() throws UnAuthorizedException, UserNotFoundException, InvalidParametersException {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		this.em.persist(user);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
		this.em.persist(user1);
		
		User actual = this.us.getUserByUsername(user, "jane_d");
		
		User expected = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
//	@Test
//	@Transactional
//	public void testGetUserByUsername_negative() throws UserNotFoundException, UnAuthorizedException, InvalidParametersException {
//		
//		UserRole customer = new UserRole("customer");
//		this.em.persist(customer);
//		
//		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
//		this.em.persist(user);
//		
//		User user1 = new User("jane_d", "password1", "Jane", "Doe", "jane_d@gmail.com", "7369273647", "4043 Blvd", customer);
//		
//		this.em.persist(user1);
//		
//		User actual = this.us.getUserByUsername(user, "jane_d);
//		
//		//assert throw
//	}
	
	@Test
	@Transactional
	public void testDeleteUserByID_admin() throws UserNotFoundException {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		this.em.persist(user);
		
		this.us.deleteUserByID(user);
	}
	
	@Test
	@Transactional
	public void testDeleteUserByID_customer() throws UserNotFoundException {
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
		this.em.persist(user);
		
		this.us.deleteUserByID(user);
	}
	
//	@Test
//	@Transactional
//	public void testDeleteUserByID_negative() throws UserNotFoundException {
//		User user = new User();
//		this.em.persist(user);
//		
//		this.us.deleteUserByID(user);
//		
//		//assert throw
//	}
	
//	@Test
//	@Transactional
//	public void testUpdateUserByID() {
//		UserRole customer = new UserRole("customer");
//		this.em.persist(customer);
//		
//		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
//		this.em.persist(user);
//		
//		//Im not sure how to go about this test
//	}
	
	@Test
	@Transactional
	public void testLogin_admin() throws UserNotFoundException, InvalidLoginException {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		this.em.persist(user);
		
		User actual = this.us.login("bach_tran", "password");
		
		User expected = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", admin);
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	public void testLogin_customer() throws UserNotFoundException, InvalidLoginException {
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
		this.em.persist(user);
		
		User actual = this.us.login("bach_tran", "password");
		
		User expected = new User("bach_tran", "password", "Bach", "Tran", "bach_tran@gmail.com", "0000000001", "5432 Ave", customer);
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
//	@Test
//	@Transactional
//	public void testLogin_negative() throws UserNotFoundException, InvalidLoginException {
//		
//		User actual = this.us.login("bach_tran", "password");
//		
//		//assert throw
//	}
	
}
