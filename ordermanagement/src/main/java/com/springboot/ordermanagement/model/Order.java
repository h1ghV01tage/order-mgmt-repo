package com.springboot.ordermanagement.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {

	@Id
	@Column(name="order_id" , nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	
	@Column(name="prd_pur_qnty" , nullable = false)
	private String productPurchaseQuantity;
	
	
	@Column(name="total_price" , nullable = false)
	private String totalPrice;
	
	@Column(name="order_status" , nullable = false)
	private String orderStatus;
	
	@Column(name="delivery_address" , nullable = false)
	private String deliveryAddress;
	
	@Column(name="payment_method" , nullable = false)
	private String paymentMethod;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customer_id", nullable=false)
	private Customer customer;

}
