package com.springboot.ordermanagement.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.springboot.ordermanagement.controller.CustomerController;
import com.springboot.ordermanagement.dao.CustomerDao;
import com.springboot.ordermanagement.model.Customer;
import com.springboot.ordermanagement.request.CustomerRequest;

@RunWith(SpringRunner.class)

public class CustomerServiceTest {

	
	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private CustomerDao customerDao;
	
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
	public void saveCustomerTestPositive() {
		
		Mockito.when(customerDao.save(Mockito.any())).thenReturn(getCustomer());
		Customer actualResponse = customerService.saveCustomer(getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse);
	
	}
	
	@Test
	public void saveCustomerTestNegative() {
		
		
		Mockito.when(customerDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> customerService.saveCustomer(getCustomerRequest()));
		
		
	}
	
	@Test
	public void getCustomerByIdTestPositive() {
		
		Mockito.when(customerDao.findById(Mockito.any())).thenReturn(Optional.of(getCustomer()));
		Customer actualResponse = customerService.getCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse);
		
		
	}
	
	@Test
	public void getCustomerByIdTestNegative() {
		
		
		Mockito.when(customerDao.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, ()-> customerService.getCustomerById(123L));
		
		
	}
	
	@Test
	public void getAllCustomersTestPositive() {
		
		List<Customer> customersList = new ArrayList<>();
		customersList.add(getCustomer());
		Mockito.when(customerDao.findAll()).thenReturn(customersList);
		List<Customer> actualCustomerList = customerService.getAllCustomers();
		assertNotNull(actualCustomerList);
		assertEquals(customersList, actualCustomerList);
		
	}
	
	@Test
	public void getAllCustomersTestNegative() {
		
		List <Customer> customerList = new ArrayList<>();
		Mockito.when(customerDao.findAll()).thenReturn(customerList);
		assertThrows(RuntimeException.class, ()-> customerService.getAllCustomers());
		
		
	}
	
	@Test
	public void getAllCustomersWithPaginationTestPositive() {
		
		
		List<Customer> customersList = new ArrayList<>();
		customersList.add(getCustomer());
		Page<Customer> pageCustomer = new PageImpl<Customer>(customersList);
		Mockito.when(customerDao.findAll(PageRequest.of(0, 1, Sort.by("customerName").descending()))).thenReturn(pageCustomer);
		List<Customer> actualCustomerList = customerService.getAllCustomerWithPagination(0, 1);
		assertNotNull(actualCustomerList);
		assertEquals(customersList, actualCustomerList);
	}
	
	@Test
	public void getAllCustomersWithPaginationTestNegative() {
		
		
		List<Customer> customersList = new ArrayList<>();
		Page<Customer> pageCustomer = new PageImpl<Customer>(customersList);
		Mockito.when(customerDao.findAll(PageRequest.of(0, 1, Sort.by("customerName").descending()))).thenReturn(pageCustomer);
		assertThrows(RuntimeException.class, ()-> customerService.getAllCustomerWithPagination(0,1));
		
	}
	
	@Test
	public void deleteCustomerByIdTestPositive() {
		
		customerService.deleteCustomerById(123L);
		
	}
	
	@Test
	public void getCustomerWithEmailTestPositive() {
		
		Mockito.when(customerDao.findCustomerByEmail(Mockito.any())).thenReturn(getCustomer());
		Customer actualResponse = customerService.getCustomerWithEmail("admin@tester.com");
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse);
		
	}
	
	@Test
	public void getCustomerWithEmailTestNegative() {
		
		Mockito.when(customerDao.findCustomerByEmail(Mockito.any())).thenReturn(getCustomer());
		assertThrows(RuntimeException.class, () -> customerService.getCustomerWithEmail(Mockito.any()));
		
	}
	
	@Test
	public void countCustomersTestPositive() {
		
		Mockito.when(customerDao.count()).thenReturn(3L);
		long actualCount = customerService.countCustomers();
		assertNotNull(actualCount);
		assertEquals(3L, actualCount);
		
	}
	
	@Test
	public void countCustomersTestNegative() {
		
		Mockito.when(customerDao.count()).thenReturn(0L);
		assertThrows(RuntimeException.class, () -> customerService.countCustomers());
		
	}
	
	@Test
	public void updateCustomerTestPostive() {
		Mockito.when(customerDao.findById(Mockito.any())).thenReturn(Optional.of(getCustomer()));
		Mockito.when(customerDao.save(Mockito.any())).thenReturn(getCustomer());
		Customer actualResponse = customerService.updateCustomer(123L, getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse);
		
	}
	
	@Test
	public void updateCustomerTestNegativeA() {
		
		Mockito.when(customerDao.findById(Mockito.any())).thenReturn(Optional.of(getCustomer()));
		Mockito.when(customerDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> customerService.updateCustomer(123L, getCustomerRequest()));
		
	}
	
	@Test
	public void updateCustomerTestNegativeB() {
		
		Mockito.when(customerDao.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(customerDao.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, ()-> customerService.updateCustomer(123L, getCustomerRequest()));
		
	}
	
}
