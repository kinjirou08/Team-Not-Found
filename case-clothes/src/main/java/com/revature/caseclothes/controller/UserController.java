package com.revature.caseclothes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caseclothes.service.UserService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private UserService us;
}
