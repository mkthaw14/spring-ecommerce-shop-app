package com.mmit.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mmit.FileUploadUtil;
import com.mmit.model.entity.Product;
import com.mmit.model.repos.*;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product findById(long id) {
		return repo.findById(id).orElse(new Product());
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}
	
	public long findCount()
	{
		return repo.getCount();
	}

	/*Annotating with Transactional means it will do something like this
	 * spring creates a proxy object and inserts transaction codes for you
	 * calling the productService.registerProduct()
	 * 
	 * reference https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth
	 */
	/*
	 * Connection connection = dataSource.getConnection(); // (1)
        try (connection) {
            connection.setAutoCommit(false); // (1)

			productService.registerProduct(parm1, parm2, param3);

            connection.commit(); // (1)
        } catch (SQLException e) {
            connection.rollback(); // (1)
        }
	 * */
	@Transactional(rollbackFor = Exception.class)
	public void registerProduct(Product product, String fileName, MultipartFile uploadPhoto) throws Exception {
		
		if(fileName.equals(""))
			throw new Exception("No photo uploaded");
		if(product.getPrice() < 10000 || product.getPrice() > 500000)
			throw new Exception("Invalid price range");
		
		Product savedProduct = repo.save(product); // save product to get id
		System.out.println("Save product id " + savedProduct.getId());
		
		//if(savedProduct != null)
			//throw new Exception("Unknown exception in save");
		System.out.println("fileName : " + fileName);

		String uploadDir = "uploads/product/" + savedProduct.getId();
		System.out.println(uploadDir);
		
		savedProduct.setPhoto(fileName); // update product photo
		repo.save(savedProduct);
		FileUploadUtil.saveUploadFile(uploadDir, fileName, uploadPhoto);
		
		//if(savedProduct != null)
			//throw new Exception("Unknown exception in save");
	}

	public List<Product> findByCategory(int id) {
		return repo.findByCategory(id);
	}

}
