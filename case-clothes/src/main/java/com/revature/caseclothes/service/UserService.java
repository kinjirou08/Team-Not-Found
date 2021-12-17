package com.revature.caseclothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.UserDAO;
import com.revature.caseclothes.exception.InvalidLoginException;
import com.revature.caseclothes.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO ud;

	public User login(String username, String password) throws InvalidLoginException {
		try {
			User user = this.ud.getUsernameAndPassword(username, password);
			
			return user;
		}catch(DataAccessException e) {
			throw new InvalidLoginException("Username and/or password is incorrect");
		}
	}

}
