package com.springboot.ordermanagement.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.ordermanagement.request.CustomerRequest;
import com.springboot.ordermanagement.response.CustomerResponse;



@RestController
@RequestMapping(value="/om/v2/customer")
public class CustomerRestTemplateController {

	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	@GetMapping(value="/findusadata")
	public ResponseEntity<?> getUSAData() {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> httpEntity = new HttpEntity <String>("parameters", httpHeaders);
		
		ResponseEntity <?> response = restTemplate.exchange("https://datausa.io/api/data?drilldowns=Nation&measures=Population&year=latest", HttpMethod.GET, httpEntity, String.class);
		
		return response;
	}
	
	
	@PostMapping(value="/saveresttemplate")
	public ResponseEntity<?> createCustomerViaRestTemplate(@RequestBody CustomerRequest customerRequest) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<CustomerRequest> httpEntity = new HttpEntity<>(customerRequest, httpHeaders);
		
		ResponseEntity <?> response = restTemplate.exchange("http://localhost:8080/om/v1/customer/save", HttpMethod.POST, httpEntity, String.class);
		
		return response;
	}
	
	
	@PutMapping(value="/updateresttemplate/{customerid}")
	//Rest template via rest api for put method using exchange
	public ResponseEntity <?> updateCustomerViaRestTemplate(@PathVariable("customerid") Long customerId, @RequestBody CustomerRequest customerRequest){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<CustomerRequest> httpEntity = new HttpEntity<CustomerRequest>(customerRequest, headers);
		ResponseEntity<?> response = restTemplate.exchange("http://localhost:8080/om/v1/customer/update/" + customerId, HttpMethod.PUT,httpEntity,String.class);
		return response;
		
	}
	
	@DeleteMapping(value="/deleteresttemplate/{customerid}")
	public ResponseEntity<?> deleteCustomerViaRestTemplate(@PathVariable("customerid") Long customerId){
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<?> response = restTemplate.exchange("http://localhost:8080/om/v1/customer/delete/" + customerId, HttpMethod.DELETE,httpEntity,String.class);
		return response;
		
	}
	
	@GetMapping(value="/findallasentity")
	public ResponseEntity<?> getAllCustomerAsEntity(){
		
		String endUrl = "http://localhost:8080/om/v2/customer/findallasentity";
		ResponseEntity<?> response = restTemplate.getForEntity(endUrl, List.class);
		return response;
		
	}
	
	@GetMapping(value="/findallasobject")
	public List<?> getAllCustomerAsObject(){
		
		List<?> customerresponse = restTemplate.getForObject("http://localhost:8080/om/v2/customer/findallasobject", List.class);
		return customerresponse;
		
	}
	
	//REST template via REST API for POST method using postForObject
	@PostMapping(value="/savecustomerasobject")
	public CustomerResponse createCustomerAsObject(CustomerRequest customerRequest) {
		
		HttpEntity<CustomerRequest> customerRequestEntity = new HttpEntity<CustomerRequest>(customerRequest);
		CustomerResponse response = restTemplate.postForObject("http://localhost:8080/om/v1/customer/save", customerRequestEntity, CustomerResponse.class); 
		return response;
	}
	
	//REST template via REST API for POST method using postForEntityy
		@PostMapping(value="/savecustomerasentity")
		public ResponseEntity<?> createCustomerAsEntity(CustomerRequest customerRequest) {
			
			HttpEntity<CustomerRequest> customerRequestEntity = new HttpEntity<CustomerRequest>(customerRequest);
			ResponseEntity<?> response = restTemplate.postForEntity("http://localhost:8080/om/v1/customer/save", customerRequestEntity, CustomerResponse.class); 
			return response;
		}
		
	
}
