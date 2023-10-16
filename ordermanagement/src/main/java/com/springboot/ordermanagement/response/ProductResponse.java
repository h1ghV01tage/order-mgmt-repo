package com.springboot.ordermanagement.response;

import com.springboot.ordermanagement.model.Product;

import lombok.Data;

@Data
public class ProductResponse {
	
	private Product data;
	private String message;
	private String code;
	
	/*
	 * 200 success
	 * 404 not found
	 * 400 bad request
	 * 401 unauthorized
	 * 500 internal server error
	 */
	
	
	
}
