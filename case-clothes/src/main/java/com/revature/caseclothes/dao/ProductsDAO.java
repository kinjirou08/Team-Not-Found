package com.revature.caseclothes.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.model.Quantities;

@Repository
public class ProductsDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Products selectProductById(int id) {

		Products p = em.find(Products.class, id);

		return p;
	}

	@Transactional
	public List<Products> getAllProducts() {

		String query = "SELECT p FROM Products p";
		TypedQuery<Products> typedQuery = em.createQuery(query, Products.class);
		List<Products> productsList = typedQuery.getResultList();

		return productsList;
	}

	@Transactional
	public List<Products> getAllProductThatContains(String name) {

		String sql = "Select p FROM Products p WHERE lower(p.name) LIKE lower(:name)";
		TypedQuery<Products> typedQuery = em.createQuery(sql, Products.class).setParameter("name", "%" + name + "%");
		List<Products> productsList = typedQuery.getResultList();
		return productsList;
	}

	@Transactional
	public Products insertNewProduct(Products productToAdd) {

		em.persist(productToAdd);

		return productToAdd;
	}

	@Transactional
	public Products updateAProduct(Products productToBeUpdated) {

		Session session = em.unwrap(Session.class);

		session.merge(productToBeUpdated);

		return productToBeUpdated;
	}

	@Transactional
	public void deleteProductById(int id) {

		Products productToDelete = em.find(Products.class, id);

		em.remove(productToDelete);

	}

	@Transactional
	public Carts selectACartById(int id) {

		String query = "SELECT c FROM Carts c WHERE c.cartId = :id";
		TypedQuery<Carts> typedQuery = em.createQuery(query, Carts.class);
		Carts cart = typedQuery.setParameter("id", id).getSingleResult();

		return cart;
	}

	@Transactional
	public Carts insertToCart(Carts c, Quantities q) {

		Session session = em.unwrap(Session.class);

		session.saveOrUpdate(q);

		session.saveOrUpdate(c);

		return c;

	}

	@Transactional
	public Carts updateProductsInTheCart(Carts currentCart) {

		Session session = em.unwrap(Session.class);

		session.saveOrUpdate(currentCart);

		return currentCart;
	}

	@Transactional
	public Carts deleteAProductInTheCart(Carts currentCart, int quantityToDelete) {

		Session session = em.unwrap(Session.class);

		Quantities toBeDeleted = session.find(Quantities.class, quantityToDelete);

		session.remove(toBeDeleted);

		session.merge(currentCart);

		return currentCart;
	}

}
