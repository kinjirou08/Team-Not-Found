package com.revature.caseclothes.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.revature.caseclothes.model.User;

@Aspect
public class SecurityAspect {
	
	private String CURRENTUSER = "currentuser";
	
	@Autowired
	private HttpServletRequest req;
	
	@Around("@annotation(com.revature.caseclothes.annotation.Customer)")
	public Object protectEndPointCustomerOnly(ProceedingJoinPoint pjp) throws Throwable {
		
		/*
		 * Before
		 */
		
		HttpSession session = req.getSession();
		
		User currentlyLoggedInUser = (User) session.getAttribute(CURRENTUSER);
		
		if(currentlyLoggedInUser==null) {
			return ResponseEntity.status(401).body("You are not logged in");
		}
		
		if(!currentlyLoggedInUser.getRole().getRole().equals("customer")) {
			return ResponseEntity.status(200).body("Logged in, but only customers can access");
		}
		
		Object returnValue = pjp.proceed();
		
		
		return returnValue;
		
	}
	
	@Around("@annotation(com.revature.caseclothes.annotation.Admin)")
	public Object protectAdminEndpointOnly(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpSession session = req.getSession();
		
		User currentlyLoggedInUser = (User) session.getAttribute(CURRENTUSER);
		
		if(currentlyLoggedInUser==null) {
			return ResponseEntity.status(401).body("You are not logged in");
		}
		
		if(!currentlyLoggedInUser.getRole().getRole().equals("admin")) {
			return ResponseEntity.status(200).body("Logged in, but only admin can access");
		}
		
		Object returnValue = pjp.proceed();
		
		
		return returnValue;
	}
	

}
