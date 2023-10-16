package com.springboot.ordermanagement.sample;

import org.springframework.stereotype.Component;

@Component
public class SimpleCalculator {

	
	public int add (int a,int b) {
		return a + b;
	}
	
	public int sub (int a,int b) {
		return a - b;
	}
	
	public int multiply(int a,int b) {
		return a * b;
	}
	
}
