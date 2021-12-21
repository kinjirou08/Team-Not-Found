package com.revature.caseclothes;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.service.UserService;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CaseClothesServiceTests {
	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserService us;
	
	@Test
	@Transactional
	public void testGetAllUsers() {
		
	}
}
