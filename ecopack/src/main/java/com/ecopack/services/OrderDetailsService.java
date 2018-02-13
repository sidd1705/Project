package com.ecopack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ecopack.domains.CustomerMasterDetails;
import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.ItemMasterDetails;
import com.ecopack.domains.OrderLineDetails;
import com.ecopack.domains.PaperTypeMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.repository.OrderDetailsRepository;

@Service
public class OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	
   PrintingScheduleAll printingScheduleAll;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public int checkOrderNumberIsExists(int orderNumber) {
		
		return orderDetailsRepository.checkOrderNumberIsExists(orderNumber);
	}
	
	public List<CustomerMasterDetails> getAllCustomers(){
		
           return orderDetailsRepository.getCustomersMasterDetails();
	}
	
	public List<EmployeeMaster> getSalesExecutiveDetails() {
		
		return orderDetailsRepository.getSalesExecutiveDetails();
	}
	
	public List<ItemMasterDetails> getAllItemMaster(String itemType){
		
		return orderDetailsRepository.getItemMasterDetails(itemType);
	}

	public List<PaperTypeMaster> getPaperTypeMasterDetails() {
		return orderDetailsRepository.getPaperTypeMasterDetails();
	}
	
	public int saveOrder(OrderLineDetails orderLineDetails) {
		
		return orderDetailsRepository.saveOrder(orderLineDetails);
	}
	
}
