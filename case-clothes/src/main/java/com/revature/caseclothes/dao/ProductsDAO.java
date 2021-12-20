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
	public Products selectProductById(int id) {
		Products p = em.find(Products.class, id);

		return p;
	}

	@Transactional
	public Carts insertProductToCart(Carts addToCart) {

		em.persist(addToCart);

		return addToCart;
	}

	@Transactional
	public Carts selectACartById(int id) {
		String query = "SELECT c FROM Carts c WHERE c.cartId = :id";
		TypedQuery<Carts> typedQuery = em.createQuery(query, Carts.class);
		Carts cart = typedQuery.setParameter("id", id).getSingleResult();

		return cart;

	}

	@Transactional
	public void deleteProductById(int id) {

		Products productToDelete = em.find(Products.class, id);

		em.remove(productToDelete);

	}

	@Transactional
	public Products updateAProduct(int id, Products productToBeUpdated) {

		Session session = em.unwrap(Session.class);

		String hqlUpdate = "update Products p set p.name = :newName, p.price = :price, p.description = :description, "
				+ "p.categories = :categories, p.imageURL = :imgURL, p.totalQuantity = :totalQuantity "
				+ "where p.id = :id";

		session.createQuery(hqlUpdate).setParameter("newName", productToBeUpdated.getName())
				.setParameter("description", productToBeUpdated.getDescription())
				.setParameter("price", productToBeUpdated.getPrice())
				.setParameter("categories", productToBeUpdated.getCategories())
				.setParameter("imgURL", productToBeUpdated.getImageURL())
				.setParameter("totalQuantity", productToBeUpdated.getTotalQuantity()).setParameter("id", id)
				.executeUpdate();

		Products updatedProducts = this.selectProductById(id);

		return updatedProducts;
	}

	@Transactional
	public Carts insertToCart(Carts c, Quantities q) {

		Session session = em.unwrap(Session.class);

		session.persist(q);

		session.saveOrUpdate(c);

		return c;

	}

}
