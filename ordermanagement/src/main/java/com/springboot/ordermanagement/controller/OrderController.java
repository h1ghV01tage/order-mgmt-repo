package com.springboot.ordermanagement.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ordermanagement.model.Order;
import com.springboot.ordermanagement.request.OrderRequest;
import com.springboot.ordermanagement.response.OrderResponse;
import com.springboot.ordermanagement.service.OrderService;

@RestController
@RequestMapping(value="/om/v1/order")
public class OrderController {
	/*
	 *API is bridge responsible for communication between response and request
	REST API representational state transfer API, used to handle HTTP requests
	CRUD - > 
	Create: post
	Read / Retrieve :get
	Update: put / patch
	Delete: delete 
	 */
	
	//@RequestBody takes input from front end in json format and passes it to 
	
	Logger logger = LoggerFactory.getLogger((OrderController.class));
	
	
	
	@Autowired
	private OrderService orderService;

	@PostMapping(value = "/save")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
		logger.info("started");
		OrderResponse orderResponse = new OrderResponse();
		
		try {
			Order savedOrder = orderService.saveOrder(orderRequest);
			logger.info("order successfuly saved");
			orderResponse.setData(savedOrder);
			orderResponse.setMessage("Order successfully saved");
			logger.info("order saved into database");
			orderResponse.setCode(HttpStatus.OK.toString());
			logger.info("create Order API ended");
			return ResponseEntity.ok().body(orderResponse);
		}
		
		catch(Exception e) {
			logger.error("order can't be saved, an exception occured" + e.getMessage());
			orderResponse.setData(null);
			orderResponse.setMessage("cust omer could not be saved");
			orderResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return ResponseEntity.internalServerError().body(orderResponse);
			
		}
		
		
			
	}
	
	//@PathVariable - value comes within url
	@GetMapping(value="find/{orderid}")
	public ResponseEntity<?> getOrderById(@PathVariable ("orderid") Long orderId) {
		
		try {
			Order order = orderService.getOrderById(orderId);
			logger.info("order fetched successfully");
			return ResponseEntity.ok().body(order);
		}
		
		catch(Exception e) {
			logger.error("order not found");
			return ResponseEntity.internalServerError().body("Order not found");
		}
		
	}
	
	@GetMapping(value="/findall")
	public ResponseEntity<?> getAllOrder () {
		
		try {
			
			List<Order> custList = orderService.getAllOrders();
			
			return ResponseEntity.ok().body(custList);
			
		}
		
		catch(Exception e){
			
			return ResponseEntity.internalServerError().body("no order exists");
			
		}
		
	}
	
	/*
	 * pagination: fetching records based on pages
	 * two params: page number and page size
	 * page no: number of page requested
	 * pagesize: how many records in a page
	 * pagenumber 10, no of records 36
	 * page index starts from 0
	 * 
	 * 
	 * Loggers: used to log messages for application
	 * Maintains the hierarchical history of the application
	 * 4 level of loggers
	 * info
	 * error
	 * warn
	 * debug
	 */
	
	@GetMapping("/findallwithpage")
	public ResponseEntity<?> getAllOrderWithPagination(@RequestParam("pagesize") Integer pageNumber, @RequestParam("pageno") Integer pageSize) {
		
		try {
			
			List<Order> orderList = orderService.getAllOrderWithPagination(pageNumber, pageSize);
			
			return ResponseEntity.ok().body(orderList);
		}
		
		catch (Exception e){
			
			return ResponseEntity.internalServerError().body("no order exists");
			
		}
		
		
		
	}
	
	@DeleteMapping(value="/delete/{orderid}")
	public ResponseEntity<?> deleteOrderById(@PathVariable("orderid") Long orderId) {
		
		orderService.deleteOrderById(orderId);
		return ResponseEntity.ok().body("The order with the order id" + orderId + "is deleted");

		
	}
	
	@GetMapping(value="/findbyemail")
	public ResponseEntity<?> getOrderWithEmail(@RequestParam("email") String email) {
		
		try {
			
			Order order = orderService.getOrderWithEmail(email);
			return ResponseEntity.ok().body(order);
			
		}
		catch(Exception e) {
			
			return ResponseEntity.internalServerError().body("can't find order associated with that email");
			
		}
		
	}
	
	@PutMapping(value="/update/{orderid}")
	public ResponseEntity<?> updateOrder(@PathVariable("orderid") Long orderId, @RequestBody OrderRequest newOrderRequest) {
		
		try {
			
			Order updatedOrder =  orderService.updateOrder(orderId, newOrderRequest);
			
			return ResponseEntity.ok().body(updatedOrder);
		}
		
		catch(Exception e) {
			
			return ResponseEntity.internalServerError().body("can't find an order with that Id");
		}
			
	}
	
	@GetMapping(value="count")
	public ResponseEntity<?> countOrders() {
		
		try {
			
			Long totalOrder = orderService.countOrders();
			return ResponseEntity.ok().body("total number of orders" + totalOrder);
			
		}
		
		catch(Exception e){
			
			return ResponseEntity.internalServerError().body("no order exists");
			
		}
	
		
	}
	
	
	
	
}


