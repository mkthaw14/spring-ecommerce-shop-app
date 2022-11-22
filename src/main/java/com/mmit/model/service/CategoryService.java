package com.mmit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mmit.FileUploadUtil;
import com.mmit.model.entity.Category;
import com.mmit.model.repos.*;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepo repo;

	public List<Category> findAll() {
		return repo.findAll();
	}

	public Category findById(int id) {
		return repo.findById(id).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public void registerCategory(Category category, String fileName, MultipartFile uploadFile) throws Exception {
		
		if(fileName.equals(""))
			throw new Exception("no photo uploaded");
		
		Category savedCategory = repo.save(category);
		
		System.out.println("Cat saved id " + savedCategory.getId());
		
		String uploadDir = "uploads/category/" + savedCategory.getId();
		savedCategory.setPhoto(fileName);
		FileUploadUtil.saveUploadFile(uploadDir, fileName, uploadFile);
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

	public long findCount() {
		return repo.getCount();
	}
}
