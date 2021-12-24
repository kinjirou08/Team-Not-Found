package com.revature.caseclothes.servicetests;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.revature.caseclothes.dao.ProductsDAO;

@Profile("ProductService-test")
@Configuration
public class ProductsDaoTestConfiguration {

	@Bean
	@Primary
	public ProductsDAO productsDao() {
		return Mockito.mock(ProductsDAO.class);
	}
}
