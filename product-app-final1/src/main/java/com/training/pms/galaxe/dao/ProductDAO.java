package com.training.pms.galaxe.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.training.pms.galaxe.model.Product;

public interface ProductDAO extends CrudRepository<Product,Integer> {
	
	public List<Product> findByProductName(String productName);
	public List<Product> findByPrice(int price);
	public List<Product> findByPriceBetween(int minPrice,int maximumPrice);
	public List<Product> findByQuantityOnHandGreaterThan(int quantityOnHand);	
	

}
