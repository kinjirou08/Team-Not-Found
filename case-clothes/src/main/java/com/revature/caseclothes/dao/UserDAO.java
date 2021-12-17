package com.revature.caseclothes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	

}
