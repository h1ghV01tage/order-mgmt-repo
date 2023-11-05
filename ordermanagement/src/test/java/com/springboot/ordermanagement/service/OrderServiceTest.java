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

import com.springboot.ordermanagement.controller.OrderController;
import com.springboot.ordermanagement.dao.OrderDao;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.model.Order;
import com.springboot.ordermanagement.model.Product;
import com.springboot.ordermanagement.request.OrderRequest;

@RunWith(SpringRunner.class)

public class OrderServiceTest {

	
	@InjectMocks
	private OrderService orderService;
	
	@Mock
	private OrderDao orderDao;
	
	@Mock
	private CustomerService customerService;
	
	@Mock
	private ProductService productService;
	

	
	//before calling each test case, we need to use this annotation
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private OrderRequest getOrderRequest() {

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setProductPurchaseQuantity("12");
		orderRequest.setPaymentMethod("CASH_ON_DELIVERY");
		// Add other necessary fields from OrderRequest
		orderRequest.setCustomerId(456L); // Example customer ID
		orderRequest.setProductId(789L); // Example product ID
		orderRequest.setDeliveryAddress("123 Main St");
		// Add any other required fields from OrderRequest
		return orderRequest;
	}

	private Order getOrder() {

		Order order = new Order();
		order.setProductPurchaseQuantity("12");
		order.setPaymentMethod("CASH_ON_DELIVERY");

		// Set the customer and product references (foreign keys)
		Customer customer = new Customer();
		customer.setCustomerId(456L); // Example customer ID
		order.setCustomer(customer);

		Product product = new Product();
		product.setProductId(789L); // Example product ID
		order.setProduct(product);
		
		order.setDeliveryAddress("123 Main St"); // Example delivery address
		// Include other required fields from Order here
		return order;

	}
	
	
	@Test
	public void saveOrderTestPositive() {
		
		Product mockProduct = new Product();
		mockProduct.setPrice("100");
		// set the total price
		int productPrice = Integer.parseInt(mockProduct.getPrice()); // conversion data type into integer
		// convert the product purchase quantity into integer
		int prodPurchaseQty = Integer.parseInt("12");
		// perform the calculation of total price
		int totalPrice = productPrice * prodPurchaseQty;
		// now convert back to string and send it out
		//Mock the behavior of customerService.getCustomerById
		Mockito.when(customerService.getCustomerById(Mockito.any())).thenReturn(new Customer());
		Mockito.when(productService.getProductById(Mockito.any())).thenReturn(mockProduct);
		
		Mockito.when(orderDao.save(Mockito.any())).thenReturn(getOrder());
		Order actualResponse = orderService.saveOrder(getOrderRequest());
		assertNotNull(actualResponse);
		assertEquals(getOrder(), actualResponse);
	
	}
	
	@Test
	public void saveOrderTestNegative() {
		
		
		Mockito.when(orderDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> orderService.saveOrder(getOrderRequest()));
		
		
	}
	
	@Test
	public void getOrderByIdTestPositive() {
		
		Mockito.when(orderDao.findById(Mockito.any())).thenReturn(Optional.of(getOrder()));
		Order actualResponse = orderService.getOrderById(123L);
		assertNotNull(actualResponse);
		assertEquals(getOrder(), actualResponse);
		
		
	}
	
	@Test
	public void getOrderByIdTestNegative() {
		
		
		Mockito.when(orderDao.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, ()-> orderService.getOrderById(123L));
		
		
	}
	
	@Test
	public void getAllOrdersTestPositive() {
		
		List<Order> ordersList = new ArrayList<>();
		ordersList.add(getOrder());
		Mockito.when(orderDao.findAll()).thenReturn(ordersList);
		List<Order> actualOrderList = orderService.getAllOrders();
		assertNotNull(actualOrderList);
		assertEquals(ordersList, actualOrderList);
		
	}
	
	@Test
	public void getAllOrdersTestNegative() {
		
		List <Order> orderList = new ArrayList<>();
		Mockito.when(orderDao.findAll()).thenReturn(orderList);
		assertThrows(RuntimeException.class, ()-> orderService.getAllOrders());
		
		
	}
	
	@Test
	public void getAllOrdersWithPaginationTestPositive() {
		
		
		List<Order> ordersList = new ArrayList<>();
		ordersList.add(getOrder());
		Page<Order> pageOrder = new PageImpl<Order>(ordersList);
		Mockito.when(orderDao.findAll(PageRequest.of(0, 1, Sort.by("orderName").descending()))).thenReturn(pageOrder);
		List<Order> actualOrderList = orderService.getAllOrderWithPagination(0, 1);
		assertNotNull(actualOrderList);
		assertIterableEquals(ordersList, actualOrderList);
		
	}
	
	@Test
	public void getAllOrdersWithPaginationTestNegative() {
		
		
		List<Order> ordersList = new ArrayList<>();
		Page<Order> pageOrder = new PageImpl<Order>(ordersList);
		Mockito.when(orderDao.findAll(PageRequest.of(0, 1, Sort.by("orderName").descending()))).thenReturn(pageOrder);
		assertThrows(RuntimeException.class, ()-> orderService.getAllOrderWithPagination(0,1));
		
	}
	
	@Test
	public void deleteOrderByIdTestPositive() {
		
		orderService.deleteOrderById(123L);
		
	}
	
	@Test
	public void getOrderWithEmailTestPositive() {
		
		Mockito.when(orderDao.findOrderByEmail(Mockito.any())).thenReturn(getOrder());
		Order actualResponse = orderService.getOrderWithEmail("admin@tester.com");
		assertNotNull(actualResponse);
		assertEquals(getOrder(), actualResponse);
		
	}
	
	@Test
	public void getOrderWithEmailTestNegative() {
		
		Mockito.when(orderDao.findOrderByEmail(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, () -> orderService.getOrderWithEmail(Mockito.any()));
		
	}
	
	@Test
	public void countOrdersTestPositive() {
		
		Mockito.when(orderDao.count()).thenReturn(3L);
		long actualCount = orderService.countOrders();
		assertNotNull(actualCount);
		assertEquals(3L, actualCount);
		
	}
	
	@Test
	public void countOrdersTestNegative() {
		
		Mockito.when(orderDao.count()).thenReturn(0L);
		assertThrows(RuntimeException.class, () -> orderService.countOrders());
		
	}
	
	@Test
	public void updateOrderTestPostive() {
		Mockito.when(orderDao.findById(Mockito.any())).thenReturn(Optional.of(getOrder()));
		Mockito.when(orderDao.save(Mockito.any())).thenReturn(getOrder());
		Order actualResponse = orderService.updateOrder(123L, getOrderRequest());
		assertNotNull(actualResponse);
		assertEquals(getOrder(), actualResponse);
		
	}
	
	@Test
	public void updateOrderTestNegativeA() {
		
		Mockito.when(orderDao.findById(Mockito.any())).thenReturn(Optional.of(getOrder()));
		Mockito.when(orderDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> orderService.updateOrder(123L, getOrderRequest()));
		
	}
	
	@Test
	public void updateOrderTestNegativeB() {
		
		Mockito.when(orderDao.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(orderDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> orderService.updateOrder(123L, getOrderRequest()));
		
	}
	
}
