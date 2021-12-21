package com.revature.caseclothes.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.model.UserRole;

@Repository
public class UserDao {
	@PersistenceContext
	private EntityManager em;

	// Add a new Customer
		@Transactional
		public User addCustomer(AddUserDTO dto, Carts c) {
			UserRole customer = (UserRole) em.createQuery("SELECT a FROM UserRole a WHERE a.role = :role")
					.setParameter("role", "customer")
					.getSingleResult();

			User userToAdd = new User(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
					dto.getEmail(), dto.getPhoneNumber(), dto.getAddress(), customer);

			em.persist(userToAdd);
			
			c = new Carts(userToAdd);
			em.persist(c);
			
			return userToAdd;
		}

		// Add a new Admin
		@Transactional
		public User addAdmin(AddUserDTO dto) {
			UserRole admin = (UserRole) em.createQuery("SELECT a FROM UserRole a WHERE a.role = :role")
					.setParameter("role", "admin")
					.getSingleResult();

			User userToAdd = new User(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
					dto.getEmail(), dto.getPhoneNumber(), dto.getAddress(), admin);

			em.persist(userToAdd);

			return userToAdd;
		}

	// Get all users
	@Transactional
	public List<User> getAllUsers() {
		List<User> users = em.createQuery("FROM User a", User.class).getResultList();

		return users;
	}

	// Get User by ID
	@Transactional
	public User getUserByID(int id) {
		User user = em.createQuery("SELECT u FROM User u WHERE u.id = :userid", User.class).setParameter("userid", id)
				.getSingleResult();

		return user;
	}

	// Get User by Username
	@Transactional
	public User getUserByUsername(String username) {
		User user = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class).setParameter("user", username)
				.getSingleResult();

		return user;
	}

	// Delete User by ID
	@Transactional
	public void deleteUserByID(int id) {
		
		User u = em.find(User.class, id);
		
		em.remove(u);
	}

	// Update current User information
	@Transactional
	public User UpdateUser(int id, User updatedUserInfo) {
		
		Session session = em.unwrap(Session.class);
		
		User currentlyLoggedIn = session.find(User.class, id);
		
		currentlyLoggedIn = updatedUserInfo;
		
		session.merge(currentlyLoggedIn);

		return currentlyLoggedIn;
	}

	@Transactional
	public User getUsernameAndPassword(String username, String password) {
		User user = em.createQuery("FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
				.setParameter("user", username)
				.setParameter("pass", password)
				.getSingleResult();
		
		return user;
	}

}