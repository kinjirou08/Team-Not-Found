package com.revature.caseclothes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caseclothes.dto.LoginDTO;
import com.revature.caseclothes.exception.InvalidLoginException;
import com.revature.caseclothes.model.User;
import com.revature.caseclothes.service.UserService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthenticationController {
	
	@Autowired
	private UserService us;
	
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private HttpServletResponse res;
	
	private static final String CURRENTUSER = "currentuser";
	
	@PostMapping(path = "/login")
	public ResponseEntity<Object> login(@RequestBody LoginDTO dto) {
		
		try {
			User user = this.us.login(dto.getUsername(),dto.getPassword());
			
			HttpSession session = req.getSession();
			session.setAttribute(CURRENTUSER, user);
			
			
//			String originalSetCookieHeader = res.getHeader("Set-cookie");
//			String newCookieHeader = originalSetCookieHeader + "; SameSite=None; Secure";
//			res.setHeader("Set-Cookie", newCookieHeader);
			
			return ResponseEntity.status(200).body(user);
		}catch (InvalidLoginException e) {
			return ResponseEntity.status(400).body(e.getMessage());
			
		}
	}
	
	@GetMapping(path = "/loginstatus")
	public ResponseEntity<Object> loginStatus(){
		User currentlyLoggedInUser = (User) req.getSession().getAttribute(CURRENTUSER);
		
		if(currentlyLoggedInUser != null) {
			return ResponseEntity.status(200).body(currentlyLoggedInUser);
		}
		return ResponseEntity.status(401).body("Not Logged in");
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<String> logout(){
		req.getSession().invalidate();
		
		return ResponseEntity.status(200).body("Successfully logged out");
	}
	

}
