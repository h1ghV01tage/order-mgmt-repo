package com.springboot.ordermanagement.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ordermanagement.model.Product;
import com.springboot.ordermanagement.request.ProductRequest;
import com.springboot.ordermanagement.response.ProductResponse;
import com.springboot.ordermanagement.service.ProductService;

@RestController
@RequestMapping(value="/om/v1/product")
public class ProductController {
	/*
	 *API is bridge responsible for communication between response and request
	REST API representational state transfer API, used to handle HTTP requests
	CRUD - > 
	Create: post
	Read / Retrieve :get
	Update: put / patch
	Delete: delete 
	 */
	
	//@RequestBody takes input from front end in json format and passes it to 
	
	Logger logger = LoggerFactory.getLogger((ProductController.class));
	
	
	
	@Autowired
	private ProductService productService;

	@PostMapping(value = "/save")
	public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
		logger.info("started");
		ProductResponse productResponse = new ProductResponse();
		
		try {
			Product savedProduct = productService.saveProduct(productRequest);
			logger.info("product successfuly saved");
			productResponse.setData(savedProduct);
			productResponse.setMessage("Product successfully saved");
			logger.info("product saved into database");
			productResponse.setCode(HttpStatus.OK.toString());
			logger.info("create Product API ended");
			return ResponseEntity.ok().body(productResponse);
		}
		
		catch(Exception e) {
			logger.error("product can't be saved, an exception occured" + e.getMessage());
			productResponse.setData(null);
			productResponse.setMessage("cust omer could not be saved");
			productResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return ResponseEntity.internalServerError().body(productResponse);
			
		}
		
		
			
	}
	
	//@PathVariable - value comes within url
	@GetMapping(value="find/{productid}")
	public ResponseEntity<?> getProductById(@PathVariable ("productid") Long productId) {
		
		try {
			Product product = productService.getProductById(productId);
			logger.info("product fetched successfully");
			return ResponseEntity.ok().body(product);
		}
		
		catch(Exception e) {
			logger.error("product not found");
			return ResponseEntity.internalServerError().body("Product not found");
		}
		
	}
	
	@GetMapping(value="/findall")
	public ResponseEntity<?> getAllProduct () {
		
		List<Product> custList = productService.getAllProducts();
		
		return ResponseEntity.ok().body(custList);
		
	}
	
	/*
	 * pagination: fetching records based on pages
	 * two params: page number and page size
	 * page no: number of page requested
	 * pagesize: how many records in a page
	 * pagenumber 10, no of records 36
	 * page index starts from 0
	 * 
	 * 
	 * Loggers: used to log messages for application
	 * Maintains the hierarchical history of the application
	 * 4 level of loggers
	 * info
	 * error
	 * warn
	 * debug
	 */
	
	@GetMapping("/findallwithpage")
	public ResponseEntity<?> getAllProductWithPagination(@RequestParam("pagesize") Integer pageNumber, @RequestParam("pageno") Integer pageSize) {
		
		List<Product> productList = productService.getAllProductWithPagination(pageNumber, pageSize);
		
		return ResponseEntity.ok().body(productList);
		
	}
	
	@DeleteMapping(value="/delete/{productid}")
	public ResponseEntity<?> deleteProductById(@PathVariable("productid") Long productId) {
		
		productService.deleteProductById(productId);
		return ResponseEntity.ok().body("The product with the product id" + productId + "is deleted");

		
	}
	
	@GetMapping(value="/findbyemail")
	public ResponseEntity<?> getProductWithEmail(@RequestParam("email") String email) {
		
		Product product = productService.getProductWithEmail(email);
		return ResponseEntity.ok().body(product);
	}
	
	@PutMapping(value="/update/{productid}")
	public Product updateProduct(@PathVariable("productid") Long productId, @RequestBody ProductRequest newProductRequest) {
		
		
		Product updatedProduct =  productService.updateProduct(productId, newProductRequest);
		
		return updatedProduct;
		
		
	}
	
	@GetMapping(value="count")
	public ResponseEntity<?> countProducts() {
		
		Long totalProduct = productService.countProducts();
		return ResponseEntity.ok().body("total number of products" + totalProduct);
		
	}
}


