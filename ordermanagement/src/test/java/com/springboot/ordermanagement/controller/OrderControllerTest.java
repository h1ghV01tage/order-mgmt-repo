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

import com.springboot.ordermanagement.controller.OrderController;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.model.Order;
import com.springboot.ordermanagement.request.OrderRequest;
import com.springboot.ordermanagement.service.OrderService;
import com.springboot.ordermanagement.service.OrderService;

@RunWith(SpringRunner.class)
public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;
	
	@Mock
	private OrderService orderService;
	
	@BeforeEach
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	private OrderRequest getOrderRequest() {
		
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setProductPurchaseQuantity("12");
		orderRequest.setTotalPrice("500");
		orderRequest.setOrderStatus("in-transit");
		orderRequest.setDeliveryAddress("13 third st");
		orderRequest.setPaymentMethod("cash");
		orderRequest.setCustomerId(123L);
		orderRequest.setProductId(123L);
		
		return orderRequest;
	}
	
	private Order getOrder() {
		
		Order order = new Order();
		order.setOrderId(123L);
		order.setProductPurchaseQuantity("12");
		order.setTotalPrice("500");
		order.setOrderStatus("in-transit");
		order.setDeliveryAddress("13 third st");
		order.setPaymentMethod("cash");
		
		return order;
		
	}
	
	
	@Test
	public void createOrderTestPositive() {
		
		Mockito.when(orderService.saveOrder(Mockito.any())).thenReturn(getOrder());
		ResponseEntity<?> actualResponse = orderController.createOrder(getOrderRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void createOrderTestNegative() {
		
		Mockito.when(orderService.saveOrder(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = orderController.createOrder(getOrderRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
	}
	
	@Test
	public void getOrderByIdTestPositive() {
		
		Mockito.when(orderService.getOrderById(Mockito.any())).thenReturn(getOrder());
		ResponseEntity<?> actualResponse = orderController.getOrderById(123L);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
		
	}
	
	@Test
	public void getOrderByIdTestNegative() {
		
		Mockito.when(orderService.getOrderById(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = orderController.getOrderById(123L);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void getAllOrderTestPositive() {
		
		//create a list of orders because we have to return a list
		List<Order> orderList = new ArrayList<>();
		orderList.add(getOrder());
		
		//mock it
		Mockito.when(orderService.getAllOrders()).thenReturn(orderList);
		ResponseEntity<?> actualResponse = orderController.getAllOrder();
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void getAllOrderTestNegative() {
		
		//mock it
		Mockito.when(orderService.getAllOrders()).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = orderController.getAllOrder();
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		
		
	}
	
	@Test
	public void getAllOrderWithPaginationTestPositive() {
		
		//create a list of orders because we have to return a list
		List<Order> orderList = new ArrayList<>();
		orderList.add(getOrder());

		// mock it
		Mockito.when(orderService.getAllOrderWithPagination(Mockito.any(), Mockito.any())).thenReturn(orderList);
		ResponseEntity<?> actualResponse = orderController.getAllOrderWithPagination(1,0);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
	}
	
	@Test
	public void getAllOrderWithPaginationTestNegative() {
	
		// mock it
		Mockito.when(orderService.getAllOrderWithPagination(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = orderController.getAllOrderWithPagination(1,0);
		assertNotNull(actualResponse);
		assertEquals("no order exists", actualResponse.getBody());
		
	}
	
	@Test
	public void deleteOrderByIdTestPositive() {
		
		ResponseEntity<?> actualResponse = orderController.deleteOrderById(123L);
		assertNotNull(actualResponse);
		assertEquals("The order with the order id" + 123L + "is deleted", actualResponse.getBody());
		
	}
	

	@Test
	public void getOrderByEmailTestPositive() {
		
		Mockito.when(orderService.getOrderWithEmail(Mockito.any())).thenReturn(getOrder());
		ResponseEntity<?> actualResponse = orderController.getOrderWithEmail("abc@ayebeecee.com");
		assertNotNull(actualResponse);
		assertEquals(getOrder(), actualResponse.getBody());
		
	}
	
	@Test
	public void getOrderByEmailTestNegative() {
		
		Mockito.when(orderService.getOrderWithEmail(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = orderController.getOrderWithEmail("abc@ayebeecee.com");
		assertNotNull(actualResponse);
		assertEquals("can't find order associated with that email", actualResponse.getBody());
		
	}
	
	@Test
	public void updateOrderTestPositive() {
		
		Mockito.when(orderService.updateOrder(Mockito.any(), Mockito.any())).thenReturn(getOrder());
		ResponseEntity<?> actualResponse =orderController.updateOrder(123L, getOrderRequest());
		assertNotNull(actualResponse);
		assertEquals(getOrder(), actualResponse.getBody());
		
		
	}
	
	@Test
	public void updateOrderTestNegative() {
		
		Mockito.when(orderService.updateOrder(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse =orderController.updateOrder(123L, getOrderRequest());
		assertNotNull(actualResponse);
		assertEquals("can't find an order with that Id", actualResponse.getBody());
		
	}
	
	@Test
	public void countOrdersTestPositive() {
	
		Mockito.when(orderService.countOrders()).thenReturn(1L);
		ResponseEntity<?> actualResponse = orderController.countOrders();
		assertNotNull(actualResponse);
		assertEquals("total number of orders" + 1L, actualResponse.getBody());
		
	}
	
	@Test
	public void countOrdersTestNegative() {
		
		Mockito.when(orderService.countOrders()).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = orderController.countOrders();
		assertNotNull(actualResponse);
		assertEquals("no order exists", actualResponse.getBody());
		
	}
}
