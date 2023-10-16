package com.springboot.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ordermanagement.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
	
	//writing user defined native query
	@Query(nativeQuery = true, value = "select * from product where email = :email ")
	public Product findProductByEmail(String email);
	
	/*query to find product whereby address is given 3 values, separated by commas (street,city,country)
	 * 	select * from oms_sep.product where address like '%st';

		select * from oms_sep.product where address like '%old%';
		
		select * from oms_sep.product where address like '999%';
	 */
	
	
}
