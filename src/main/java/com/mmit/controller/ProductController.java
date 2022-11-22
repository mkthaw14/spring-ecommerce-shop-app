package com.mmit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mmit.FileUploadUtil;
import com.mmit.model.entity.Product;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.OrderItemService;
import com.mmit.model.service.ProductService;


@Controller
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderItemService orderItemService;

	
	@GetMapping("/products")
	public String productList(ModelMap map)
	{
		map.put("productList", productService.findAll());
		//map.put("operation", operation);
		return "product";
	}
	
	@GetMapping("/product/detail")
	public String productDetail()
	{
		return "product-detail";
	}
	

	
	@GetMapping("/product/edit/{id}")
	public String productEdit(@PathVariable long id, ModelMap map)
	{
		map.put("product", productService.findById(id));
		map.put("categories", categoryService.findAll());
		return "product-add";
	}
	
	@GetMapping("/product/add")
	public String productAdd(ModelMap map)
	{
		map.put("product", new Product());
		map.put("categories", categoryService.findAll());
		return "product-add";
	}
	
	
	@PostMapping("/product/save")
	public String productSave(@ModelAttribute Product product, @RequestParam("upload_file") MultipartFile uploadPhoto, RedirectAttributes redirectAttr, ModelMap map)
	{	
		final long P_ID = product.getId();
		try
		{
			String fileName = org.springframework.util.StringUtils.cleanPath(uploadPhoto.getOriginalFilename());
			productService.registerProduct(product, fileName, uploadPhoto);
			System.out.println(product + " is saved " + fileName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
			
			if(P_ID != 0)
				return "redirect:/product/edit/" + P_ID;
			
			return "redirect:/product/add";
		}
		
		//operation = "ok";
		redirectAttr.addFlashAttribute("operation", "ok");
		return "redirect:/products";
	}
	
	@PostMapping("/product/delete")
	public String delete(@RequestParam("id") long id, RedirectAttributes redirectAttr)
	{
		System.out.println("Id " + id);
		
		try
		{
			productService.deleteById(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
			return "redirect:/products";
		}
		redirectAttr.addFlashAttribute("operation", "ok");
		return "redirect:/products";
	}
	
	@GetMapping("/product/save-fail")
	public String productSaveOperationFail(RedirectAttributes redirectAttr, ModelMap map)
	{
		redirectAttr.addFlashAttribute("alert", "fail");
		return "redirect:/product/add";
	}
}
