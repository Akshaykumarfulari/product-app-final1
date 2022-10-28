package com.training.pms.galaxe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.pms.galaxe.model.Product;
import com.training.pms.galaxe.service.ProductService;


//@CrossOrigin(origin="http://localhoost:3000")
@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	public ProductController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping()					//http://localhost:9090/product/		-POST		-BODY (product) 102
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		ResponseEntity<String> responseEntity;
		int pId = product.getProductId();
		if(productService.isProductExists(pId)) {
			responseEntity = new ResponseEntity<String>("Product with product id :"+pId+" already exists", HttpStatus.CONFLICT);
		}
		else
		{
			String message = productService.saveProduct(product);
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	
	@GetMapping				// For Getting all products 	//http://localhost:9090/product
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productService.getProduct();
				
		ResponseEntity<List<Product>> responseEntity;

		if(products.isEmpty()) {
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
		}
		else
		{
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	// For getting single product with productId
	@GetMapping("{productId}") // url - localhost:9090/product/2435678
	public ResponseEntity<Product> getProduct(@PathVariable("productId") Integer productId) {
	
		ResponseEntity<Product> responseEntity;
		Product message = new Product();
		if(productService.isProductExists(productId)) {
			
			message = productService.getProduct(productId);
			responseEntity = new ResponseEntity<Product>(message, HttpStatus.OK);
			
		}
		
		else {
			responseEntity = new ResponseEntity<Product>(message, HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@DeleteMapping("{productId}") // localhost:9090/product/5
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) {
        ResponseEntity<String> responseEntity;
        if (productService.isProductExists(productId)) {
            String message = productService.deleteProduct(productId);
            responseEntity = new ResponseEntity<String>(message,
                    HttpStatus.OK);
        } else {
            String errorMessage = "No Such Product ID Exists";
            responseEntity = new ResponseEntity<String>(errorMessage, HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
	
	
	
	@PutMapping()
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		ResponseEntity<String> responseEntity;
		int pId = product.getProductId();
		if(!productService.isProductExists(pId)) {
			responseEntity = new ResponseEntity<String>("Product with product id :"+pId+" Doesn't Exists", HttpStatus.NOT_FOUND);
		}
		else
		{
			String message = productService.updateProduct(product);
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	
	@GetMapping("searchByProductName/{productName}") // url - localhost:9090/product/2435678
	public ResponseEntity<List<Product>> getProductByName(@PathVariable("productName") String productName) {
	
		ResponseEntity<List<Product>> responseEntity;
		List<Product> products  = productService.searchProduct(productName);
		if(products.isEmpty()) {
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
		}
		else
		{
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		}
		return responseEntity;
	}

	
//	search by product price range
//		http://localhost:9090/product/searchByPriceRange/100/200
	@GetMapping("searchByProductPriceRange/{min}/and/{max}") 
    public ResponseEntity<List<Product>> getProductInRange(@PathVariable("min") Integer min, @PathVariable("max") Integer max) {
    
        ResponseEntity<List<Product>> responseEntity;
        List<Product> products  = productService.searchProduct(min,max);
        if(products.isEmpty()) {
            responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
        }
        else
        {
            responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        }
        return responseEntity;
    }
}