package com.ecopack.domains;

import java.util.List;

public class OrderLineDetails {
	
	
	OrderDetails orderDetails;
	LineItemsDetail lineItemDetails;
	ItemMasterDetails itemMasterDetails;
	
	List<LineItemsDetail> lineDetails;
	
	public OrderLineDetails(OrderDetails orderDetails, List<LineItemsDetail> lineDetails) {
		super();
		this.orderDetails = orderDetails;
		this.lineDetails = lineDetails;
	}


	public OrderLineDetails(OrderDetails orderDetails, LineItemsDetail lineItemDetails,
			ItemMasterDetails itemMasterDetails) {
		super();
		this.orderDetails = orderDetails;
		this.lineItemDetails = lineItemDetails;
		this.itemMasterDetails = itemMasterDetails;
	}
	

	public OrderLineDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
	


	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	public LineItemsDetail getLineItemDetails() {
		return lineItemDetails;
	}
	public void setLineItemDetails(LineItemsDetail lineItemDetails) {
		this.lineItemDetails = lineItemDetails;
	}
	public ItemMasterDetails getItemMasterDetails() {
		return itemMasterDetails;
	}
	public void setItemMasterDetails(ItemMasterDetails itemMasterDetails) {
		this.itemMasterDetails = itemMasterDetails;
	}


	public List<LineItemsDetail> getLineDetails() {
		return lineDetails;
	}


	public void setLineDetails(List<LineItemsDetail> lineDetails) {
		this.lineDetails = lineDetails;
	}
	
	

}
