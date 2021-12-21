package com.revature.caseclothes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caseclothes.dto.AddUserDTO;
import com.revature.caseclothes.exception.InvalidParametersException;
import com.revature.caseclothes.exception.UnAuthorizedException;
import com.revature.caseclothes.exception.UserNotFoundException;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.service.UserService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private UserService us;

	// Add User
	@PostMapping(path = "/users") // needs fixing
	public ResponseEntity<Object> addUser(@RequestBody AddUserDTO dto) throws UnAuthorizedException {
		HttpSession session = req.getSession();

		User currentlyLoggedInUser = (User) session.getAttribute("currentuser");

		if (currentlyLoggedInUser != null) {
			User addedUser = us.addAdmin(currentlyLoggedInUser, dto);
			return ResponseEntity.status(201).body(addedUser);

		} else {

			User addedUser = us.addCustomer(dto);

			return ResponseEntity.status(201).body(addedUser);
		}

	}

	// Get All Users if Admin
	@GetMapping(path = "/users")
	public ResponseEntity<Object> getAllUsers() throws UnAuthorizedException {
		HttpSession session = req.getSession();

		User currentlyLoggedInUser = (User) session.getAttribute("currentuser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not logged in");
		}

		List<User> usersToList = us.getAllUsers(currentlyLoggedInUser);

		return ResponseEntity.status(200).body(usersToList);
	}

	// Get User by ID if Admin
	@GetMapping(path = "/users/{id}")
	public ResponseEntity<Object> getUserByID(@PathVariable("id") int id)
			throws UserNotFoundException, UnAuthorizedException, InvalidParametersException {
		HttpSession session = req.getSession();

		User currentlyLoggedInUser = (User) session.getAttribute("currentuser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not logged in");
		}

		User userFound = us.getUserByID(currentlyLoggedInUser, id);

		return ResponseEntity.status(200).body(userFound);
	}

	// Get User by Username if Admin
	@GetMapping(path = "/users/{username}")
	public ResponseEntity<Object> getUserByUsername(@PathVariable("username") String username)
			throws UserNotFoundException, UnAuthorizedException, InvalidParametersException {
		HttpSession session = req.getSession();

		User currentlyLoggedInUser = (User) session.getAttribute("currentuser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not logged in");
		}

		User userFound = us.getUserByUsername(currentlyLoggedInUser, username);

		return ResponseEntity.status(200).body(userFound);
	}

	// Delete User if you are the User
	@DeleteMapping(value = "/users")
	public ResponseEntity<Object> deleteUserByID() throws UserNotFoundException {
		HttpSession session = req.getSession();

		User currentlyLoggedInUser = (User) session.getAttribute("currentuser");

		if (currentlyLoggedInUser == null) {
			throw new UserNotFoundException("User was not found or is not logged in");
		}

		int id = currentlyLoggedInUser.getId();
		us.deleteUserByID(currentlyLoggedInUser);
		return ResponseEntity.status(200).body("Successfully Deleted User of id: " + id);
	}

	// Update User if you are the user
	@PutMapping(path = "/users") //needs fixing
	public ResponseEntity<Object> updateUser(@RequestBody User user) throws InvalidParametersException {
		
		try {
			HttpSession session = req.getSession();
			User currentlyLoggedInUser = (User) session.getAttribute("currentuser");
			User userToBeUpdated = us.UpdateUser(currentlyLoggedInUser, user);
			return ResponseEntity.status(200).body(userToBeUpdated);
		} catch (InvalidParametersException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
		
	}
}