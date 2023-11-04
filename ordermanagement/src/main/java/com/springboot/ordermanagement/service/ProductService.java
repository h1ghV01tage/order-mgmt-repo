package com.springboot.ordermanagement.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.ordermanagement.dao.ProductDao;
import com.springboot.ordermanagement.model.Product;
import com.springboot.ordermanagement.request.ProductRequest;

@Service //contains business logic
public class ProductService {
	
	
	Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductDao productDao;
	
	public Product saveProduct(ProductRequest productRequest) {
		
		//take all the request fields from productRequest and set it in product model object
		//save product model object in database and return it
		
		Product product = new Product ();
		
		product.setProductName(productRequest.getProductName());
		product.setPrice(productRequest.getPrice());
		product.setProductQuantity(productRequest.getProductQuantity());
		product.setDescription(productRequest.getDescription());
		
		productDao.save(product);
		logger.info("saved successfully");
		
		if(product == null) {
			
			logger.info("product not saved exception occured");
			throw new RuntimeException("product not saved");
		}
		
		
			
		return product;
		
	}
	
	public Product getProductById(Long productId) {
		
		logger.info("get product by id method started");
		//Optional - used to check value can be present or not present
		Optional <Product> productOptional = productDao.findById(productId);
		
		if(!productOptional.isPresent()) {
			logger.error("couldn't find the product");
			throw  new RuntimeException("product not found in the database");
		}
		
		Product product = productOptional.get();
		logger.info("product successfully found");
		return product;
		
	}
	
	public List<Product> getAllProducts() {
		
		List<Product> productList = productDao.findAll();
		return productList;
		
		
	}
	
	public List<Product> getAllProductWithPagination(Integer pageNumber, Integer pageSize) {
		
		Page<Product> pageProduct = productDao.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("ProductName").ascending()));
		
		//we need to convert page to list of products and return list as 
		List<Product> productList = new ArrayList<>();
		
		for(Product cust: pageProduct){
			productList.add(cust);
		}
		return productList;
	}
	
	public void deleteProductById(Long productId) {
		
		productDao.deleteById(productId);
		
	}
	
	public Product getProductWithEmail(String email) {
		
		Product product = productDao.findProductByEmail(email);
		return product;
		
	}
	
	public Product updateProduct(Long productId, ProductRequest newProductRequest) {
		
		
		Product updatedProduct = null;
		Optional <Product> productOptional = productDao.findById(productId);
		if(productOptional.isPresent()) {
			
			Product oldProduct = productOptional.get();
			
			oldProduct.setProductName(newProductRequest.getProductName());
			oldProduct.setPrice(newProductRequest.getPrice());
			oldProduct.setProductQuantity(newProductRequest.getProductQuantity());
			oldProduct.setDescription(newProductRequest.getDescription());
			
			updatedProduct = productDao.save(oldProduct);
			
	
			
		}
		
		return updatedProduct;
		
	}

	public Long countProducts() {
		// TODO Auto-generated method stub
		Long totalProduct = productDao.count();
		
		return totalProduct;
	}
	
}


