package com.springboot.ordermanagement.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.ordermanagement.dao.CustomerDao;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.request.CustomerRequest;

@Service //contains business logic
public class CustomerService {
	
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	public Customer saveCustomer(CustomerRequest customerRequest) {
		
		//take all the request fields from customerRequest and set it in customer model object
		//save customer model object in database and return it
		logger.info("saveCustomer method");
		Customer customer = new Customer ();
		customer.setCustomerName(customerRequest.getCustomerName());
		customer.setEmail(customerRequest.getEmail());
		customer.setMobile(customerRequest.getMobile());
		customer.setPassword(customerRequest.getPassword());
		customer.setAddress(customerRequest.getAddress());
		
		customer = customerDao.save(customer);
		logger.info("saved successfully");
		if(customer == null) {
			
			logger.info("customer not saved exception occured");
			throw new RuntimeException("customer not saved");
		}
		
		
		return customer;
		
	}
	
	public Customer getCustomerById(Long customerId) {
		
		logger.info("get customer by id method started");
		//Optional - used to check value can be present or not present
		Optional <Customer> customerOptional = customerDao.findById(customerId);
		
		if(!customerOptional.isPresent()) {
			logger.error("couldn't find the customer");
			throw  new RuntimeException("customer not found in the database");
		}
		
		Customer customer = customerOptional.get();
		logger.info("customer successfully found");
		return customer;
		
	}
	
	public List<Customer> getAllCustomers() {
		
		List<Customer> customerList = customerDao.findAll();
		
		if(customerList.isEmpty()) {
			
			throw new RuntimeException();
			
		}
		
		return customerList;
		
	}
	
	public List<Customer> getAllCustomerWithPagination(Integer pageNumber, Integer pageSize) {
		
		Page<Customer> pageCustomer = customerDao.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("CustomerName").descending()));
		
		//we need to convert page to list of customers and return list as 
		List<Customer> customerList = new ArrayList<>();
		
		for(Customer cust: pageCustomer){
			customerList.add(cust);
		}
		
		if(customerList.isEmpty()) {
			
			throw new RuntimeException();
		}
			
		
		return customerList;
	}
	
	public void deleteCustomerById(Long customerId) {
		
		customerDao.deleteById(customerId);
		
	}
	
	public Customer getCustomerWithEmail(String email) {
		
		Customer customer = customerDao.findCustomerByEmail(email);
		
		if(customer == null) {
			throw new RuntimeException();
		}
		
		return customer;
		
	}
	
	public Customer updateCustomer(Long customerId, CustomerRequest newCustomerRequest) {
		
		
		Customer updatedCustomer = null;
		Optional <Customer> customerOptional = customerDao.findById(customerId);
		if(customerOptional.isPresent()) {
			
			Customer oldCustomer = customerOptional.get();
			
			oldCustomer.setCustomerName(newCustomerRequest.getCustomerName());
			oldCustomer.setEmail(newCustomerRequest.getEmail());
			oldCustomer.setMobile(newCustomerRequest.getMobile());
			oldCustomer.setAddress(newCustomerRequest.getAddress());
			oldCustomer.setPassword(newCustomerRequest.getPassword());
			
			updatedCustomer = customerDao.save(oldCustomer);
			
			if(updatedCustomer == null) {
				
				throw new RuntimeException();
				
			}
		}
		
		else {

			throw new RuntimeException("Customer not found");

		}
			
		
		return updatedCustomer;
		
	
	}

	public Long countCustomers() {
		
		// TODO Auto-generated method stub
		Long totalCustomer = customerDao.count();
		if(totalCustomer == 0) {
			
			throw new RuntimeException();
			
		}
		
		return totalCustomer;
	}
	
}



