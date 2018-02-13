package com.ecopack.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.config.DateParserConfig;
import com.ecopack.domains.CustomerMasterDetails;
import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.ItemMasterDetails;
import com.ecopack.domains.OrderLineDetails;
import com.ecopack.domains.PaperTypeMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.services.OrderDetailsService;

@RestController
public class OrderDetailsControllers {
	
	@Autowired
	OrderDetailsService orderDetailsService;
	
	@Autowired
	DateParserConfig dateParserConfig;


	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	
	@RequestMapping(value = "/calculateLineDetailsFields", method = RequestMethod.GET)
	public PrintingScheduleAll getPCSHours(@RequestParam String itemSize,@RequestParam String printType,@RequestParam String itemName) {
		
	 PrintingScheduleAll printingScheduleAll=null;
	 int pcsPerSheet=0;
		System.out.println("itemName="+itemName);
		
		String query=null;
		if(printType.equals("A")) {
			 query="SELECT cutter.all_over_cutter FROM "
	 		          + " ecopack_cutter_list_master cutter "
	 		          + " WHERE "
	 		          + " cutter.moulds="+itemSize;
			
		 }else if(printType.equals("C")) {
			 
			 query="SELECT cutter.center_cutter FROM "
	 		          + " ecopack_cutter_list_master cutter "
	 		          + " WHERE "
	 		          + " cutter.moulds="+itemSize;
			 
		 }
		
		
		 try{
		  pcsPerSheet = jdbcTemplate.queryForObject(query, (resultSet, i) -> {
				return resultSet.getInt(1);	
			});
	      }catch(Exception e){
		    pcsPerSheet=0;
	    }
		 System.out.println("pcsPerSheet="+pcsPerSheet);
	//----------------------------------	
		 String[] result = itemName.split("\\s");
		 System.out.println("Item Name="+result[0]);
		String moulds1 = null;
		String firstWord=result[0];

		if (firstWord.charAt(0) == 'I') {
			if (firstWord.contains("_") == true) {
				firstWord = firstWord.split("_")[0];
				if ((firstWord.charAt(firstWord.length() - 1) >= 'a' && firstWord.charAt(firstWord.length() - 1) <= 'z')
						|| (firstWord.charAt(firstWord.length() - 1) >= 'A'
								&& firstWord.charAt(firstWord.length() - 1) <= 'Z'))
					firstWord = firstWord.substring(0, firstWord.length() - 1);
				else if (firstWord.contains("."))
					firstWord = firstWord.substring(0, firstWord.length() - 2);
				else
					firstWord = firstWord.substring(0, firstWord.length());
				System.out.print("character =" + firstWord);
				if (firstWord.length() > 5) {
					moulds1=max(firstWord);
				} else
					moulds1=twolength(firstWord.length() / 2 + 1, firstWord);
			} else {
				moulds1=twolength(firstWord.length() / 2 + 1, firstWord);
			}
		} else //if (firstWord.charAt(0) == 'D') {
		{
			if (itemSize.contains(".") == true) {

				String m = (itemSize.split("\\.", 2)[0]).substring(0, (itemSize.split("\\.", 2)[0]).length() / 2) + "*"
						+ ((itemSize.split("\\.", 2)[0]).split("\\.", 2)[0]).substring(
								(itemSize.split("\\.", 2)[0]).length() / 2)
						+ "." + itemSize.split("\\.", 2)[1];
				System.out.println("m=" + m);
				moulds1 = m;

			} else if (itemSize.contains("*") == true) {
				moulds1 = itemSize;
			} else {
				if ((itemSize.length() == 4) && (itemSize.contains("\\.") == false)) {
					// moulds1=itemSize.substring(0,itemSize.length()/2)+"*"+itemSize.substring(itemSize.length()/2);
					moulds1=twolength(itemSize.length() / 2, itemSize);
				} else if (((itemSize.length() == 6) || (itemSize.length() == 7))
						&& (itemSize.contains("\\.") == false)) {

					// moulds1=itemSize.substring(0,itemSize.length()-4)+"*"+itemSize.substring(itemSize.length()-4,itemSize.length()-2)+"*"+itemSize.substring(itemSize.length()-2);
					moulds1=max(itemSize);
				}
			}

		}
		System.out.println("product code="+moulds1);
		String sqlPerHr;
		int pcsPerHour=0;
		String productCode;
		sqlPerHr="SELECT `pcs_per_hour` FROM `ecopack_product_code_master` WHERE `product_code`=?";
		try{
		pcsPerHour =  jdbcTemplate.queryForObject(sqlPerHr, new Object[] { moulds1 }, Integer.class);
		 productCode=moulds1;
		}catch(EmptyResultDataAccessException e){
			pcsPerHour=0;
		}
		
		printingScheduleAll=new PrintingScheduleAll();
		printingScheduleAll.setPcsPerHour(pcsPerHour);
		printingScheduleAll.setPcsPerSheet(pcsPerSheet);
		printingScheduleAll.setProductCode(moulds1);
		
		
		return printingScheduleAll;
		
	}
	
	public String  max(String firstWord){
		String moulds1=firstWord.substring(0,firstWord.length()-4)+"*"+firstWord.substring(firstWord.length()-4,firstWord.length()-2)+"*"+firstWord.substring(firstWord.length()-2);
	System.out.print("product code ="+moulds1);
	return moulds1;
		}
	public String twolength(int len,String firstWord){
		
				String moulds1;	
					
					moulds1=firstWord.substring(0,len)+"*"+firstWord.substring(len);
			return moulds1;	
	}
	
    
	
	@RequestMapping(value = "/checkOrderNumberIsExists", method = RequestMethod.GET)
	public int checkOrderNumberIsExists(@RequestParam int orderNumber) {
		
		return orderDetailsService.checkOrderNumberIsExists(orderNumber);
	}
	
	@RequestMapping(value = "/loadCustomerDetails", method = RequestMethod.GET)
	public List<CustomerMasterDetails> getAllCustomersDetails() {
		
		return orderDetailsService.getAllCustomers();
	}
	
	@RequestMapping(value = "/loadSalesExecutiveDetails", method = RequestMethod.GET)
	public List<EmployeeMaster> getSalesExecutiveDetails() {

		return orderDetailsService.getSalesExecutiveDetails();
	}
	
	@RequestMapping(value = "/getItemMasterDetails", method = RequestMethod.GET)
	public List<ItemMasterDetails> getAllItemMaster(@RequestParam String itemType) {

		return orderDetailsService.getAllItemMaster(itemType);
	}

	@RequestMapping(value = "/getPaperTypeMasterDetails", method = RequestMethod.GET)
	public List<PaperTypeMaster> getPaperTypeMasterDetails(){

		return orderDetailsService.getPaperTypeMasterDetails();
	}
	
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public @ResponseBody int saveOrder(@RequestBody OrderLineDetails orderLineDetails) {
		
		return orderDetailsService.saveOrder(orderLineDetails);
	}

}
