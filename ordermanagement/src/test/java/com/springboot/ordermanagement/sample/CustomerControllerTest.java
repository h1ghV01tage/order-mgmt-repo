package com.springboot.ordermanagement.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockitoSession;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
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
	
	private CustomerRequest getCustomerRequest() {
		
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
		Mockito.when( customerService.saveCustomer(Mockito.any())).thenReturn(getCustomer());
		//Customer savedCustomer = customerService.saveCustomer(getcustomerRequest());
		ResponseEntity<?> actualResponse = customerController.createCustomer(getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		
		
	}
	
	
	
	@Test
	public void createCustomerTestNegative() {
		
		//we need to see if we are calling any other class methods inside the actual method
		//if we are calling we need to use mockito.when().then return();
		//Mockito.any will take any parameter at runtime and this can be called only inside Mockito.when
		Mockito.when( customerService.saveCustomer(Mockito.any())).thenThrow(new RuntimeException());
		//Customer savedCustomer = customerService.saveCustomer(getcustomerRequest());
		ResponseEntity<?> actualResponse = customerController.createCustomer(getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		
		
	}
		
	
	
	@Test
	public void getCustomerByIdTestPositive() {
		
		Mockito.when(customerService.getCustomerById(Mockito.any())).thenReturn(getCustomer());
		ResponseEntity<?> actualResponse = customerController.getCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse.getBody());
			
	}
	
	
	
	@Test
	public void getCustomerByIdTestNegative() {
		
		Mockito.when(customerService.getCustomerById(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = customerController.getCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
			
	}
	
	@Test
	public void getAllCustomerTestPositive() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(getCustomer());
		Mockito.when(customerService.getAllCustomers()).thenReturn(customerList);
		//List<Customer> custList = customerService.getAllCustomers();
		ResponseEntity<?> actualResponse = customerController.getAllCustomer();
		assertNotNull(actualResponse);
		assertEquals(customerList,actualResponse.getBody());
		
	}
	
	@Test
	public void getAllCustomerTestNegative() {
		
		Mockito.when(customerService.getAllCustomers()).thenThrow(new RuntimeException());
		//List<Customer> custList = customerService.getAllCustomers();
		ResponseEntity<?> actualResponse = customerController.getAllCustomer();
		assertNotNull(actualResponse);
		assertEquals("no customers found",actualResponse.getBody());
		
	}
	
	
	@Test
	public void getAllCustomerWithPaginationTestPositive() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(getCustomer());
		Mockito.when(customerService.getAllCustomerWithPagination(Mockito.any(),Mockito.any())).thenReturn(customerList);
		//List<Customer> custList = customerService.getAllCustomers();
		ResponseEntity<?> actualResponse = customerController.getAllCustomerWithPagination(1,0);
		assertNotNull(actualResponse);
		assertEquals(customerList,actualResponse.getBody());
		
	}
	
	@Test
	public void getAllCustomerWithPaginationTestNegative() {
		
		Mockito.when(customerService.getAllCustomerWithPagination(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		//List<Customer> custList = customerService.getAllCustomers();
		ResponseEntity<?> actualResponse = customerController.getAllCustomerWithPagination(1,0);
		assertNotNull(actualResponse);
		assertEquals("no customers found",actualResponse.getBody());
		
	}
	
	@Test
	public void deleteCustomerByIdTestPositive() {
		
		ResponseEntity<?> actualResponse = customerController.deleteCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals("The customer with the customer Id " + 123L + " is deleted successfully!", actualResponse.getBody());
		
	}

	//can't write a negative scenario because the method isn't returning anything.
	//Mockito doesn't allow then throw if the method doesn't returning any type
	
	@Test
	public void getCustomerWithEmailTestPositive() {
		
		Mockito.when(customerService.getCustomerWithEmail(Mockito.any())).thenReturn(getCustomer());
		ResponseEntity<?> actualResponse = customerController.getCustomerWithEmail("test@tester.com");
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse.getBody());
		
	}
	
	@Test
	public void getCustomerWithEmailTestNegative() {
		
		Mockito.when(customerService.getCustomerWithEmail(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = customerController.getCustomerWithEmail("test@tester.com");
		assertNotNull(actualResponse);
		assertEquals("can't find customer associated with that email", actualResponse.getBody());
		
	}
	
	@Test
	public void countCustomerTestPositive() {
		
		Mockito.when(customerService.countCustomers()).thenReturn(1L);
		ResponseEntity<?> actualResponse = customerController.countCustomers();
		assertNotNull(actualResponse);
		assertEquals("total number of customers" + 1L, actualResponse.getBody());
		
	}
	
	@Test
	public void countCustomerTestNegative() {
		
		Mockito.when(customerService.countCustomers()).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = customerController.countCustomers();
		assertNotNull(actualResponse);
		assertEquals("no customers found", actualResponse.getBody());
		
	}
	
	@Test
	public void updateCustomerTestPositive() {
		
		Mockito.when(customerService.updateCustomer(Mockito.any(), Mockito.any())).thenReturn(getCustomer());
		ResponseEntity<?> actualResponse = customerController.updateCustomer(123L, getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse.getBody());
		
	}
	
	@Test
	public void updateCustomerTestNegative() {
		
		Mockito.when(customerService.updateCustomer(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<?> actualResponse = customerController.updateCustomer(123L, getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals("customer not found", actualResponse.getBody());
	}
	
	
}
