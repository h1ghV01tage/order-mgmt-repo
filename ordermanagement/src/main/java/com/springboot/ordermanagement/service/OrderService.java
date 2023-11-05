package com.springboot.ordermanagement.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.ordermanagement.dao.CustomerDao;
import com.springboot.ordermanagement.dao.OrderDao;
import com.springboot.ordermanagement.dao.ProductDao;
import com.springboot.ordermanagement.enums.OrderStatus;
import com.springboot.ordermanagement.enums.PaymentMethod;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.model.Order;
import com.springboot.ordermanagement.model.Product;
import com.springboot.ordermanagement.request.CustomerRequest;
import com.springboot.ordermanagement.request.OrderRequest;

@Service //contains business logic
public class OrderService {
	
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	//@Autowired
	//private CustomerRequest customerRequest;
	
	public Order saveOrder(OrderRequest orderRequest) {
		
		//take all the request fields from orderRequest and set it in order model object
		//save order model object in database and return it
		
		Order order = new Order ();
		
		order.setProductPurchaseQuantity(orderRequest.getProductPurchaseQuantity());
		
		/*
		 * 	order.setTotalPrice(orderRequest.getTotalPrice());
			order.setOrderStatus(orderRequest.getOrderStatus());
		 */
	
		//set the order status
		order.setOrderStatus(OrderStatus.IN_PROGRESS.toString());
		order.setDeliveryAddress(orderRequest.getDeliveryAddress());
		
		//order.setPaymentMethod(orderRequest.getPaymentMethod());
		
		if(PaymentMethod.ONLINE_TRANSACTION.toString().equalsIgnoreCase(orderRequest.getPaymentMethod())) {
			
			order.setPaymentMethod(PaymentMethod.ONLINE_TRANSACTION.toString());
			
		}
		
		else if (PaymentMethod.CREDIT_CARD.toString().equalsIgnoreCase(orderRequest.getPaymentMethod())){
			order.setPaymentMethod(PaymentMethod.CREDIT_CARD.toString());
		}
		
		else if (PaymentMethod.DEBIT_CARD.toString().equalsIgnoreCase(orderRequest.getPaymentMethod())){
			order.setPaymentMethod(PaymentMethod.DEBIT_CARD.toString());
		}
		
		else {
			order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY.toString());
		}
		
		
		//getting customer and product details with the Id value 
		Customer customer = customerService.getCustomerById(orderRequest.getCustomerId());
		order.setCustomer(customer);
	
		Product product = productService.getProductById(orderRequest.getProductId());
		order.setProduct(product);
		
		//set the total price
		int productPrice = Integer.parseInt(product.getPrice()); //conversion data type into integer
		//convert the product purchase quantity into integer
		int prodPurchaseQty = Integer.parseInt(orderRequest.getProductPurchaseQuantity());
		//perform the calculation of total price
		int totalPrice = productPrice * prodPurchaseQty;
		//now convert back to string and send it out
		order.setTotalPrice(String.valueOf(totalPrice));
		

		order = orderDao.save(order);
		logger.info("saved successfully");
		
		if(order == null) {
			
			logger.info("order not saved exception occured");
			throw new RuntimeException("order not saved");
			
		}
	
		return order;
		
	}
	
	public Order getOrderById(Long orderId) {
		
		logger.info("get order by id method started");
		//Optional - used to check value can be present or not present
		Optional <Order> orderOptional = orderDao.findById(orderId);
		
		if(!orderOptional.isPresent()) {
			logger.error("couldn't find the order");
			throw  new RuntimeException("order not found in the database");
		}
		
		Order order = orderOptional.get();
		logger.info("order successfully found");
		return order;
		
	}
	
	public List<Order> getAllOrders() {
		
		List<Order> orderList = orderDao.findAll();
		return orderList;
		
		
	}
	
	public List<Order> getAllOrderWithPagination(Integer pageNumber, Integer pageSize) {
		
		Page<Order> pageOrder = orderDao.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("OrderId").ascending()));
		
		//we need to convert page to list of orders and return list as 
		List<Order> orderList = new ArrayList<>();
		
		for(Order cust: pageOrder){
			orderList.add(cust);
		}
		return orderList;
	}
	
	public void deleteOrderById(Long orderId) {
		
		orderDao.deleteById(orderId);
		
	}
	
	public Order getOrderWithEmail(String email) {
		
		Order order = orderDao.findOrderByEmail(email);
		return order;
		
	}
	
	public Order updateOrder(Long orderId, OrderRequest newOrderRequest) {
		
		
		Order updatedOrder = null;
		Optional <Order> orderOptional = orderDao.findById(orderId);
		if(orderOptional.isPresent()) {
			
			Order oldOrder = orderOptional.get();
			
			oldOrder.setProductPurchaseQuantity(newOrderRequest.getProductPurchaseQuantity());
			
			//getting customer and product details with the Id value 
			Customer customer = customerService.getCustomerById(newOrderRequest.getCustomerId());
			oldOrder.setCustomer(customer);
		
			Product product = productService.getProductById(newOrderRequest.getProductId());
			oldOrder.setProduct(product);
			
			//set the total price
			int productPrice = Integer.parseInt(product.getPrice()); //conversion data type into integer
			//convert the product purchase quantity into integer
			int prodPurchaseQty = Integer.parseInt(newOrderRequest.getProductPurchaseQuantity());
			//perform the calculation of total price
			int totalPrice = productPrice * prodPurchaseQty;
			//now convert back to string and send it out
			oldOrder.setTotalPrice(String.valueOf(totalPrice));
			
			
			//oldOrder.setTotalPrice(newOrderRequest.getTotalPrice());
			
			if(OrderStatus.SHIPPED.toString().equalsIgnoreCase(newOrderRequest.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.SHIPPED.toString());
			}
			else if(OrderStatus.DELIVERED.toString().equalsIgnoreCase(newOrderRequest.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.DELIVERED.toString());
			}
			
			else if(OrderStatus.RETURNED.toString().equalsIgnoreCase(newOrderRequest.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.RETURNED.toString());
			}
			
			else if(OrderStatus.CANCELLED.toString().equalsIgnoreCase(newOrderRequest.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
			}
			
			else {
				oldOrder.setOrderStatus(OrderStatus.IN_PROGRESS.toString());
			}
			
			
			//oldOrder.setOrderStatus(newOrderRequest.getOrderStatus());
			oldOrder.setDeliveryAddress(newOrderRequest.getDeliveryAddress());
			
			
			if(PaymentMethod.ONLINE_TRANSACTION.toString().equalsIgnoreCase(newOrderRequest.getPaymentMethod())) {
				
				oldOrder.setPaymentMethod(PaymentMethod.ONLINE_TRANSACTION.toString());
				
			}
			
			else if (PaymentMethod.CREDIT_CARD.toString().equalsIgnoreCase(newOrderRequest.getPaymentMethod())){
				oldOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD.toString());
			}
			
			else if (PaymentMethod.DEBIT_CARD.toString().equalsIgnoreCase(newOrderRequest.getPaymentMethod())){
				oldOrder.setPaymentMethod(PaymentMethod.DEBIT_CARD.toString());
			}
			
			else {
				oldOrder.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY.toString());
			}
			
			
			//oldOrder.setPaymentMethod(newOrderRequest.getPaymentMethod());
			
			updatedOrder = orderDao.save(oldOrder);
			
			
		}
		
		return updatedOrder;
		
	}

	public Long countOrders() {
		// TODO Auto-generated method stub
		Long totalOrder = orderDao.count();
		
		return totalOrder;
	}
	
}


