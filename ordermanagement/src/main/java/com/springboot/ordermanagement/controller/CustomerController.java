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

import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.request.CustomerRequest;
import com.springboot.ordermanagement.response.CustomerResponse;
import com.springboot.ordermanagement.service.CustomerService;

@RestController
@RequestMapping(value="/om/v1/customer")
public class CustomerController {
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
	
	Logger logger = LoggerFactory.getLogger((CustomerController.class));
	
	
	
	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/save")
	public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
		logger.info("started");
		CustomerResponse customerResponse = new CustomerResponse();
		
		try {
			Customer savedCustomer = customerService.saveCustomer(customerRequest);
			logger.info("customer successfuly saved");
			customerResponse.setData(savedCustomer);
			customerResponse.setMessage("Customer successfully saved");
			logger.info("customer saved into database");
			customerResponse.setCode(HttpStatus.OK.toString());
			logger.info("create Customer API ended");
			return ResponseEntity.ok().body(customerResponse);
		}
		
		catch(Exception e) {
			logger.error("customer can't be saved, an exception occured" + e.getMessage());
			customerResponse.setData(null);
			customerResponse.setMessage("cust omer could not be saved");
			customerResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return ResponseEntity.internalServerError().body(customerResponse);
			
		}
		
		
			
	}
	
	//@PathVariable - value comes within url
	@GetMapping(value="find/{customerid}")
	public ResponseEntity<?> getCustomerById(@PathVariable ("customerid") Long customerId) {
		
		try {
			Customer customer = customerService.getCustomerById(customerId);
			logger.info("customer fetched successfully");
			return ResponseEntity.ok().body(customer);
		}
		
		catch(Exception e) {
			logger.error("customer not found");
			return ResponseEntity.internalServerError().body("Customer not found");
		}
		
	}
	
	@GetMapping(value="/findall")
	public ResponseEntity<?> getAllCustomer () {
		
		List<Customer> custList = customerService.getAllCustomers();
		
		return ResponseEntity.ok().body(custList);
		
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
	public ResponseEntity<?> getAllCustomerWithPagination(@RequestParam("pagesize") Integer pageNumber, @RequestParam("pageno") Integer pageSize) {
		
		List<Customer> customerList = customerService.getAllCustomerWithPagination(pageNumber, pageSize);
		
		return ResponseEntity.ok().body(customerList);
		
	}
	
	@DeleteMapping(value="/delete/{customerid}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerid") Long customerId) {
		
		customerService.deleteCustomerById(customerId);
		return ResponseEntity.ok().body("The customer with the customer id" + customerId + "is deleted");

		
	}
	
	@GetMapping(value="/findbyemail")
	public ResponseEntity<?> getCustomerWithEmail(@RequestParam("email") String email) {
		
		Customer customer = customerService.getCustomerWithEmail(email);
		return ResponseEntity.ok().body(customer);
	}
	
	@PutMapping(value="/update/{customerid}")
	public Customer updateCustomer(@PathVariable("customerid") Long customerId, @RequestBody CustomerRequest newCustomerRequest) {
		
		
		Customer updatedCustomer =  customerService.updateCustomer(customerId, newCustomerRequest);
		
		return updatedCustomer;
		
		
	}
	
	@GetMapping(value="count")
	public ResponseEntity<?> countCustomers() {
		
		Long totalCustomer = customerService.countCustomers();
		return ResponseEntity.ok().body("total number of customers" + totalCustomer);
		
	}
}


