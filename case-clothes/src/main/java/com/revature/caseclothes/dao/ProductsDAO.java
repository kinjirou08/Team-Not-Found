package com.revature.caseclothes.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.model.Products;

@Repository
public class ProductsDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<Products> getAllProducts() {
		
		//String query = "SELECT p.id, p.name, p.price, p.description, c.categories, p.imageURL, p.totalQuantity "
		//	+ "FROM Products p JOIN p.categories c";
		//TypedQuery<Products[]> typedQuery = em.createQuery(query, Products[].class);
		String query = "SELECT p FROM Products p";
		TypedQuery<Products> typedQuery = em.createQuery(query, Products.class);
		List<Products> productsList = typedQuery.getResultList();
		
		return productsList;
	}
	
	@Transactional
	public List<Products> getAllProductThatContains(String name) {
		
		String sql = "Select p FROM Products p WHERE lower(p.name) LIKE lower(:name)";
		TypedQuery<Products> typedQuery = em.createQuery(sql, Products.class)
				.setParameter("name", "%"+name+"%");
		List<Products> productsList = typedQuery.getResultList();
		return productsList;
	}

}
