package com.springboot.ordermanagement.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

//we are using Junit v4, Junit is testing used to write test cases
@RunWith(SpringRunner.class)
public class SimpleCalculatorTest {
	
	SimpleCalculator simpleCalculator = new SimpleCalculator();
	
	@Test //specifies that this method is considered the test case
	public void addTestPositive() {
		
		int result = simpleCalculator.add(10, 5);
		assertNotNull(result);
		assertEquals(15, result); //it checks whether
		
	}
	
	@Test //specifies that this method is considered the test case
	public void addTestNegative() {
		
		int result = simpleCalculator.add(10, 5);
		assertNotNull(result);
		assertEquals(10, result); //it checks whether
		
	}
	
	//This is for the substract test
	
	@Test //specifies that this method is considered the test case
	public void substractTestPositive() {
		
		int result = simpleCalculator.sub(10, 5);
		assertNotNull(result);
		assertEquals(5, result); //it checks whether
		
	}
	
	@Test //specifies that this method is considered the test case
	public void substractTestNegative() {
		
		int result = simpleCalculator.sub(10, 5);
		assertNotNull(result);
		assertEquals(10, result); //it checks whether
		
	}
	
	//This is multiplication test
	
	@Test //specifies that this method is considered the test case
	public void umltiplicationTestPositive() {
		
		int result = simpleCalculator.sub(10, 5);
		assertNotNull(result);
		assertEquals(5, result); //it checks whether
		
	}
	
	@Test //specifies that this method is considered the test case
	public void multiplicationTestNegative() {
		
		int result = simpleCalculator.sub(10, 5);
		assertNotNull(result);
		assertEquals(10, result); //it checks whether
		
	}
	
	
	
	
}
