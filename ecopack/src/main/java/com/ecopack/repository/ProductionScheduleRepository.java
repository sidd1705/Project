package com.ecopack.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecopack.config.DateParserConfig;
import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.domains.ProductCodeMaster;
import com.ecopack.domains.ProductionSchedule;

@Repository
public class ProductionScheduleRepository {
	
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	DateParserConfig dateParserConfig;
	
	int totalProductionForShift;
	String requiredHours;

	
	public List<MachineMaster> getAllProductionMachine() {

		String machine1= "P-399";
		String machine2= "P-400";
		String queryString = "SELECT machine_id,machine_no from ecopack_machine_master WHERE machine_no NOT IN( ?, ?)";
		
		return jdbcTemplate.query(queryString,new Object[]{machine1,machine2}, new RowMapper<MachineMaster>() {
                    @Override
                    public MachineMaster mapRow(ResultSet rs, int i) throws SQLException {
                    	
                    	MachineMaster machine = new MachineMaster();
        				machine.setMachineId(rs.getInt(1));
        				machine.setMachineNo(rs.getString(2));
						return machine;
                    }
		});
        
	}
	
	public List<ProductCodeMaster> getAllProductCode() {

		String query = "SELECT product_code_id,product_code,pcs_per_hour from ecopack_product_code_master";

		List<ProductCodeMaster> listProductCode = jdbcTemplate.query(query, new RowMapper<ProductCodeMaster>() {

			@Override
			public ProductCodeMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

				ProductCodeMaster productCodeMaster = new ProductCodeMaster();
				productCodeMaster.setProductCodeId(rs.getInt(1));
				productCodeMaster.setProductCode(rs.getString(2));
				productCodeMaster.setPcsPerHour(rs.getInt(3));
				return productCodeMaster;
			}
		});

