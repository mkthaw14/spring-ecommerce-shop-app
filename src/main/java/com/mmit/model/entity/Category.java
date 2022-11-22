package com.mmit.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "categories")
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String name;
	private String photo;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products;
	
	@CreatedDate
	private LocalDate created_at;
	@LastModifiedDate
	private LocalDate updated_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getPhotoPath()
	{
		if(this.photo == null)
			return "no image";
		String path = "/uploads/category/" + this.getId() + "/" + this.getPhoto();
		return path;
	}
	
	public LocalDate getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}
	public LocalDate getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDate updated_at) {
		this.updated_at = updated_at;
	}

	

	public List<Product> getProducts() {
		return products;
	}
	public void setProduct(List<Product> products) {
		this.products = products;
	}
	
	
	public boolean noProducts()
	{
		return products.size() == 0;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", photo=" + photo + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + "]";
	}
	
	
}
