package com.revature.caseclothes.daounittests;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.caseclothes.dao.ProductsDAO;

@SpringBootTest
public class ProductsDaoTest {
	@Autowired

	private EntityManager em;
	@Autowired
	private ProductsDAO sut;
	@Test
	@Transactional
	public void  testGetAllProducts_positive(){
		
		
		
}

}
 