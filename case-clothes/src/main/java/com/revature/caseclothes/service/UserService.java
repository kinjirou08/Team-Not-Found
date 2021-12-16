package com.revature.caseclothes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caseclothes.dao.UserDao;
import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao ud;
	
	//Add new Customer
	public User addCustomer(AddUserDTO dto) {
		
		return ud.addCustomer(dto);
	}
	
	//Add new Admin
	public User addAdmin(AddUserDTO dto) {
		
		return ud.addAdmin(dto);
	}
	
	//Attempt to put addAdmin and addCustomer in the same function
	public User addUser(User currentUser, AddUserDTO dto) {
		if(currentUser.getRole().getRole().equals("admin")) {
			return ud.addAdmin(dto);
		}
		
		return ud.addCustomer(dto);
	}
	
	//Get all users if Admin
	public List<User> getAllUsers(User currentUser) {
		if(currentUser.getRole().getRole().equals("admin")) {
			return ud.getAllUsers();
		}
		
		//if user is customer
		//throw authentication exception
		if(currentUser.getRole().getRole().equals("customer")) {
			//throw new UnAuthorizedException("Must be an Admin to use this feature.");
		}
		
		return new ArrayList<>();
	}
	
	//Get all users if Admin
	public User getUserByID(User currentUser, String userID) {
		try {
			int id = Integer.parseInt(userID);
			
			if(currentUser.getRole().getRole().equals("admin")) {
				User user = ud.getUserByID(id);
				
				if(user == null) {
					//throw new UserNotFoundException("User with an id of " + userID + " was not found.");
				}
				
				return user;
			} else {
				//throw new UnAuthorizedException("Must be an Admin to use this feature.");
			}
			
		} catch(NumberFormatException e) {
			//throw new InvalidParametersException("ID provided is not an int compatible value.");
		}

		//delete this later
		return currentUser;
	}
	
	
}
