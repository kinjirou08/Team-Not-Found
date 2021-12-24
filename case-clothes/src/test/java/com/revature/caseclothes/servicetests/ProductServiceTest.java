package com.revature.caseclothes.servicetests;

import static org.mockito.Mockito.times;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.revature.caseclothes.dao.ProductsDAO;
import com.revature.caseclothes.exception.CartNotFoundException;
import com.revature.caseclothes.exception.ProductNotFoundException;
import com.revature.caseclothes.model.Carts;
import com.revature.caseclothes.model.Category;
import com.revature.caseclothes.model.Products;
import com.revature.caseclothes.model.Quantities;
import com.revature.caseclothes.service.ProductsService;

@ActiveProfiles("ProductService-test")
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTest {

	@Autowired
	ProductsService productsService;

	@Autowired
	ProductsDAO productsDao;

	/*
	 * getProductById() test
	 */

	@Test // Happy Path
	void getProductById_PositiveTest() throws ProductNotFoundException {

		Mockito.when(productsDao.selectProductById(1)).thenReturn(new Products("tshirt",
				"Your perfect pack for everyday", 109.95, new Category("Men's Clothing"), "imageURL", 100));

		Products actual = productsService.getProductById("1");

		Assertions.assertEquals(new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), "imageURL", 100), actual);
	}

	@Test // Sad Path
	void getProductById_Negative() {

		Assertions.assertThrows(ProductNotFoundException.class, () -> {

			productsService.getProductById("1");

		});
	}

	/*
	 * getAllProducts() test
	 */

	@Test // Happy Path
	void getAllProducts_PositiveTest() {

		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 109.95, new Category("Men's Clothing"),
				"imageURL", 100);
		p1.setId(1);
		Products p2 = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		p2.setId(2);

		List<Products> productList = new ArrayList<>();
		productList.add(p1);
		productList.add(p2);

		Mockito.when(productsDao.getAllProducts()).thenReturn(productList);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), "imageURL", 100);
		expectedp1.setId(1);
		Products expectedp2 = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		expectedp2.setId(2);

		List<Products> expectedList = new ArrayList<>();
		expectedList.add(expectedp1);
		expectedList.add(expectedp2);

		List<Products> actual = productsService.getAllProducts();

		Assertions.assertEquals(expectedList, actual);
	}

	/*
	 * getAllProductThatContains() test
	 */

	@Test // Happy Path
	void getAllProductThatContains_PositiveTest() throws ProductNotFoundException {

		Products p1 = new Products("tshirt", "Your perfect pack for everyday", 109.95, new Category("Men's Clothing"),
				"imageURL", 100);
		p1.setId(1);
		Products p2 = new Products("tshirt", "Perfect clothing for warm season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		p2.setId(2);

		List<Products> productList = new ArrayList<>();
		productList.add(p1);
		productList.add(p2);

		Mockito.when(productsDao.getAllProductThatContains("shirt")).thenReturn(productList);

		Products expectedp1 = new Products("tshirt", "Your perfect pack for everyday", 109.95,
				new Category("Men's Clothing"), "imageURL", 100);
		expectedp1.setId(1);
		Products expectedp2 = new Products("tshirt", "Perfect clothing for warm season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		expectedp2.setId(2);

		List<Products> expectedList = new ArrayList<>();
		expectedList.add(expectedp1);
		expectedList.add(expectedp2);

		List<Products> actual = productsService.getAllProductThatContains("shirt");

		Assertions.assertEquals(expectedList, actual);
	}

	@Test // Sad Path
	void getAllProductThatContains_NoValidProduct() {

		Assertions.assertThrows(ProductNotFoundException.class, () -> {

			productsService.getAllProductThatContains("shirt");

		});
	}

	/*
	 * ProductsService's addNewProduct() test
	 */

	@Test // Happy Path
	void addNewProduct_PositiveTest() {

		Category c1 = new Category("Men's clothing");
		c1.setCategoryId(1);

		Products newProduct = new Products("Mens Casual Slim Fit",
				"H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts", 15.99, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		newProduct.setId(1);

		Mockito.when(productsDao.insertNewProduct(newProduct))
				.thenReturn(new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts",
						15.99, c1, "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100));

		Products actual = productsService.addNewProduct(newProduct);
		actual.setId(1);

		Products expected = new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts",
				15.99, c1, "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		expected.setId(1);

		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void addNewProduct_NoNameInputAllFieldsValid_NegativeTest() {

		Category c1 = new Category("Men's clothing");
		c1.setCategoryId(1);

		Products p = new Products("", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts", 15.99, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.addNewProduct(p);
		});
	}

	@Test // Sad Path
	void addNewProduct_NoDescriptionInputOtherFieldsValid_NegativeTest() {

		Category c1 = new Category("Men's clothing");
		c1.setCategoryId(1);

		Products p = new Products("Mens Casual Slim Fit", "", 15.99, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.addNewProduct(p);
		});
	}

	@Test // Sad Path
	void addNewProduct_NoPriceInputOtherFieldsValid_NegativeTest() throws ProductNotFoundException {

		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);

		Products p = new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts", 0, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.addNewProduct(p);
		});
	}

	@Test // Sad Path
	void addNewProduct_PriceIsNotADoubleInputOtherFieldsValid_NegativeTest() {

		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);

		Products p = new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts",
				Double.NaN, c1, "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.addNewProduct(p);
		});
	}

	/*
	 * deleteProductById() test
	 */
	@Test // Happy Path
	void deleteAProduct_PositiveTest() {

		productsService.deleteProductById("1");

		Mockito.verify(productsDao, times(1)).deleteProductById(1);
	}

	/*
	 * updateAProduct() test
	 */

	@Test // Happy Path
	void updateAProduct_PositiveTest() throws ProductNotFoundException {

		int productId = 1;
		Products productToBeUpdated = new Products("Mens Casual Slim Fit",
				"H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts", 29.99, new Category("Men's Clothing"),
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		productToBeUpdated.setId(productId);

		Mockito.when(productsDao.selectProductById(1)).thenReturn(productToBeUpdated);

		Mockito.when(productsDao.updateAProduct(productToBeUpdated))
				.thenReturn(new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts",
						29.99, new Category("Men's Clothing"),
						"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100));

		Products actual = productsService.updateAProduct("1", productToBeUpdated);
		actual.setId(1);

		Products expected = new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts",
				29.99, new Category("Men's Clothing"), "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		expected.setId(1);

		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void updateAProduct_ProductNotFound_NegativeTest() {

		Products p = null;

		Assertions.assertThrows(ProductNotFoundException.class, () -> {
			productsService.updateAProduct("1", p);
		});
	}

	@Test // Sad Path - Very Similar to addNewProduct()'s Input validation
	void updateAProduct_EmptyNameInput_OtherFieldsValid_NegativeTest() throws ProductNotFoundException {

		Category c1 = new Category("Men's clothing");
		c1.setCategoryId(1);

		Products p = new Products("", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts", 15.99, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		Mockito.when(productsDao.selectProductById(1)).thenReturn(p);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.updateAProduct("1", p);
		});

	}

	@Test // Sad Path - Very Similar to addNewProduct()'s Input validation
	void updateAProduct_EmptyDescriptionInput_OtherFieldsValid_NegativeTest() {

		Category c1 = new Category("Men's clothing");
		c1.setCategoryId(1);

		Products p = new Products("Mens Casual Slim Fit", "", 15.99, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		Mockito.when(productsDao.selectProductById(1)).thenReturn(p);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.updateAProduct("1", p);
		});
	}

	@Test // Sad Path - Very Similar to addNewProduct()'s Input validation
	void updateAProduct_EmptyPriceInput_OtherFieldsValid_NegativeTest() {

		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);

		Products p = new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts", 0, c1,
				"https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		Mockito.when(productsDao.selectProductById(1)).thenReturn(p);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.updateAProduct("1", p);
		});
	}

	@Test // Sad Path - Very Similar to addNewProduct()'s Input validation
	void updateAProduct_PriceIsNotADoubleInput_OtherFieldsValid_NegativeTest() {

		Category c1 = new Category("Men's Clothing");
		c1.setCategoryId(1);

		Products p = new Products("Mens Casual Slim Fit", "H2H Mens Casual Slim Fit Long Sleeve V-Neck T-Shirts",
				Double.NaN, c1, "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", 100);
		Mockito.when(productsDao.selectProductById(1)).thenReturn(p);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			productsService.updateAProduct("1", p);
		});
	}

	/*
	 * addMoreProductsToCart() test
	 */

	@Test // Happy Path
	void addMoreProductsToCart_PostiveTest() throws ProductNotFoundException, CartNotFoundException {

		List<Quantities> quantityList = new ArrayList<>();

		Carts currentCart = new Carts(quantityList);
		currentCart.setCartId(1);

		Mockito.when(productsDao.selectACartById(1)).thenReturn(currentCart);

		Products productToAdd = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		productToAdd.setId(2);
		Quantities q2 = new Quantities(productToAdd, 1);
		q2.setQuantityId(2);

		Mockito.when(productsDao.selectProductById(2)).thenReturn(productToAdd);

		List<Quantities> currentQuantitiesInTheCart = currentCart.getQuantities();
		currentQuantitiesInTheCart.add(q2);
		currentCart.setQuantities(currentQuantitiesInTheCart);

		Mockito.when(productsDao.insertToCart(currentCart, q2)).thenReturn(currentCart);

		Carts actual = productsService.addMoreProductsToCart(currentCart, "1", "2", "1");

		Carts expected = currentCart;

		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void addMoreProductsToCart_CartNotFound_NegativeTest() {

		Assertions.assertThrows(CartNotFoundException.class, () -> {
			productsService.addMoreProductsToCart(null, "1", "1", "1");
		});
	}

	@Test // Sad Path
	void addMoreProductsToCart_ProductNotFound_NegativeTest() {

		List<Quantities> quantityList = new ArrayList<>();
		Carts currentCart = new Carts(quantityList);
		currentCart.setCartId(1);
		Mockito.when(productsDao.selectACartById(1)).thenReturn(currentCart);

		Assertions.assertThrows(ProductNotFoundException.class, () -> {
			productsService.addMoreProductsToCart(currentCart, "1", "1", "1");
		});
	}

	/*
	 * updateProductQuantityInCart() test
	 */

	@Test // Happy Path
	void updateProductQuantityInCart_PositiveTest() throws CartNotFoundException, ProductNotFoundException {

		List<Quantities> quantityList = new ArrayList<>();

		Carts currentCart = new Carts(quantityList);
		currentCart.setCartId(1);

		Mockito.when(productsDao.selectACartById(1)).thenReturn(currentCart);

		Products productToUpdate = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		productToUpdate.setId(2);
		Quantities q2 = new Quantities(productToUpdate, 1);
		q2.setQuantityId(2);

		Mockito.when(productsDao.selectProductById(2)).thenReturn(productToUpdate);

		List<Quantities> currentQuantitiesInTheCart = currentCart.getQuantities();
		for (Quantities q : currentQuantitiesInTheCart) {
			q.setQuantity(2);
		}

		currentCart.setQuantities(currentQuantitiesInTheCart);

		Mockito.when(productsDao.updateProductsInTheCart(currentCart)).thenReturn(currentCart);

		Carts actual = productsService.updateProductQuantityInCart(currentCart, "1", "2", "2");

		Carts expected = currentCart;

		Assertions.assertEquals(expected, actual);

	}

	/*
	 * deleteProductInTheCart() test
	 */

	@Test // Happy Path
	void deleteProductInTheCart() throws CartNotFoundException, ProductNotFoundException {

		List<Quantities> quantityList = new ArrayList<>();
		Carts currentCart = new Carts(quantityList);
		currentCart.setCartId(1);

		Mockito.when(productsDao.selectACartById(1)).thenReturn(currentCart);

		Products productToDelete = new Products("Jacket", "Perfect clothing for cold season", 109.95,
				new Category("Women's Clothing"), "imageURL", 100);
		productToDelete.setId(2);

		Mockito.when(productsDao.selectProductById(2)).thenReturn(productToDelete);

		Quantities q2 = new Quantities(new Products(), 1);
		q2.setQuantityId(1);

		List<Quantities> currentProductInTheCart = currentCart.getQuantities();
		int quantityToDelete = 0;

		Iterator<Quantities> iter = currentProductInTheCart.iterator();
		Quantities q1 = null;
		while (iter.hasNext()) {
			q1 = iter.next();
			iter.remove();
			quantityToDelete = q1.getQuantityId();
		}

		currentCart.setQuantities(currentProductInTheCart);

		Mockito.when(productsDao.deleteAProductInTheCart(currentCart, quantityToDelete)).thenReturn(currentCart);

		Carts actual = productsService.delteteProductInCart(currentCart, "1", "2");

		Carts expected = currentCart;

		Assertions.assertEquals(expected, actual);
	}
}
