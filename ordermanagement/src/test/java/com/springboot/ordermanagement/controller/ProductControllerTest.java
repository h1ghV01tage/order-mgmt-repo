package com.springboot.ordermanagement.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.ordermanagement.controller.ProductController;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.model.Product;
import com.springboot.ordermanagement.request.ProductRequest;
import com.springboot.ordermanagement.service.ProductService;

@RunWith(SpringRunner.class)
public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;
	
	@Mock
	private ProductService productService;
	
	@BeforeEach
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	private ProductRequest getProductRequest() {
		
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("apple");
		productRequest.setDescription("fresh apples");
		productRequest.setProductQuantity("12");
		productRequest.setPrice("130");
		
		return productRequest;
	}
	
	private Product getProduct() {
		
		Product product = new Product();
		product.setProductId(123L);
		product.setProductName("apple");
		product.setDescription("fresh apples");
		product.setProductQuantity("12");
		product.setPrice("130");
		
		return product;
		
	}
	
	
	
	@Test
	public void createProductTestPositive() {
		
		Mockito.when(productService.saveProduct(Mockito.any())).thenReturn(getProduct());
		ResponseEntity<?> actualResponse = productController.createProduct(getProductRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void createProductTestNegative() {
		
		Mockito.when(productService.saveProduct(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = productController.createProduct(getProductRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
	}
	
	@Test
	public void getProductByIdTestPositive() {
		
		Mockito.when(productService.getProductById(Mockito.any())).thenReturn(getProduct());
		ResponseEntity<?> actualResponse = productController.getProductById(123L);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
		
	}
	
	@Test
	public void getProductByIdTestNegative() {
		
		Mockito.when(productService.getProductById(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = productController.getProductById(123L);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void getAllProductTestPositive() {
		
		//create a list of products because we have to return a list
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct());
		
		//mock it
		Mockito.when(productService.getAllProducts()).thenReturn(productList);
		ResponseEntity<?> actualResponse = productController.getAllProduct();
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void getAllProductTestNegative() {
		
		//mock it
		Mockito.when(productService.getAllProducts()).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = productController.getAllProduct();
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		
		
	}
	
	@Test
	public void getAllProductWithPaginationTestPositive() {
		
		//create a list of products because we have to return a list
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct());

		// mock it
		Mockito.when(productService.getAllProductWithPagination(Mockito.any(), Mockito.any())).thenReturn(productList);
		ResponseEntity<?> actualResponse = productController.getAllProductWithPagination(1,0);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void getAllProductWithPaginationTestNegative() {
	
		// mock it
		Mockito.when(productService.getAllProductWithPagination(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = productController.getAllProductWithPagination(1,0);
		assertNotNull(actualResponse);
		assertEquals("no product exists", actualResponse.getBody());
		
	}
	
	@Test
	public void deleteProductByIdTestPositive() {
		
		ResponseEntity<?> actualResponse = productController.deleteProductById(123L);
		assertNotNull(actualResponse);
		assertEquals("The product with the product id" + 123L + "is deleted", actualResponse.getBody());
		
	}
	

	@Test
	public void getProductByEmailTestPositive() {
		
		Mockito.when(productService.getProductWithEmail(Mockito.any())).thenReturn(getProduct());
		ResponseEntity<?> actualResponse = productController.getProductWithEmail("abc@ayebeecee.com");
		assertNotNull(actualResponse);
		assertEquals(getProduct(), actualResponse.getBody());
		
	}
	
	@Test
	public void getProductByEmailTestNegative() {
		
		Mockito.when(productService.getProductWithEmail(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = productController.getProductWithEmail("abc@ayebeecee.com");
		assertNotNull(actualResponse);
		assertEquals("can't find product associated with the email", actualResponse.getBody());
		
	}
	
	@Test
	public void updateProductTestPositive() {
		
		Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(getProduct());
		ResponseEntity<?> actualResponse =productController.updateProduct(123L, getProductRequest());
		assertNotNull(actualResponse);
		assertEquals(getProduct(), actualResponse.getBody());
		
		
	}
	
	@Test
	public void updateProductTestNegative() {
		
		Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse =productController.updateProduct(123L, getProductRequest());
		assertNotNull(actualResponse);
		assertEquals("can't find a product with that Id", actualResponse.getBody());
		
	}
	
	@Test
	public void countProductsTestPositive() {
	
		Mockito.when(productService.countProducts()).thenReturn(1L);
		ResponseEntity<?> actualResponse = productController.countProducts();
		assertNotNull(actualResponse);
		assertEquals("total number of products" + 1L, actualResponse.getBody());
		
	}
	
	@Test
	public void countProductsTestNegative() {
		
		Mockito.when(productService.countProducts()).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = productController.countProducts();
		assertNotNull(actualResponse);
		assertEquals("no products exist", actualResponse.getBody());
		
	}
}
