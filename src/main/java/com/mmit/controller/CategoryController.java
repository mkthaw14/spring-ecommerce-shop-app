package com.mmit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mmit.model.entity.Category;
import com.mmit.model.service.CategoryService;

@Controller
public class CategoryController 
{
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/categories")
	public String categoryList(ModelMap map)
	{
		map.addAttribute("categoryList", categoryService.findAll());
		return "category";
	}
	
	@GetMapping("/category/add")
	public String categoryAdd(ModelMap map)
	{
		map.addAttribute("category", new Category());
		return "category-add";
	}
	
	@GetMapping("/category/edit/{id}")
	public String categoryEdit(@PathVariable int id, ModelMap map)
	{
		map.addAttribute("category", categoryService.findById(id));
		return "category-add";
	}
	
	@PostMapping("/category/save")
	public String categorySave(Category category,@RequestParam("upload_file") MultipartFile uploadFile,  RedirectAttributes redirectAttr)
	{
		final int C_ID = category.getId();
		try 
		{
			String fileName = StringUtils.cleanPath(uploadFile.getOriginalFilename());
			categoryService.registerCategory(category, fileName, uploadFile);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			if(C_ID != 0)
				return "redirect:/category/edit/" + C_ID;
			
			redirectAttr.addFlashAttribute("operation", "fail");
			return "redirect:/category/add";
		}
		
		System.out.println("Save operation success");
		redirectAttr.addFlashAttribute("operation", "ok");
		return "redirect:/categories";
	}
	
	@PostMapping("/category/delete")
	public String categoryDelete(@RequestParam("id") int id, RedirectAttributes redirectAttr)
	{
		try 
		{
			categoryService.deleteById(id);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
			return "redirect:/categories";
		}
		
		redirectAttr.addFlashAttribute("operation", "ok");
		return "redirect:/categories";
	}
}
