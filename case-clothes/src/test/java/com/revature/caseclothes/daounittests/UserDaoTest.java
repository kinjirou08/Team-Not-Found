package com.revature.caseclothes.daounittests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.dao.UserDao;
import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDaoTest {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserDao sut;
	
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
	
	
	@Test
	@Transactional
	public void testGetUsernameAndPassword_incorrectPassword() {
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User user = new User("tanveer_singh","password1","Tanveer","Singh","tanveersingh@list.com","2607105094","220 West",customer);
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () ->{
			this.sut.getUsernameAndPassword("tanveer_singh", "password1234");
		});
	}
	
	
	@Test
	@Transactional  //Happy Path
	public void testAddCustomer() {
		
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		AddUserDTO userDto = new AddUserDTO("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test");
		
		Carts cart = new Carts();
		this.em.persist(cart);
		
		this.em.flush();
		
		User expected = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",customer);
		expected.setId(1);
		
		User actual = this.sut.addCustomer(userDto, cart);
		
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional  //Happy Path
	public void testAddAdmin() {
		
		Category c1 = new Category("Men's Clothing");
		this.em.persist(c1);
		
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		AddUserDTO userDto = new AddUserDTO("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test");
		
		Carts cart = new Carts();
		this.em.persist(cart);
		
		this.em.flush();
		
		User expected = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		expected.setId(1);
		
		User actual = this.sut.addAdmin(userDto);
		
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional  //Happy Path
	public void testGetAllUsers() {
		
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User u1 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		this.em.persist(u1);
		User u2 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		this.em.persist(u2);
		User u3 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",customer);
		this.em.persist(u3);
		User u4 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",customer);
		this.em.persist(u4);
		
		this.em.flush();
		
		List<User> actual = this.sut.getAllUsers();
		
		User expected1 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		expected1.setId(1);
		User expected2 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		expected2.setId(2);
		User expected3 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",customer);
		expected3.setId(3);
		User expected4 = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",customer);
		expected4.setId(4);
	
		List<User> expectedUsers = new ArrayList<>();
		expectedUsers.add(expected1);
		expectedUsers.add(expected2);
		expectedUsers.add(expected3);
		expectedUsers.add(expected4);
		
		Assertions.assertEquals(expectedUsers, actual);
	}
	
	@Test
	@Transactional  //Happy Path
	public void testGetUserById() {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User u = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		this.em.persist(u);
		
		this.em.flush();
		
		User actual = this.sut.getUserByID(1);
		
		User expected = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional  //Happy Path
	public void testGetUserByUsername() {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User u = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		this.em.persist(u);
		
		this.em.flush();
		
		User actual = this.sut.getUserByUsername("tanveer_singh");
		
		User expected = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	public void testDeleteUserById() {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User u = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		this.em.persist(u);
		
		this.em.flush();
		
		this.sut.deleteUserByID(1);
		
		User userToBeDeleted = em.find(User.class, 1);
		
		assertThat(userToBeDeleted).isNull();
	}
	
	@Test
	@Transactional
	public void updateUserById() {
		UserRole admin = new UserRole("admin");
		this.em.persist(admin);
		
		UserRole customer = new UserRole("customer");
		this.em.persist(customer);
		
		User u = new User("tanveer_singh","password12","Tanveer","Singh","t_singh@list.com","3458976","345 Test",admin);
		this.em.persist(u);
		
		this.em.flush();
		
		User userToBeUpdated = new User("yudhveer_singh", "password12345","Yudhveer","Singh","yudhveer@list.com","3458976","345 Test",admin);
		userToBeUpdated.setId(1);
		
		User actual = this.sut.UpdateUser(1, userToBeUpdated);
		
		Assertions.assertEquals(userToBeUpdated, actual);
	}
	
	
}
