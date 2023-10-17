package com.springboot.ordermanagement.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.springboot.ordermanagement.controller.CustomerController;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.request.CustomerRequest;
import com.springboot.ordermanagement.service.CustomerService;

@RunWith(SpringRunner.class)
public class CustomerControllerTest {
	
	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	//before calling each test case, we need to use this annotation
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private CustomerRequest getcustomerRequest() {
		
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setCustomerName("foo");
		customerRequest.setEmail("foo@bar.com");
		customerRequest.setMobile("8885557777");
		customerRequest.setAddress("123 four st");
		customerRequest.setPassword("test@123");
		return customerRequest;
		
	}
	
	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(123L);
		customer.setCustomerName("tester");
		customer.setEmail("admin@tester.com");
		customer.setMobile("3334445555");
		customer.setAddress("444 five st");
		customer.setPassword("admin@123");
		return customer;
	}
	
	
	@Test
	public void createCustomerTestPositive() {
		
		//we need to see if we are calling any other class methods inside the actual method
		//if we are calling we need to use mockito.when().then return();
		//Mockito.any will take any parameter at runtime and this can be called only inside Mockito.when
		Mockito.when( customerService.saveCustomer(Mockito.any())).thenThrow(new RuntimeException());
		//Customer savedCustomer = customerService.saveCustomer(getcustomerRequest());
		ResponseEntity<?> actualResponse = customerController.createCustomer(getcustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		
		
	}
	
	
	
}
