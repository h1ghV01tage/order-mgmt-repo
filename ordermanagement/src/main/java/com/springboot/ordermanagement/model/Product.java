package com.springboot.ordermanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {

	@Id
	@Column(name="product_id" , nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;
	
	@Column(name="product_name" , nullable = false)
	private String productName;
	
	
	@Column(name="price" , nullable = false)
	private String price;
	
	@Column(name="product_quantity" , nullable = false)
	private String productQuantity;
	
	@Column(name="description" , nullable = false)
	private String description;
	
	
}
