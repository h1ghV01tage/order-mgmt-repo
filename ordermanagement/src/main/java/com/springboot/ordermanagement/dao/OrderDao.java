package com.springboot.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ordermanagement.model.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
	
	//writing user defined native query
	@Query(nativeQuery = true, value = "select * from order where email = :email ")
	public Order findOrderByEmail(String email);
	
	/*query to find order whereby address is given 3 values, separated by commas (street,city,country)
	 * 	select * from oms_sep.order where address like '%st';

		select * from oms_sep.order where address like '%old%';
		
		select * from oms_sep.order where address like '999%';
	 */
	
	
}