		return listProductCode;
	}
	
	public List<PrintingScheduleAll> getProductionSchedule(){
		String flow_status_code="Done";
		String flow_status_code2="Completed";
		
		String is_mfg_required ="Yes";
		
		
		String query="SELECT "
				+ "orderDetails.header_id,"
				+ "orderDetails.order_number,"
				+ "orderDetails.ordered_date,"
				
				+ "lineItems.item_id,"
				+ "lineItems.item_desc,"
				+ "lineItems.ordered_qty,"
				+ "lineItems.mfg_required,"
				
				+ "itemMaster.item_name,"
				+ "lineItems.item_size,"
				+ "lineItems.design_no,"
				+ "lineItems.design_name,"
				+ "lineItems.paper_color,"
				+ "lineItems.paper_gsm,"
				+ "lineItems.print_type,"
				+ "lineItems.color1,"
				+ "lineItems.color2,"
				
				+ "printing.printing_line_id,"
				+ "lineItems.pcs_per_sheet,"		
				+ "printing.extra_no_of_sheets_to_print,"		
				+ "printing.no_of_sheets_printed,"	
				+ "printing.actual_printing_date,"
				+ "printing.flow_status_code ,"
				+ "lineItems.pcs_per_hour,"
				+ "lineItems.product_code,"
				+ " production.production_line_id,"
				+ " production.remaining_production_qty "
				+ " FROM ecopack_order_details orderDetails,"
				+ " ecopack_lines_items lineItems,"
				+ " ecopack_item_master itemMaster,"
				+ " ecopack_printing_schedule printing LEFT OUTER JOIN"
				+ " ecopack_production_schedule production ON"
				+ " printing.printing_line_id = production.printing_line_id"
				+ " WHERE "
				+ " orderDetails.header_id = lineItems.header_id AND "
				+ " lineItems.header_id = printing.header_id AND "
				+ " lineItems.item_id = itemMaster.item_id AND "
				+ " lineItems.line_id  = printing.line_id AND "
				+ " lineItems.mfg_required='"+is_mfg_required+"' AND"
				+ " printing.flow_status_code IN ('"+flow_status_code+"','"+flow_status_code2+"') AND"
				+ " production.flow_status_code IS NULL";
				//+ " production.printing_line_id IS NULL;";
		
		List<PrintingScheduleAll> orderLinePrinting= jdbcTemplate.query(query, new RowMapper<PrintingScheduleAll>() {

			@Override
			public PrintingScheduleAll mapRow(ResultSet rs, int rownumber) throws SQLException {
				PrintingScheduleAll printingScheduleAll=new PrintingScheduleAll();
				
				printingScheduleAll.setHeaderId(rs.getInt(1));
				printingScheduleAll.setOrderNumber(rs.getInt(2));
				printingScheduleAll.setOrderedDate(rs.getString(3));
				             
				                
//			    LineItemsDetail lineItemsDetail = new LineItemsDetail();
			    
			    printingScheduleAll.setItemId(rs.getInt(4));
			    printingScheduleAll.setItemDesc(rs.getString(5));
			    printingScheduleAll.setOrderedQty(rs.getInt(6));
			    printingScheduleAll.setMfgRequired(rs.getString(7));

								
			//	ItemMasterDetails itemMasterDetails =new ItemMasterDetails();
				printingScheduleAll.setItemName(rs.getString(8));
				printingScheduleAll.setItemSize(rs.getString(9));
				printingScheduleAll.setDesignNo(rs.getString(10));
				printingScheduleAll.setDesignName(rs.getString(11));
				printingScheduleAll.setPaperColor(rs.getString(12));
				printingScheduleAll.setPaperGsm(rs.getString(13));
				printingScheduleAll.setPrintType(rs.getString(14));
				printingScheduleAll.setColor1(rs.getString(15));
				printingScheduleAll.setColor2(rs.getString(16));
								
				
				                    printingScheduleAll.setPrintingLineId(rs.getInt(17));
				                    printingScheduleAll.setPcsPerSheet(rs.getInt(18));
				                    printingScheduleAll.setExtraNoOfSheetsToPrint(rs.getDouble(19));;
				                    printingScheduleAll.setNoOfSheetsPrinted(rs.getInt(20));
				                    printingScheduleAll.setActualPrintingDate(rs.getString(21));
				                    printingScheduleAll.setFlowStatusCode(rs.getString(22));
				                    printingScheduleAll.setPcsPerHour(rs.getInt(23));
				                    printingScheduleAll.setProductCode(rs.getString(24));
				                    
				                    printingScheduleAll.setProductionLineId(rs.getInt(25));
				                    printingScheduleAll.setRemainingProductionQty(rs.getInt(26));
				    				
				
				//ProductionSchedule productionSchedule = null;
				return printingScheduleAll;
				//return new OrderLineProduction(orderDetails, lineItemsDetail, itemMasterDetails, printingScheduleAll, productionSchedule);
			}
		});
		
		return orderLinePrinting;
	}
	
    public int checkMrnNumberIsExists(int mrnNumber) {
		
		String checkQuery="SELECT COUNT(*) FROM ecopack_production_schedule WHERE mrn_number =?";
		return jdbcTemplate.queryForObject(checkQuery, new Object[] { mrnNumber }, Integer.class);
	}

	public int checkMachineBusyStatus(ProductionSchedule productionSchedule) {
		
			String busyHours ="08:00:00";
			int machineId = productionSchedule.getMachineId();
			int productCodeId = productionSchedule.getProductCodeId();
			String shift = productionSchedule.getShift();
			 String scheduleDate = dateParserConfig.utcToLocaleDateParser(productionSchedule.getProductionScheduleDate());
			
			String query=" SELECT " + 
						 "	count(*) " + 
						 "	FROM " + 
						 "	ecopack_production_schedule prod " + 
						 "	WHERE " + 
						 "  prod.machine_id = ? AND " + 
						 "	prod.product_code_id = ? AND " + 
						 "	prod.schedule_date = ? AND " + 
						 "	prod.shift = ? AND " + 
						 "	prod.required_hours =?";
			int machineBusyCount = jdbcTemplate.queryForObject(query, new Object[] { machineId,productCodeId,scheduleDate,shift,busyHours }, Integer.class);
			return machineBusyCount;
	}
	
	
	public int saveProductionSchedule(ProductionSchedule productionSchedule){
	
		int status =0;
	   int isMachineBusyCount = checkMachineBusyStatus(productionSchedule);
	   
	   if(isMachineBusyCount == 0) {
       String scheduleDate = dateParserConfig.utcToLocaleDateParser(productionSchedule.getProductionScheduleDate());
	   
       int productionLineId = productionSchedule.getProductionLineId();
       int pcsPerHour =productionSchedule.getPcsPerHour();
//       int requiredTimeHrs = productionSchedule.getRequiredTimeHrs();
//       int requiredTimeMin = productionSchedule.getRequiredTimeMin();
      
       int productionQty = productionSchedule.getProductionQty();
       
       int totalProductionPerShift =  pcsPerHour * 8;
       String flowStatusCode= "Scheduled";
       
       if(productionLineId == 0) {
    
    	   int remainingProdutionQty = productionQty - totalProductionPerShift;
    	   
    	   if(productionQty < totalProductionPerShift){
    		   
    		   totalProductionForShift = productionQty;
    		   requiredHours = productionSchedule.getRequiredHours();
    	   }else {
    		   totalProductionForShift = totalProductionPerShift;
    		   requiredHours = "08:00";
    	   }
    		
    	  
         String queryString = "insert into ecopack_production_schedule(printing_line_id,mrn_number,production_qty,required_hours,shift,machine_id,product_code_id,pcs_per_hour,operator,schedule_date,product_code,flow_status_code)values(?,?,?,?,?,?,?,?,?,?,?,?)";		
    	 jdbcTemplate.execute(queryString,new PreparedStatementCallback<Integer>(){  
    		   
    		    
    			@Override
    			public Integer doInPreparedStatement(PreparedStatement ps)
    					throws SQLException, DataAccessException {
    				   
    				ps.setInt(1, productionSchedule.getPrintingLineId());
    				ps.setInt(2, productionSchedule.getMrnNumber());
    				ps.setDouble(3, totalProductionForShift);
    				ps.setString(4, requiredHours);
    				ps.setString(5, productionSchedule.getShift());
    				ps.setInt(6, productionSchedule.getMachineId());
    				ps.setInt(7, productionSchedule.getProductCodeId());
    				ps.setInt(8, productionSchedule.getPcsPerHour());
    				ps.setString(9, productionSchedule.getOperator());
    				ps.setString(10, scheduleDate);
    				ps.setString(11, productionSchedule.getProductCode());
    				ps.setString(12, flowStatusCode);
    				
    				return ps.executeUpdate();
    			}  
    		    });  
           
           if(remainingProdutionQty > 0) {
        	   
        	   String queryString2 = "insert into ecopack_production_schedule(printing_line_id,remaining_production_qty)values(?,?)";		
        	   
        	   jdbcTemplate.execute(queryString2,new PreparedStatementCallback<Integer>(){  
        		   
       			@Override
       			public Integer doInPreparedStatement(PreparedStatement ps)
       					throws SQLException, DataAccessException {
       				   
       				ps.setInt(1, productionSchedule.getPrintingLineId());
       				ps.setInt(2,remainingProdutionQty);
       				return ps.executeUpdate();
       			}  
       		    });  
           }
           
       }else if(productionLineId > 0) {
    	
    	   
    	   int  remainingProdutionQty = productionSchedule.getRemainingProductionQty() - totalProductionPerShift;
    	   
    	   if(productionSchedule.getRemainingProductionQty() < totalProductionPerShift){
    		   totalProductionForShift = productionSchedule.getRemainingProductionQty();
    		   requiredHours = productionSchedule.getRequiredHours();
    	   }else {
    		   totalProductionForShift = totalProductionPerShift;
    		   requiredHours = "08:00";
    	   }
    	   
    	  
       
    	   String updateQuery="UPDATE ecopack_production_schedule SET mrn_number=?,production_qty=?,required_hours=?,shift=?,machine_id=?,product_code_id=?,pcs_per_hour=?,operator=?,schedule_date=?,product_code=?,flow_status_code=? WHERE production_line_id=?";
    	   // write update code here
    	   
    	   jdbcTemplate.execute(updateQuery,new PreparedStatementCallback<Integer>(){  
    		   
      			@Override
      			public Integer doInPreparedStatement(PreparedStatement ps)
      					throws SQLException, DataAccessException {
      				   
      				ps.setInt(1, productionSchedule.getMrnNumber());
      				ps.setDouble(2, totalProductionForShift);
      				ps.setString(3, requiredHours);
    				ps.setString(4, productionSchedule.getShift());
    				ps.setInt(5, productionSchedule.getMachineId());
    				ps.setInt(6, productionSchedule.getProductCodeId());
    				ps.setInt(7, productionSchedule.getPcsPerHour());
    				ps.setString(8, productionSchedule.getOperator());
    				ps.setString(9, scheduleDate);
    				ps.setString(10, productionSchedule.getProductCode());
    				ps.setString(11, flowStatusCode);
    				ps.setInt(12, productionSchedule.getProductionLineId());
      				return ps.executeUpdate();
      			}  
      		    }); 
    	   
    	   
           if(remainingProdutionQty > 0) {
        	   
        	   String queryString2 = "insert into ecopack_production_schedule(printing_line_id,remaining_production_qty)values(?,?)";		
        	   
        	   jdbcTemplate.execute(queryString2,new PreparedStatementCallback<Integer>(){  
        		   
       			@Override
       			public Integer doInPreparedStatement(PreparedStatement ps)
       					throws SQLException, DataAccessException {
       				   
       				ps.setInt(1, productionSchedule.getPrintingLineId());
       				ps.setInt(2,remainingProdutionQty);
       				return ps.executeUpdate();
       			}  
       		    });  
           }
       }
       status =1;
	  }else if(isMachineBusyCount > 0) {
		  status =0;
	  }
       return status;
//       String queryString = "insert into ecopack_production_schedule(printing_line_id,mrn_number,production_qty,shift,machine_id,product_code_id,pcs_per_hour,operator,schedule_date,product_code)values(?,?,?,?,?,?,?,?,?,?)";		
//	   return jdbcTemplate.execute(queryString,new PreparedStatementCallback<Integer>(){  
//		   
//			@Override
//			public Integer doInPreparedStatement(PreparedStatement ps)
//					throws SQLException, DataAccessException {
//				   
//				ps.setInt(1, productionSchedule.getPrintingLineId());
//				ps.setInt(2, productionSchedule.getMrnNumber());
//				ps.setDouble(3, productionSchedule.getProductionQty());
//				ps.setString(4, productionSchedule.getShift());
//				ps.setInt(5, productionSchedule.getMachineId());
//				ps.setInt(6, productionSchedule.getProductCodeId());
//				ps.setInt(7, productionSchedule.getPcsPerHour());
//				ps.setString(8, productionSchedule.getOperator());
//				ps.setString(9, scheduleDate);
//				ps.setString(10, productionSchedule.getProductCode());
//				
//				return ps.executeUpdate();
//			}  
//		    });  
  	
}
	
	
//	 public int updateProductionSchedulePriority(List<ProductionScheduleAll> productionScheduleAll){
//		 
//			int status = 0;
//			for(int i=0;i<productionScheduleAll.size();i++){
//				
//				int production_line_id=productionScheduleAll.get(i).getProduction_line_id();
//				int priority_id=productionScheduleAll.get(i).getPriority_id();
//				
//				System.out.println(production_line_id +":"+ priority_id);
//				
//				
//				String query="UPDATE ecopack_production_schedule_all SET priority_id="+priority_id+" WHERE production_line_id="+production_line_id;
//				status=jdbcTemplate.update(query);
//			}
//			
//			return status;
//		}

	
	
}
