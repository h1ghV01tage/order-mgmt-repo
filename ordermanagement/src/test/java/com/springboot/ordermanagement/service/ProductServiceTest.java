package com.springboot.ordermanagement.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.ordermanagement.controller.ProductController;
import com.springboot.ordermanagement.dao.ProductDao;
import com.springboot.ordermanagement.model.Product;
import com.springboot.ordermanagement.request.ProductRequest;

@RunWith(SpringRunner.class)

public class ProductServiceTest {

	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductDao productDao;
	
	//before calling each test case, we need to use this annotation
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
	public void saveProductTestPositive() {
		
		Mockito.when(productDao.save(Mockito.any())).thenReturn(getProduct());
		Product actualResponse = productService.saveProduct(getProductRequest());
		assertNotNull(actualResponse);
		assertEquals(getProduct(), actualResponse);
	
	}
	
	@Test
	public void saveProductTestNegative() {
		
		
		Mockito.when(productDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> productService.saveProduct(getProductRequest()));
		
		
	}
	
	@Test
	public void getProductByIdTestPositive() {
		
		Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.of(getProduct()));
		Product actualResponse = productService.getProductById(123L);
		assertNotNull(actualResponse);
		assertEquals(getProduct(), actualResponse);
		
		
	}
	
	@Test
	public void getProductByIdTestNegative() {
		
		
		Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, ()-> productService.getProductById(123L));
		
		
	}
	
	@Test
	public void getAllProductsTestPositive() {
		
		List<Product> productsList = new ArrayList<>();
		productsList.add(getProduct());
		Mockito.when(productDao.findAll()).thenReturn(productsList);
		List<Product> actualProductList = productService.getAllProducts();
		assertNotNull(actualProductList);
		assertEquals(productsList, actualProductList);
		
	}
	
	@Test
	public void getAllProductsTestNegative() {
		
		List <Product> productList = new ArrayList<>();
		Mockito.when(productDao.findAll()).thenReturn(productList);
		assertThrows(RuntimeException.class, ()-> productService.getAllProducts());
		
		
	}
	
	@Test
	public void getAllProductsWithPaginationTestPositive() {
		
		
		List<Product> productsList = new ArrayList<>();
		productsList.add(getProduct());
		Page<Product> pageProduct = new PageImpl<Product>(productsList);
		Mockito.when(productDao.findAll(PageRequest.of(0, 1, Sort.by("productName").descending()))).thenReturn(pageProduct);
		List<Product> actualProductList = productService.getAllProductWithPagination(0, 1);
		assertNotNull(actualProductList);
		assertIterableEquals(productsList, actualProductList);
		
	}
	
	@Test
	public void getAllProductsWithPaginationTestNegative() {
		
		
		List<Product> productsList = new ArrayList<>();
		Page<Product> pageProduct = new PageImpl<Product>(productsList);
		Mockito.when(productDao.findAll(PageRequest.of(0, 1, Sort.by("productName").descending()))).thenReturn(pageProduct);
		assertThrows(RuntimeException.class, ()-> productService.getAllProductWithPagination(0,1));
		
	}
	
	@Test
	public void deleteProductByIdTestPositive() {
		
		productService.deleteProductById(123L);
		
	}
	
	@Test
	public void getProductWithEmailTestPositive() {
		
		Mockito.when(productDao.findProductByEmail(Mockito.any())).thenReturn(getProduct());
		Product actualResponse = productService.getProductWithEmail("admin@tester.com");
		assertNotNull(actualResponse);
		assertEquals(getProduct(), actualResponse);
		
	}
	
	@Test
	public void getProductWithEmailTestNegative() {
		
		Mockito.when(productDao.findProductByEmail(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, () -> productService.getProductWithEmail(Mockito.any()));
		
	}
	
	@Test
	public void countProductsTestPositive() {
		
		Mockito.when(productDao.count()).thenReturn(3L);
		long actualCount = productService.countProducts();
		assertNotNull(actualCount);
		assertEquals(3L, actualCount);
		
	}
	
	@Test
	public void countProductsTestNegative() {
		
		Mockito.when(productDao.count()).thenReturn(0L);
		assertThrows(RuntimeException.class, () -> productService.countProducts());
		
	}
	
	@Test
	public void updateProductTestPostive() {
		Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.of(getProduct()));
		Mockito.when(productDao.save(Mockito.any())).thenReturn(getProduct());
		Product actualResponse = productService.updateProduct(123L, getProductRequest());
		assertNotNull(actualResponse);
		assertEquals(getProduct(), actualResponse);
		
	}
	
	@Test
	public void updateProductTestNegativeA() {
		
		Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.of(getProduct()));
		Mockito.when(productDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> productService.updateProduct(123L, getProductRequest()));
		
	}
	
	@Test
	public void updateProductTestNegativeB() {
		
		Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(productDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> productService.updateProduct(123L, getProductRequest()));
		
	}
	
}
