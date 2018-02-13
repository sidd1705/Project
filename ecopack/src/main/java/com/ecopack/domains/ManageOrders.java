package com.ecopack.domains;

public class ManageOrders {

	
	OrderDetails orderDetails;
	CustomerMasterDetails customerMaster;
	EmployeeMaster employeeMaster;
	
	private int noOfLineItems;

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public CustomerMasterDetails getCustomerMaster() {
		return customerMaster;
	}

	public void setCustomerMaster(CustomerMasterDetails customerMaster) {
		this.customerMaster = customerMaster;
	}

	public EmployeeMaster getEmployeeMaster() {
		return employeeMaster;
	}

	public void setEmployeeMaster(EmployeeMaster employeeMaster) {
		this.employeeMaster = employeeMaster;
	}

	public int getNoOfLineItems() {
		return noOfLineItems;
	}

	public void setNoOfLineItems(int noOfLineItems) {
		this.noOfLineItems = noOfLineItems;
	}

	public ManageOrders(OrderDetails orderDetails, CustomerMasterDetails customerMaster, EmployeeMaster employeeMaster,
			int noOfLineItems) {
		super();
		this.orderDetails = orderDetails;
		this.customerMaster = customerMaster;
		this.employeeMaster = employeeMaster;
		this.noOfLineItems = noOfLineItems;
	}

	public ManageOrders() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	
	
	
}
