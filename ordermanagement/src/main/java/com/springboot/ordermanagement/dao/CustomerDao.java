package com.springboot.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ordermanagement.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
	
	//writing user defined native query
	@Query(nativeQuery = true, value = "select * from customer where email = :email ")
	public Customer findCustomerByEmail(String email);
	
	/*query to find customer whereby address is given 3 values, separated by commas (street,city,country)
	 * 	select * from oms_sep.customer where address like '%st';

		select * from oms_sep.customer where address like '%old%';
		
		select * from oms_sep.customer where address like '999%';
	 */
	
	
}
