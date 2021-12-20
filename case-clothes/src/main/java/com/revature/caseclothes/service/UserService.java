package com.revature.caseclothes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.UserDao;
import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.exception.InvalidParametersException;
import com.revature.caseclothes.exception.UnAuthorizedException;
import com.revature.caseclothes.exception.UserNotFoundException;

import com.revature.caseclothes.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao ud;
	
	//Attempt to put addAdmin and addCustomer in the same function
	public User addUser(User currentUser, AddUserDTO dto) {
		if(currentUser.getRole().getRole().equals("admin")) {
			return ud.addAdmin(dto);
		}
		
		return ud.addCustomer(dto);
	}
	
	//Get all users if Admin
	public List<User> getAllUsers(User currentUser) throws UnAuthorizedException {
		if(currentUser.getRole().getRole().equals("admin")) {
			return ud.getAllUsers();
		}
		
		//if user is customer
		//throw authentication exception
		if(currentUser.getRole().getRole().equals("customer")) {
			throw new UnAuthorizedException("Must be an Admin to use this feature.");
		}
		
		return new ArrayList<>();
	}
	
	//Get user by id if Admin
	public User getUserByID(User currentUser, int id) throws UserNotFoundException, UnAuthorizedException, InvalidParametersException {
		try {
			if(currentUser.getRole().getRole().equals("admin")) {
				User user = ud.getUserByID(id);
				
				if(user == null) {
					throw new UserNotFoundException("User with an id of " + id + " was not found.");
				}
				
				return user;
			} else {
				throw new UnAuthorizedException("Must be an Admin to use this feature.");
			}
			
		} catch(NumberFormatException e) {
			throw new InvalidParametersException("ID provided is not an int compatible value.");
		}
	}
	
	//Get user by id if Admin
	public User getUserByUsername(User currentUser, String username) throws UserNotFoundException, UnAuthorizedException, InvalidParametersException {
		try {
			if(currentUser.getRole().getRole().equals("admin")) {
				User user = ud.getUserByUsername(username);
					
				if(user == null) {
					throw new UserNotFoundException("User with an id of " + username + " was not found.");
				}
					
				return user;
			} else {
				throw new UnAuthorizedException("Must be an Admin to use this feature.");
			}
		} catch(NumberFormatException e) {
			throw new InvalidParametersException("ID provided is not an int compatible value.");
		}
	}
	
	//Delete user if currentUserId = userToBeDeleted
	public void deleteUserByID(User currentUser) throws UserNotFoundException {
		if(currentUser != null) {
			int currentUserID = currentUser.getId();
			ud.deleteUserByID(currentUserID);
		} else {
			throw new UserNotFoundException("Current User is NULL");
		}
	}
	
	//Update Information of currentUser
	public void UpdateUserByID(User currentUser) throws UserNotFoundException {
		if(currentUser != null) {
			int currentUserID = currentUser.getId();
			ud.UpdateUserByID(currentUserID, currentUser);
		} else {
			throw new UserNotFoundException("Current User is NULL");
		}
	}

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
