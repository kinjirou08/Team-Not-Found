package com.revature.caseclothes.daounittests;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.revature.caseclothes.CaseClothesApplication;
import com.revature.caseclothes.dao.UserDAO;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

public class UserDaoTest {
	@Autowired
	private EntityManager tem;
	@Autowired
	private UserDAO sut;
	@Test
	@Transactional
	public void testGetUsernameAndPassword_positive(){
	UserRole admin =new UserRole("admin");
		this.tem.persist(admin);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		User actual = this.sut.getUsernameAndPassword("alemu","alemu123");
		User expected = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		expected.setId(1);
		Assertions.assertEquals(expected,actual);
	}
@Test
@Transactional
public void testGetUserByUsernameAndPassword_incorrectpassword() {
	UserRole admin =new UserRole("admin");
	this.tem.persist(admin);
	User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
	this.tem.persist(user);
	this.tem.flush();
 Assertions.assertThrows(DataAccessException.class, () ->{
	  this.sut.getUsernameAndPassword("alemu","alemu1234");
});
}
 @Test
 @Transactional
 public void testgetUserByUsernameAndPassword_incorrectusername() {
	 
	 UserRole admin =new UserRole("admin");
		this.tem.persist(admin);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		 Assertions.assertThrows(DataAccessException.class, () ->{
			  this.sut.getUsernameAndPassword("alemu1","alemu123");
		});
		
 }
 @Test
 @Transactional
 public void testgetUserByUsernameAndPassword_incorrectusername_incorrectpassword() {
	 UserRole admin =new UserRole("admin");
		this.tem.persist(admin);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush(); 
		 Assertions.assertThrows(DataAccessException.class, () ->{
			  this.sut.getUsernameAndPassword("alemu1","alemu1234");
		});
 }

 @Test
 @Transactional
 public void testGetUserByUsername() {
	UserRole admin=new UserRole("admin");
	this.tem.persist(admin);
	UserRole customer = new UserRole("customer");
	this.tem.persist(customer);
	User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
	this.tem.persist(user);
	this.tem.flush(); 
	User actual =  this.sut.getUserByUsername("alemu");
	User expected = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
	expected.setId(1);
	Assertions.assertEquals(expected,actual);
	 
 }
 @Test
 @Transactional
 public void testGetUserByUsername_incorrectusername() {
		UserRole admin=new UserRole("admin");
		this.tem.persist(admin);
		UserRole customer = new UserRole("customer");
		this.tem.persist(customer);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		 Assertions.assertThrows(DataAccessException.class, () ->{
			  this.sut.getUserByUsername("alemu1");
		});
}
 @Test
 @Transactional
 public void tesGettUserById() {
	 UserRole admin=new UserRole("admin");
		this.tem.persist(admin);
		UserRole customer = new UserRole("customer");
		this.tem.persist(customer);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		User actual = this.sut.getUserById(1);
		User expected = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		expected.setId(1);
		Assertions.assertEquals(expected,actual);
	 
 }
 @Test
 @Transactional
 public void testGetUserById_incorectid() {
	 UserRole admin=new UserRole("admin");
		this.tem.persist(admin);
		UserRole customer = new UserRole("customer");
		this.tem.persist(customer);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		User actual = this.sut.getUserById(1);
		
		 Assertions.assertThrows(DataAccessException.class, () ->{
			  this.sut.getUserById(2);
		});
}
 @Test
 @Transactional
 public void testUpdateUserById() {
	 UserRole admin=new UserRole("admin");
		this.tem.persist(admin);
		UserRole customer = new UserRole("customer");
		this.tem.persist(customer);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		User userToBeUpdated=new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		userToBeUpdated.setId(1);
		User actual = this.sut.UpdateUser(1,userToBeUpdated);
		Assertions.assertEquals(userToBeUpdated,actual);
		
 }
 @Test
 @Transactional
 public void testDeletuserById() {
	 UserRole admin=new UserRole("admin");
		this.tem.persist(admin);
		UserRole customer = new UserRole("customer");
		this.tem.persist(customer);
		User user = new User("alemu","alemu123","Alemu","Robele","rob@gmail.com","678-678-6789","1818 Georgia",admin);
		this.tem.persist(user);
		this.tem.flush();
		this.sut.deleteUserByID(1);
		
		User userToBeDeleted =tem.find(User.class,1);
		assertThat(userToBeDeleted).isNull();
 }
 
}