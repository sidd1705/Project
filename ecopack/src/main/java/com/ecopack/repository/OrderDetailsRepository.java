package com.ecopack.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecopack.config.DateParserConfig;
import com.ecopack.domains.CustomerMasterDetails;
import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.ItemMasterDetails;
import com.ecopack.domains.LineItemsDetail;
import com.ecopack.domains.OrderDetails;
import com.ecopack.domains.OrderLineDetails;
import com.ecopack.domains.PaperTypeMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.mysql.jdbc.Statement;

@Repository
public class OrderDetailsRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	DateParserConfig dateParserConfig;
	
	
	PrintingScheduleAll printingScheduleAll;
	
	public int checkOrderNumberIsExists(int orderNumber) {
		
		String checkQuery="SELECT COUNT(*) FROM ecopack_order_details WHERE order_number =?";
		return jdbcTemplate.queryForObject(checkQuery, new Object[] { orderNumber }, Integer.class);
	}

	public List<CustomerMasterDetails> getCustomersMasterDetails() {

		String query = "select customer_id,customer_name from ecopack_customer_master";
		try {

			return jdbcTemplate.query(query, new RowMapper<CustomerMasterDetails>() {

				@Override
				public CustomerMasterDetails mapRow(ResultSet rs, int rownumber) throws SQLException {

					CustomerMasterDetails customerDetails = new CustomerMasterDetails();
					customerDetails.setCustomerId(rs.getInt(1));
					customerDetails.setCustomerName(rs.getString(2));

					return customerDetails;
				}
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}
	
	public List<EmployeeMaster> getSalesExecutiveDetails() {

		int roleId = 7;
		String query = "select emp_id,emp_fullname from ecopack_employee_master WHERE role_id =?";
		try {

			return jdbcTemplate.query(query, new Object[] { roleId },new RowMapper<EmployeeMaster>() {

				@Override
				public EmployeeMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

					EmployeeMaster salesExecutive = new EmployeeMaster();
					salesExecutive.setEmpId(rs.getInt(1));
					salesExecutive.setEmpFullname(rs.getString(2));

					return salesExecutive;
				}
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}

	public List<ItemMasterDetails> getItemMasterDetails(String itemType) {
		List<ItemMasterDetails> itemMasterList;
		try {
			String query = "select item_id,"
					+ "item_name,"
					+ "item_size,"
					+ "design_no,"
					+ "design_name,"
					+ "paper_color,"
					+ "paper_gsm,"
					+ "print_type,"
					+ "color1,"
					+ "color2 from ecopack_item_master where item_type=?";
			 itemMasterList=jdbcTemplate.query(query,new Object[] { itemType } ,new RowMapper<ItemMasterDetails>() {

				@Override
				public ItemMasterDetails mapRow(ResultSet rs, int rownumber) throws SQLException {

					ItemMasterDetails itemMaster = new ItemMasterDetails();
					itemMaster.setItemId(rs.getInt(1));
					itemMaster.setItemName(rs.getString(2));
					itemMaster.setItemSize(rs.getString(3));
					itemMaster.setDesignNo(rs.getString(4));
					itemMaster.setDesignName(rs.getString(5));
					itemMaster.setPaperColor(rs.getString(6));
					itemMaster.setPaperGsm(rs.getString(7));
					itemMaster.setPrintType(rs.getString(8));
					itemMaster.setColor1(rs.getString(9));
					itemMaster.setColor2(rs.getString(10));

					return itemMaster;
				}
			});
			 
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}
		return itemMasterList;
	}
	
	
	public List<PaperTypeMaster> getPaperTypeMasterDetails() {

		try {
			String query = "select paper_type_id,paper_type from ecopack_paper_type_master";
			return jdbcTemplate.query(query, new RowMapper<PaperTypeMaster>() {

				@Override
				public PaperTypeMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

					PaperTypeMaster paperType = new PaperTypeMaster();
					paperType.setPaperTypeId(rs.getInt(1));
					paperType.setPaperType(rs.getString(2));

					return paperType;
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}
	
	
	public int saveOrder(OrderLineDetails orderLineDetails) {
		
		int saveStatus =0;
		int failedCount=0;
		int affectedCount=0;

		OrderDetails orderDetails =orderLineDetails.getOrderDetails();
		List<LineItemsDetail>  listLineDetails= orderLineDetails.getLineDetails(); 
		
		System.out.println(orderDetails.getOrderNumber());
		System.out.println(listLineDetails.size());
		
        String orderedDate = dateParserConfig.utcToLocaleDateParser(orderDetails.getOrderedDate());
	
		String flowStatusCode = "Entered";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		String query = "insert into ecopack_order_details(order_number,customer_type,customer_id,ordered_date,sales_executive_id,"
					+ "currency,total_order_value,flow_status_code) values(?,?,?,?,?,?,?,?)";
		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(query, new String[] {"header_id"});
					    ps.setInt(1, orderDetails.getOrderNumber());
					    ps.setString(2,orderDetails.getCustomerType());
					    ps.setInt(3, orderDetails.getCustomerId());
					    ps.setString(4,orderedDate);
					    ps.setString(5, orderDetails.getSalesExecutiveId());
					    ps.setString(6, orderDetails.getCurrency());
					    ps.setDouble(7, orderDetails.getTotalOrderValue());
					    ps.setString(8, flowStatusCode);
	    	            return ps;
	    	        }
	    	    },
	    	    keyHolder);
			
			int headerId = keyHolder.getKey().intValue();
	
			String query2 = "insert into ecopack_lines_items(header_id,item_id,item_size,design_no,design_name,paper_color,paper_gsm,print_type,color1,color2,pcs_per_sheet,pcs_per_hour,product_code,item_desc,paper_type_id,order_value,ordered_qty,in_stock,printing_required,mfg_required,due_date,flow_status_code)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			int[] affectedRows = jdbcTemplate.batchUpdate(query2,new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	            	   
	            	  String dueDate = dateParserConfig.utcToLocaleDateParser(listLineDetails.get(i).getDueDate());
	            	
	            	 	ps.setInt(1, headerId);
	            	 	ps.setInt(2, listLineDetails.get(i).getItemId());
	            	 	ps.setString(3, listLineDetails.get(i).getItemSize());
	            	 	ps.setString(4, listLineDetails.get(i).getDesignNo());
	            	 	ps.setString(5, listLineDetails.get(i).getDesignName());
	            	 	ps.setString(6, listLineDetails.get(i).getPaperColor());
	            		ps.setString(7, listLineDetails.get(i).getPaperGsm());
	            		ps.setString(8, listLineDetails.get(i).getPrintType());
	            		ps.setString(9, listLineDetails.get(i).getColor1());
	            		ps.setString(10, listLineDetails.get(i).getColor2());
	            		ps.setInt(11, listLineDetails.get(i).getPcsPerSheet());
	            		ps.setInt(12, listLineDetails.get(i).getPcsPerHour());
	            	 	ps.setString(13, listLineDetails.get(i).getProductCode());
	            	 	ps.setString(14, listLineDetails.get(i).getItemDesc());
	            	 	ps.setInt(15, listLineDetails.get(i).getPaperTypeId());
	            	 	ps.setDouble(16, listLineDetails.get(i).getOrderValue());
	            	 	ps.setInt(17, listLineDetails.get(i).getOrderedQty());
	            	 	ps.setString(18, listLineDetails.get(i).getInStock());
	            	 	ps.setString(19, listLineDetails.get(i).getPrintingRequired());
	            	 	ps.setString(20, listLineDetails.get(i).getMfgRequired());
	            	 	ps.setString(21, dueDate);
	            	 	ps.setString(22, flowStatusCode);
	            }

	            @Override
	            public int getBatchSize() {
	                return listLineDetails.size();
	            }
	        });
			
			for(int affectedRow : affectedRows){
	            if(affectedRow == Statement.EXECUTE_FAILED){
	                failedCount++;
	            }else{
	            	affectedCount++;
	            }
	        }
			
			if(affectedCount == affectedRows.length) {
				saveStatus = 1;
				System.out.println("No of Saved Rows : "+affectedCount);
			}else {
				saveStatus = 0;
				System.out.println("No of failed Rows while Save Line Details : "+failedCount);
			}
			return saveStatus;
		
	}

}
