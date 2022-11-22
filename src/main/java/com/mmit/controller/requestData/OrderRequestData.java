package com.mmit.controller.requestData;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestData 
{
	//Note : The name of the field variables and setter methods must be similar to the JSON format sent by client
	/*
	 * eg: Consider the JSON format is the following format
	 * The left is key and right is value
	 * 
	 * { 
	 * 		receiver : receiverInfo,
	 * 		itemDataList : cartItem
	 * }
	 * 
	 * The field name in java obj must be as same as the key of JSON 
	 * */
	private OrderReceiverData receiver;
	private List<OrderItemData> itemDataList = new ArrayList<>();
	
	public OrderReceiverData getReceiver() {
		return receiver;
	}

	public void setReceiver(OrderReceiverData receiver) {
		this.receiver = receiver;
	}

	public List<OrderItemData> getItemDataList() {
		return itemDataList;
	}

	public void setItemDataList(List<OrderItemData> itemDataList) {
		this.itemDataList = itemDataList;
	}

	@Override
	public String toString() {
		return "OrderRequestData [receiver=" + receiver + ", itemDataList=" + itemDataList + "]";
	}

}
