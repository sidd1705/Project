package com.ecopack.controllers;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.CustomerMasterDetails;
import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.ItemMasterDetails;
import com.ecopack.domains.LineItemsDetail;
import com.ecopack.domains.ManageOrders;
import com.ecopack.domains.OrderDetails;



@RestController
public class ManageOrdersController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@RequestMapping(value = "/getActionAllLineDetails", method = RequestMethod.GET)
	public List<LineItemsDetail> getActionAllLineDetails(@RequestParam int orderNumber) {
	            System.out.println("order Number="+orderNumber);
	            String queryGet_header_id = "select header_id from ecopack_order_details where order_number=" + orderNumber;
	    		
	    		int headerId = jdbcTemplate.queryForObject(queryGet_header_id, (resultSet, i) -> {
	    			return resultSet.getInt(1);
	    		});
	    		String flag="Entered";
	    		
	    		String queryString="SELECT line.header_id,line.line_id,line.item_id,"
	    				+ " item.item_name,"
	    				+ "line.item_size,"
	    				+"line.design_no,"
	    				+ "line.design_name,"
	    				+ "line.paper_color,"
	    				+"line.paper_gsm,"
	    				+ "line.print_type,"
	    				+ "line.color1,"
	    				+ "line.color2,"
	    				+ "line.item_desc,"
	    				+ "print.paper_type,"
	    				+ "line.order_value,"
	    				+ "line.ordered_qty,"
	    				+ "line.in_stock,"
	    				+ "line.printing_required,"
	    				+ "line.mfg_required"
	    				+ " FROM "
	    				+ " ecopack_lines_items line,"
	    				+"ecopack_item_master item,"
	    				+ "ecopack_paper_type_master print"
	    				+ "  WHERE "
	    				+ " line.item_id = item.item_id AND"
	    				+ " print.paper_type_id=line.paper_type_id"
	    				+ " AND  line.header_id="+headerId+" AND "
	    				 + " `flow_status_code`='"+flag+"'";
	    		
	    		List<LineItemsDetail> orderLineDetails= jdbcTemplate.query(queryString, new RowMapper<LineItemsDetail>() {

	    			@Override
	    			public LineItemsDetail mapRow(ResultSet rs, int rownumber) throws SQLException {

	    			             
	    				LineItemsDetail lineItemDetails = new LineItemsDetail();
	    				lineItemDetails.setHeaderId(rs.getInt(1));
	    				lineItemDetails.setLineId(rs.getInt(2));
	    				lineItemDetails.setItemId(rs.getInt(3));
	    				lineItemDetails.setItemName(rs.getString(4));
	    				lineItemDetails.setItemSize(rs.getString(5));
	    				lineItemDetails.setDesignNo(rs.getString(6));
	    				lineItemDetails.setDesignName(rs.getString(7));
	    				lineItemDetails.setPaperColor(rs.getString(8));
	    				lineItemDetails.setPaperGsm(rs.getString(9));
	    				lineItemDetails.setPrintType(rs.getString(10));
	    				lineItemDetails.setColor1(rs.getString(11));
	    				lineItemDetails.setColor2(rs.getString(12));
	    				lineItemDetails.setItemDesc(rs.getString(13));
	    				lineItemDetails.setPaperType(rs.getString(14));
	    				lineItemDetails.setOrderValue(rs.getInt(15));
	    				lineItemDetails.setOrderedQty(rs.getInt(16));
	    				lineItemDetails.setInStock(rs.getString(17));
	    				lineItemDetails.setPrintingRequired(rs.getString(18));
	    				lineItemDetails.setMfgRequired(rs.getString(19));
	    				
	    				                                    
	    				return lineItemDetails;// new OrderLineDetails(null, lineItemDetails, itemMasterDetails);
	    			}
	    		});

	    		return orderLineDetails;

	    	
	}
	
	
	@RequestMapping(value = "/getAllEnteredOrders", method = RequestMethod.GET)
	public List<ManageOrders> getAllLineItems() {
		
		String status1 ="Entered";
		String status2="Resubmitted";

		String queryString="SELECT " + 
					 " o.header_id," + 
					 " o.order_number," + 
					 " c.customer_name," + 
					 " o.customer_type," + 
					 " e.emp_fullname," + 
					 " o.ordered_date," + 
					 " o.due_date," + 
					 " o.currency," + 
					 " o.total_order_value," + 
					 " count(*) as no_of_line_items," + 
					 " o.flow_status_code" + 
					 " FROM " + 
					 " ecopack_order_details o,"+
					 " ecopack_customer_master c,"+
					 " ecopack_employee_master e,"+
					 " ecopack_user_roles r,"+
					 " ecopack_lines_items l " + 
					 " WHERE " + 
					 " o.customer_id = c.customer_id AND " + 
					 " o.sales_executive_id =e.emp_id AND " + 
				     " e.role_id = r.role_id AND " + 
				     " o.header_id  = l.header_id AND"+
				     " o.flow_status_code IN(?,?) AND NOT (flag ='Yes') " + 
				     " GROUP BY o.header_id";
		
		
		return jdbcTemplate.query(queryString,new Object[] { status1,status2 } ,new RowMapper<ManageOrders>() {

			@Override
			public ManageOrders mapRow(ResultSet rs, int rownumber) throws SQLException {

			             
				OrderDetails orderDetails =new OrderDetails();
					orderDetails.setHeaderId(rs.getInt(1));
					orderDetails.setOrderNumber(rs.getInt(2));
					orderDetails.setCustomerType(rs.getString(4));
					orderDetails.setOrderedDate(rs.getString(6));
					orderDetails.setDueDate(rs.getString(7));
					orderDetails.setCurrency(rs.getString(8));
					orderDetails.setTotalOrderValue(rs.getDouble(9));
					orderDetails.setFlowStatusCode(rs.getString(11));
				
				CustomerMasterDetails customerMaster =new CustomerMasterDetails();
					customerMaster.setCustomerName(rs.getString(3));
				
				EmployeeMaster employeeMaster =new EmployeeMaster();
					employeeMaster.setEmpFullname(rs.getString(5));
				
				int noOfLineItems = rs.getInt(10);
				                                    
				return new ManageOrders(orderDetails, customerMaster, employeeMaster,noOfLineItems);
			}
		});
		
	}
	
	//@Transactional(rollbackFor = Exception.class)   /* use this without try-catch */

	
	
	
	
	@Transactional
	@RequestMapping(value = "/manageOrder", method = RequestMethod.GET)
	public @ResponseBody int deleteRecord(@RequestParam int headerId,@RequestParam String statusAction,@RequestParam String remark){
  
		List<ItemMasterDetails> treeflase = null;
		int status = 0;
		 List<ItemMasterDetails> itemsLists = null;
		 String queryString =null;

		if(statusAction.equals("Approved")){
		queryString = "SELECT line_id,item_id FROM `ecopack_lines_items` WHERE `header_id`="+headerId;
				
		 try{
					 treeflase = jdbcTemplate.query(queryString, new RowMapper<ItemMasterDetails>(){

						@Override
						public ItemMasterDetails mapRow(ResultSet rs, int number) throws SQLException {
							ItemMasterDetails itemMasterDetails=new ItemMasterDetails();
							itemMasterDetails.setLineId(rs.getInt(1));
							itemMasterDetails.setItemId(rs.getInt(2));
							return itemMasterDetails; 
						}
						
					});
		 }catch(Exception e){
						e.printStackTrace();
					}
					
		  for(ItemMasterDetails i:treeflase){
			 lineItemDetails(headerId,i.getLineId(),statusAction);
		  }//for loop
 
	}//if 
			String queryForOrder="UPDATE ecopack_order_details SET flow_status_code=?,remark=? WHERE header_id=?";
		
			  jdbcTemplate.execute(queryForOrder,new PreparedStatementCallback<Integer>(){  
			    @Override  
			    public Integer doInPreparedStatement(PreparedStatement ps)  
			            throws SQLException, DataAccessException {  
			        
			    	ps.setString(1,statusAction);
			    	ps.setString(2, remark);
			    	ps.setInt(3,headerId);
			    	
			        return ps.executeUpdate();  
			              
			    }  
			});  
		 
			String queryForLineItems="UPDATE ecopack_lines_items SET flow_status_code=? WHERE header_id=?";
			
			  jdbcTemplate.execute(queryForLineItems,new PreparedStatementCallback<Integer>(){  
			    @Override  
			    public Integer doInPreparedStatement(PreparedStatement ps)  
			            throws SQLException, DataAccessException {  
			        
			    	ps.setString(1,statusAction);
			    	ps.setInt(2,headerId);
			    	
			        return ps.executeUpdate();  
			              
			    }  
			});  
			  updateOrderDetailsFlag(headerId);
		 
			  
		  return status =1;
		
	}



	
	@RequestMapping(value = "/manageLineItem", method = RequestMethod.GET)
	public @ResponseBody int manageItem(@RequestParam int headerId,@RequestParam int lineId,@RequestParam String statusAction,@RequestParam String remark){
      int flag=0;
		if (statusAction.equals("Approved")) {
			lineItemDetails(headerId, lineId, statusAction);
		flag=1;
		}

		String queryForLineItems = "UPDATE ecopack_lines_items SET flow_status_code=? WHERE line_id=?";

		jdbcTemplate.execute(queryForLineItems, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				ps.setString(1, statusAction);
				ps.setInt(2, lineId);

				return ps.executeUpdate();

			}
		});
		String queryString = "SELECT IF((flow_status_code!='Entered') ,'YES','NO') AS flow_status_code FROM `ecopack_lines_items` WHERE header_id="
				+ headerId;

		List<String> treeflase = jdbcTemplate.query(queryString, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNumber) throws SQLException {

				return rs.getString(1);
			}

		});

		String flag1 = "YES";

		for (String i : treeflase) {
			if (i.equals("NO")) {
				flag1 = "NO";
				break;
			}

		}

		if (flag1.equals("YES")) {
			updateOrderDetailsFlag(headerId);
		}
		return flag;
	
	}
	
	
	public void updateOrderDetailsFlag(int headerId){
			String queryForOrder="UPDATE ecopack_order_details SET flag=? WHERE header_id=?";
			
			  jdbcTemplate.execute(queryForOrder,new PreparedStatementCallback<Integer>(){  
			    @Override  
			    public Integer doInPreparedStatement(PreparedStatement ps)  
			            throws SQLException, DataAccessException {  
			        	
			    	ps.setString(1,"Yes");
			    	ps.setInt(2,headerId);
			    	
			        return ps.executeUpdate();  
			              
			    }  
			}); 
		
		
	}
	
	public void lineItemDetails(int headerId,int lineId,String statusAction ){
		
		String queryForGet = "SELECT pcs_per_sheet,item_size,ordered_qty,print_type,line_id,printing_required,flow_status_code FROM ecopack_lines_items WHERE line_id="+lineId;
			int itemID=0;
			//itemID =i.getItemId();
			 List<ItemMasterDetails> lineIttemsLists = null;
				try{
					lineIttemsLists =jdbcTemplate.query(queryForGet,new RowMapper<ItemMasterDetails>(){

				@Override
				public ItemMasterDetails mapRow(ResultSet rs, int number) throws SQLException {
					ItemMasterDetails itemMasterDetails=new ItemMasterDetails();
					itemMasterDetails.setPcsPerSheet(rs.getInt(1));
					itemMasterDetails.setItemSize(rs.getString(2));
					itemMasterDetails.setOrderedQty(rs.getInt(3));
					itemMasterDetails.setPrintType(rs.getString(4));
					itemMasterDetails.setLineId(rs.getInt(5));
					itemMasterDetails.setPrintingRequired(rs.getString(6));
					itemMasterDetails.setFlowStatusCode(rs.getString(7));
					return itemMasterDetails;
				}
		 });
			}catch(EmptyResultDataAccessException e){
				e.printStackTrace();
			}
			
			
				for(ItemMasterDetails itemsList:lineIttemsLists){

	 				 int pcsPerSheet=0;

	                 pcsPerSheet = itemsList.getPcsPerSheet();

	      
					String queryString1 = null;
				// insert in ecopack_printing_schedule
					System.out.println("status="+itemsList.getFlowStatusCode());
					if(itemsList.getPrintingRequired().equals("Yes")&&itemsList.getFlowStatusCode().equals("Entered")){
						 queryString1 = "insert into ecopack_printing_schedule(header_id,"
																					 + "line_id,"
																					 + "flow_status_code)"
																					 + " values("
								                                                     + headerId + ","
																					 + itemsList.getLineId()+",'"
								                                                     + statusAction+"')";
						jdbcTemplate.update(queryString1);
				
					 }
				 

				}
				
		}



}