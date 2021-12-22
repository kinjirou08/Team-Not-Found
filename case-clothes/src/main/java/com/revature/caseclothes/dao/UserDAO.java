package com.revature.caseclothes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
//import org.springframework.boot.autoconfigure.web.ServerProperties.Reactive.Session;
//import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.model.User;

@Repository
public class UserDAO {
	
	@PersistenceContext
	private EntityManager em;
	@Transactional
	public User getUsernameAndPassword(String username, String password) {
		User user = em.createQuery("FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
				.setParameter("user", username)
				.setParameter("pass", password)
				.getSingleResult();
		
		
		return user;
		
	}
	@Transactional
	public User getUserByUsername(String username) {
		User user = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class).setParameter("user", username)
				.getSingleResult();

		return user;
	}
	@Transactional
	public User getUserById(int id) {

		User user = em.createQuery("SELECT u FROM User u WHERE u.id = :userid", User.class).setParameter("userid", id)
				.getSingleResult();

		return user;
	}
	@Transactional
	public User UpdateUser(int id, User updatedUserInfo) {

		Session session = em.unwrap(Session.class);
		
		User currentlyLoggedIn =   session.find(User.class, id);
		
		currentlyLoggedIn = updatedUserInfo;
		
		  session.merge(currentlyLoggedIn);

		return currentlyLoggedIn;
	}
	@Transactional
	public void deleteUserByID(int id) {

		User u = em.find(User.class, id);
		
		em.remove(u);
		
	}	

}
