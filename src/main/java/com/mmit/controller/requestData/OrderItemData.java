package com.mmit.controller.requestData;

public class OrderItemData {
	
	private int id;
	private int qty;
 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "OrderItemData [id=" + id + ", qty=" + qty + "]";
	}
	
	
}
