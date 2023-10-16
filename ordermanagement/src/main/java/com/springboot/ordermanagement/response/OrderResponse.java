package com.springboot.ordermanagement.response;

import com.springboot.ordermanagement.model.Order;

import lombok.Data;

@Data
public class OrderResponse {
	
	private Order data;
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
