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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	private int price;
	private String photo;
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@CreatedDate
	private LocalDate created_at;
	@LastModifiedDate
	private LocalDate updated_at;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getPhotoPath()
	{
		String photoPath = "/uploads/product/" + this.id + "/"+ this.photo;
		
		if(this.photo == null)
			return "no image";
		
		return photoPath;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItem(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public boolean noOrders()
	{
		return orderItems.size() == 0;
	}
	
	/*
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", photo=" + photo + ", description="
				+ description + ", category=" + category + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ "]";
	}
	*/
	
}
