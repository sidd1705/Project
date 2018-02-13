package com.ecopack.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecopack.config.DateParserConfig;
import com.ecopack.domains.LossReport;
import com.ecopack.domains.LossTypesMaster;
import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.MachineTypesMaster;
import com.ecopack.domains.OEELossReport;
import com.ecopack.domains.OEEProductionPlan;
import com.ecopack.domains.OEEReport;
import com.ecopack.domains.ProductionSchedule;
import com.mysql.jdbc.Statement;

@Repository
public class OEEReportRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	DateParserConfig dateParserConfig;

	public List<MachineTypesMaster> getAllMachineTypes() {

		String query = "select machine_type_id,machine_type from ecopack_machine_types_master";

		List<MachineTypesMaster> listMahinceTypes = jdbcTemplate.query(query, new RowMapper<MachineTypesMaster>() {

			@Override
			public MachineTypesMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

				MachineTypesMaster machineTypes = new MachineTypesMaster();
				machineTypes.setMachineTypeId(rs.getInt(1));
				machineTypes.setMachineType(rs.getString(2));
				return machineTypes;
			}
		});

		return listMahinceTypes;
	}
	
	public List<MachineMaster> getAllMachines() {

		String query = "select machine_id,machine_no from ecopack_machine_master";

		List<MachineMaster> listMahinceTypes = jdbcTemplate.query(query, new RowMapper<MachineMaster>() {

			@Override
			public MachineMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

				MachineMaster machine = new MachineMaster();
				machine.setMachineId(rs.getInt(1));
				machine.setMachineNo(rs.getString(2));
				return machine;
			}
		});

		return listMahinceTypes;
	}

	public List<LossTypesMaster> loadLossReport(int machineTypeId) {

		String query="SELECT "
				+ "lossTypes.machine_loss_id,"
				+ "lossTypes.machine_type_id,"
				+ "lossTypes.loss_number,"
				+ "lossTypes.loss_type"
				+ " FROM ecopack_loss_types_master lossTypes"
				+ " WHERE lossTypes.machine_type_id=?";
		
		List<LossTypesMaster> lossTypesMaster= jdbcTemplate.query(query,new Object[] { machineTypeId } ,new RowMapper<LossTypesMaster>() {

			@Override
			public LossTypesMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

				LossTypesMaster lossTypes=new LossTypesMaster();
				lossTypes.setMachineLossId(rs.getInt(1));
				lossTypes.setMachineTypeId(rs.getInt(2));
				lossTypes.setLossNumber(rs.getInt(3));
				lossTypes.setLossType(rs.getString(4));
				

				return lossTypes;
			}
		});
		
		return lossTypesMaster;
	}
	
	public int findOrderNumber(int machineId, int productCodeId, String productionScheduleDate, String shift, int mrnNumber) {
		
				String query="SELECT" + 
						     " orders.order_number " + 
						     " FROM " + 
						     " ecopack_order_details orders," + 
						     " ecopack_printing_schedule printing," + 
						     " ecopack_production_schedule prod " + 
						     " WHERE " + 
						     " orders.header_id = printing.header_id AND " + 
						     " prod.printing_line_id = printing.printing_line_id AND " + 
						     " prod.machine_id = ? AND " + 
						     " prod.product_code_id = ? AND " + 
						     " prod.schedule_date = ? AND" + 
						     " prod.shift = ? AND " + 
						     " prod.mrn_number= ? ";
				try {
					
					return jdbcTemplate.queryForObject(query, new Object[] { machineId,productCodeId,productionScheduleDate,shift,mrnNumber }, Integer.class);
				} catch (EmptyResultDataAccessException e) {
					return 0;
				}
	}
		
	public  List<OEEReport> getScheduledProduction(ProductionSchedule prodSchedule){
		
		
		int mrnNumber = prodSchedule.getMrnNumber();
        int machineId = prodSchedule.getMachineId();
        String productionScheduleDate = dateParserConfig.utcToLocaleDateParser(prodSchedule.getProductionScheduleDate());
        String shift = prodSchedule.getShift();
        int productCodeId = prodSchedule.getProductCodeId();
       
         int orderNumber = findOrderNumber(machineId,productCodeId,productionScheduleDate,shift,mrnNumber);
      
        
		String queryString ="SELECT " + 
					  "prod.machine_id," + 
					  "prod.shift," + 
					  "prod.schedule_date," + 
					  "prod.product_code_id," + 
					  "prod.pcs_per_hour,"+
					  "prod.production_qty,"+
					  "prodReport.machine_type_id,"+
					  "prodReport.production_plan_id,"+
					  "prodReport.prod_plan_first_hour," + 
					  "prodReport.actual_prod_first_hour," + 
					  "prodReport.prod_plan_second_hour," + 
					  "prodReport.actual_prod_second_hour," + 
					  "prodReport.prod_plan_third_hour," + 
					  "prodReport.actual_prod_third_hour," + 
					  "prodReport.prod_plan_fourth_hour," + 
					  "prodReport.actual_prod_fourth_hour," + 
					  "prodReport.prod_plan_fifth_hour," + 
					  "prodReport.actual_prod_fifth_hour," + 
					  "prodReport.prod_plan_sixth_hour," + 
					  "prodReport.actual_prod_sixth_hour," + 
					  "prodReport.prod_plan_seventh_hour," + 
					  "prodReport.actual_prod_seventh_hour," + 
					  "prodReport.prod_plan_eightth_hour," + 
					  "prodReport.actual_prod_eightth_hour," + 
					  "prodReport.total_production_plan," + 
					  "prodReport.total_actual_production," + 
					  "prodReport.operator," + 
					  "prodReport.maintenance_operator," + 
					  "prodReport.prod_supervisor," + 
					  "prodReport.prod_manager," + 
					  "lossReport.oee_loss_report_id," + 
					  "lossReport.machine_type_id," + 
					  "lossReport.machine_id," + 
					  "lossReport.machine_loss_id," + 
					  "lossReport.shift," + 
					  "lossReport.operator_id," + 
					  "lossReport.loss_report_date," + 
					  "lossReport.firstHour," + 
					  "lossReport.secondHour," + 
					  "lossReport.thirdHour," + 
					  "lossReport.fourthHour," + 
					  "lossReport.fifthHour," + 
					  "lossReport.sixthHour," + 
					  "lossReport.seventhHour," + 
					  "lossReport.eightthHour," + 
					  "lossReport.totalHours " + 
				" FROM " + 
				" ecopack_production_schedule prod " + 
				" LEFT OUTER JOIN ecopack_oee_production_report prodReport ON " + 
				"	prod.machine_id = prodReport.machine_id AND " + 
				"	prod.schedule_date = prodReport.oee_report_date AND " + 
				"	prod.shift = prodReport.shift AND " + 
				"	prod.product_code_id = prodReport.product_code_id1 "+
			    " LEFT OUTER JOIN ecopack_oee_loss_report lossReport ON "+
			    " 	lossReport.machine_id = prodReport.machine_id AND"+
				"	lossReport.loss_report_date = prodReport.oee_report_date AND"+
			    "	lossReport.shift = prodReport.shift AND"+
				"	lossReport.product_code_id = prodReport.product_code_id1 AND"
				+ " lossReport.machine_type_id = prodReport.machine_type_id "+
				" WHERE "+
				" prod.mrn_number=? AND"+
				" prod.machine_id=? AND" + 
				" prod.schedule_date=? AND " + 
				" prod.shift =? AND " + 
				" prod.product_code_id =?";
	
		return jdbcTemplate.query(queryString,new Object[] { mrnNumber,machineId,productionScheduleDate,shift,productCodeId },new RowMapper<OEEReport>(){  
			    @Override  
			    public OEEReport mapRow(ResultSet rs, int rownumber) throws SQLException {  
			    	 
			    ProductionSchedule productionSchedule=new ProductionSchedule();
			    	productionSchedule.setMachineId(rs.getInt(1));
			    	productionSchedule.setShift(rs.getString(2));
			    	productionSchedule.setProductionScheduleDate(rs.getString(3));
			    	productionSchedule.setProductCodeId(rs.getInt(4));
			    	productionSchedule.setPcsPerHour(rs.getInt(5));
			    	productionSchedule.setProductionQty(rs.getInt(6));
			    	productionSchedule.setOrderNumber(orderNumber);
			    	
				OEEProductionPlan oeeProductionPlan =new OEEProductionPlan();
					oeeProductionPlan.setMachineTypeId(rs.getInt(7));
					oeeProductionPlan.setProductionPlanId(rs.getInt(8));
					oeeProductionPlan.setProdPlanFirstHour(rs.getInt(9));
					oeeProductionPlan.setActualProdFirstHour(rs.getInt(10));
					oeeProductionPlan.setProdPlanSecondHour(rs.getInt(11));
					oeeProductionPlan.setActualProdSecondHour(rs.getInt(12));
					oeeProductionPlan.setProdPlanThirdHour(rs.getInt(13));
					oeeProductionPlan.setActualProdThirdHour(rs.getInt(14));
					oeeProductionPlan.setProdPlanFourthHour(rs.getInt(15));
					oeeProductionPlan.setActualProdFourthHour(rs.getInt(16));
					oeeProductionPlan.setProdPlanFifthHour(rs.getInt(17));
					oeeProductionPlan.setActualProdFifthHour(rs.getInt(18));
					oeeProductionPlan.setProdPlanSixthHour(rs.getInt(19));
					oeeProductionPlan.setActualProdSixthHour(rs.getInt(20));
					oeeProductionPlan.setProdPlanSeventhHour(rs.getInt(21));
					oeeProductionPlan.setActualProdSeventhHour(rs.getInt(22));
					oeeProductionPlan.setProdPlanEightthHour(rs.getInt(23));
					oeeProductionPlan.setActualProdEightthHour(rs.getInt(24));
					oeeProductionPlan.setTotalProductionPlan(rs.getInt(25));
					oeeProductionPlan.setTotalActualProduction(rs.getInt(26));
					oeeProductionPlan.setOperator(rs.getString(27));
					oeeProductionPlan.setMaintenanceOperator(rs.getString(28));
					oeeProductionPlan.setProductionSupervisor(rs.getString(29));
					oeeProductionPlan.setProductionManager(rs.getString(30));
					
				LossReport lossReport =new LossReport();
					lossReport.setLossReportId(rs.getInt(31));
					lossReport.setMachineTypeId(rs.getInt(32));
					lossReport.setMachineId(rs.getInt(33));
					lossReport.setMachineLossId(rs.getInt(34));
					lossReport.setShiftId(rs.getString(35));
					lossReport.setOperatorId(rs.getInt(36));
					lossReport.setLossReportDate(rs.getString(37));
					lossReport.setFirstHour(rs.getInt(38));
					lossReport.setSecondHour(rs.getInt(39));
					lossReport.setThirdHour(rs.getInt(40));
					lossReport.setFourthHour(rs.getInt(41));
					lossReport.setFifthHour(rs.getInt(42));
					lossReport.setSixthHour(rs.getInt(43));
					lossReport.setSeventhHour(rs.getInt(44));
					lossReport.setEightthHour(rs.getInt(45));
					lossReport.setTotalHours(rs.getInt(46));
			    
				return new OEEReport(productionSchedule, oeeProductionPlan, lossReport);
			 }  
		});
		
	}  
	
	public int saveOEEReport(OEEReport oeeReport) throws ParseException,Exception {
		int status=0;
		ProductionSchedule productionSchedule = oeeReport.getProductionSchedule();
		
		OEEProductionPlan productionPlanDetails = oeeReport.getOeeProductionPlan();
		List<OEELossReport> oeeLossReport = oeeReport.getOeeLossReportList();
		
		int createdBy = oeeReport.getCurrentUserId();
		
		String oeeReportCompositeKey =  generateOEEReportCompositeKey(productionSchedule);
		
		if(oeeReportCompositeKey != null){
			int oeeProductionReportSaveStatus = saveProductionPlanDetails(oeeReportCompositeKey,productionSchedule,productionPlanDetails,createdBy);
	        int oeeLossReportSaveStatus = saveProductionLossReport(oeeReportCompositeKey,productionSchedule,oeeLossReport,createdBy);
		
	        if(oeeProductionReportSaveStatus == 1 && oeeLossReportSaveStatus == 1) {
				status =1;
			}
		}
        
		
		return status;
	}
	
	public String generateOEEReportCompositeKey(ProductionSchedule productionSchedule) throws ParseException {
		
		String prodScheduleDate= dateParserConfig.utcToLocaleDateParser(productionSchedule.getProductionScheduleDate());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = dateFormat.parse(prodScheduleDate);
			
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
	    String productionScheduleDate =  dateFormat2.format(date);
	    
	    int mrnNumber = productionSchedule.getMrnNumber();
	    int machineId = productionSchedule.getMachineId();
	    String shift = productionSchedule.getShift();
	    int productCodeId = productionSchedule.getProductCodeId();
	    int machineTypeId = productionSchedule.getMachineTypeId();
	    
	    String tildeKey = "~";
	    String oeeReportCompositeKey = null;
	    
	    //FORMAT --> ProductionScheduleDate~MachineTypeID~MachineID~Shift~ProductCodeId
	    
	    oeeReportCompositeKey = mrnNumber+ tildeKey +productionScheduleDate + tildeKey +machineTypeId + tildeKey + machineId + tildeKey + shift + tildeKey + productCodeId;
	
		return oeeReportCompositeKey;
	}
	public int saveProductionPlanDetails(String oeeReportCompositeKey,ProductionSchedule productionSchedule,OEEProductionPlan productionPlanDetails,int createdBy) {
		
	   String productionScheduleDate= dateParserConfig.utcToLocaleDateParser(productionSchedule.getProductionScheduleDate());
	   
	   String query="INSERT INTO ecopack_oee_production_report(report_id,machine_type_id,machine_id,shift,oee_report_date,product_code_id1,prod_plan_first_hour,actual_prod_first_hour,time_loss_first_hour,prod_plan_second_hour,actual_prod_second_hour,time_loss_second_hour,prod_plan_third_hour,actual_prod_third_hour,time_loss_third_hour,prod_plan_fourth_hour,actual_prod_fourth_hour,time_loss_fourth_hour,prod_plan_fifth_hour,actual_prod_fifth_hour,time_loss_fifth_hour,prod_plan_sixth_hour,actual_prod_sixth_hour,time_loss_sixth_hour,prod_plan_seventh_hour,actual_prod_seventh_hour,time_loss_seventh_hour,prod_plan_eightth_hour,actual_prod_eightth_hour,time_loss_eightth_hour,total_production_plan,total_actual_production,total_time_loss,operator,maintenance_operator,prod_supervisor,prod_manager,created_by) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
		  
	   return jdbcTemplate.execute(query,new PreparedStatementCallback<Integer>(){  
		   
		 public Integer doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		           
			    ps.setString(1, oeeReportCompositeKey);
			    ps.setInt(2, productionSchedule.getMachineTypeId());
		        ps.setInt(3,productionSchedule.getMachineId());  
		        ps.setString(4,productionSchedule.getShift());
		        ps.setString(5,productionScheduleDate);
		        ps.setInt(6, productionSchedule.getProductCodeId());
		        ps.setInt(7, productionPlanDetails.getProdPlanFirstHour());
		        ps.setInt(8, productionPlanDetails.getActualProdFirstHour());
		        ps.setInt(9, productionPlanDetails.getTimeLossFirstHour());
		        ps.setInt(10, productionPlanDetails.getProdPlanSecondHour());
		        ps.setInt(11, productionPlanDetails.getActualProdSecondHour());
		        ps.setInt(12, productionPlanDetails.getTimeLossSecondHour());
		        ps.setInt(13, productionPlanDetails.getProdPlanThirdHour());
		        ps.setInt(14, productionPlanDetails.getActualProdThirdHour());
		        ps.setInt(15, productionPlanDetails.getTimeLossThirdHour());
		        ps.setInt(16, productionPlanDetails.getProdPlanFourthHour());
		        ps.setInt(17, productionPlanDetails.getActualProdFourthHour());
		        ps.setInt(18, productionPlanDetails.getTimeLossFourthHour());
		        ps.setInt(19, productionPlanDetails.getProdPlanFifthHour());
		        ps.setInt(20, productionPlanDetails.getActualProdFifthHour());
		        ps.setInt(21, productionPlanDetails.getTimeLossFifthHour());
		        ps.setInt(22, productionPlanDetails.getProdPlanSixthHour());
		        ps.setInt(23, productionPlanDetails.getActualProdSixthHour());
		        ps.setInt(24, productionPlanDetails.getTimeLossSixthHour());
		        ps.setInt(25, productionPlanDetails.getProdPlanSeventhHour());
		        ps.setInt(26, productionPlanDetails.getActualProdSeventhHour());
		        ps.setInt(27, productionPlanDetails.getTimeLossSeventhHour());
		        ps.setInt(28, productionPlanDetails.getProdPlanEightthHour());
		        ps.setInt(29, productionPlanDetails.getActualProdEightthHour());
		        ps.setInt(30, productionPlanDetails.getTimeLossEightthHour());
		        ps.setInt(31, productionPlanDetails.getTotalProductionPlan());
		        ps.setInt(32, productionPlanDetails.getTotalActualProduction());
		        ps.setInt(33, productionPlanDetails.getTotalTimeLoss());
		        ps.setString(34,productionPlanDetails.getOperator());
		        ps.setString(35,productionPlanDetails.getMaintenanceOperator());
		        ps.setString(36,productionPlanDetails.getProductionSupervisor());
		        ps.setString(37,productionPlanDetails.getProductionManager());
		        ps.setInt(38, createdBy);
		        
		        return ps.executeUpdate();
		    }  
	   });  
		 
	}

	public int saveProductionLossReport(String oeeReportCompositeKey,ProductionSchedule productionSchedule,List<OEELossReport> oeeLossReport,int createdBy) {
		
		int insertStatus = 0;
		int failedCount=0;
		int affectedCount=0;
		
		String query="INSERT INTO ecopack_oee_loss_report(report_id,machine_type_id,machine_id,shift,loss_report_date,product_code_id,machine_loss_id,firstHour,secondHour,thirdHour,fourthHour,fifthHour,sixthHour,seventhHour,eightthHour,totalHours,created_by) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
		String productionScheduleDate= dateParserConfig.utcToLocaleDateParser(productionSchedule.getProductionScheduleDate());
		
		int[] affectedRows = jdbcTemplate.batchUpdate(query,new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
            	 	ps.setString(1, oeeReportCompositeKey);
				    ps.setInt(2, productionSchedule.getMachineTypeId());
				    ps.setInt(3,productionSchedule.getMachineId());  
			        ps.setString(4,productionSchedule.getShift());
			        ps.setString(5,productionScheduleDate);
			        ps.setInt(6, productionSchedule.getProductCodeId());
            	 
			        ps.setInt(7, oeeLossReport.get(i).getLossTypes().getMachineLossId());  
 			        ps.setInt(8, oeeLossReport.get(i).getLossReport().getFirstHour());
 			        ps.setInt(9, oeeLossReport.get(i).getLossReport().getSecondHour());
 			        ps.setInt(10, oeeLossReport.get(i).getLossReport().getThirdHour());
 			        ps.setInt(11, oeeLossReport.get(i).getLossReport().getFourthHour());
 			        ps.setInt(12, oeeLossReport.get(i).getLossReport().getFifthHour());
 			        ps.setInt(13, oeeLossReport.get(i).getLossReport().getSixthHour());
 			        ps.setInt(14, oeeLossReport.get(i).getLossReport().getSeventhHour());
 			        ps.setInt(15, oeeLossReport.get(i).getLossReport().getEightthHour());
 			        ps.setInt(16, oeeLossReport.get(i).getLossReport().getTotalHours());
 			        ps.setInt(17, createdBy);
            }

            @Override
            public int getBatchSize() {
                return oeeLossReport.size();
            }
        });
		
		for(int affectedRow : affectedRows){
            if(affectedRow == Statement.EXECUTE_FAILED){
                failedCount++;
            }else{
            	affectedCount++;
            }
        }
		System.out.println("No of Inserted Rows : "+affectedCount);
		
		if(affectedCount == affectedRows.length) {
			insertStatus = 1;
		}else {
			System.out.println("No of failed Rows while Saving OEE Report : "+failedCount);
		}
		return insertStatus;
	}
	

	public int updateOEEReport(OEEReport oeeReport) throws ParseException{
		
		int status = 0;
		
		OEEProductionPlan productionPlanDetails = oeeReport.getOeeProductionPlan();
		List<OEELossReport> oeeLossReport = oeeReport.getOeeLossReportList();
		int lastUpdatedBy = oeeReport.getCurrentUserId();
		
		int oeeProductionReportUpdateStatus = updateProductionPlanDetails(productionPlanDetails,lastUpdatedBy);
        int oeeLossReportUpdateStatus =updateProductionLossReport(oeeLossReport,lastUpdatedBy);
		
        if(oeeProductionReportUpdateStatus == 1 && oeeLossReportUpdateStatus == 1) {
        	
        	status = 1;
        }
        
		return status;
	}
	
	public int updateProductionPlanDetails(OEEProductionPlan productionPlanDetails,int lastUpdatedBy) {
		
		String query ="UPDATE ecopack_oee_production_report SET "
				+ "prod_plan_first_hour=?, actual_prod_first_hour=?, time_loss_first_hour=?,"
				+ "prod_plan_second_hour=?, actual_prod_second_hour=?, time_loss_second_hour=?,"
				+ "prod_plan_third_hour=?, actual_prod_third_hour=?, time_loss_third_hour=?,"
				+ "prod_plan_fourth_hour=?, actual_prod_fourth_hour=?, time_loss_fourth_hour=?,"
				+ "prod_plan_fifth_hour=?, actual_prod_fifth_hour=?, time_loss_fifth_hour=?,"
				+ "prod_plan_sixth_hour=?, actual_prod_sixth_hour=?, time_loss_sixth_hour=?,"
				+ "prod_plan_seventh_hour=?, actual_prod_seventh_hour=?, time_loss_seventh_hour=?,"
				+ "prod_plan_eightth_hour=?, actual_prod_eightth_hour=?, time_loss_eightth_hour=?,"
				+ "total_production_plan=?, total_actual_production=?, total_time_loss=?, "
				+ " last_updated_by=? "
				+ " WHERE production_plan_id=? ";
				
		
		 return  jdbcTemplate.execute(query,new PreparedStatementCallback<Integer>(){  
			    @Override  
			    public Integer doInPreparedStatement(PreparedStatement ps)  
			            throws SQLException, DataAccessException {  
			        
			    	ps.setInt(1,productionPlanDetails.getProdPlanFirstHour());
			    	ps.setInt(2,productionPlanDetails.getActualProdFirstHour());
			    	ps.setInt(3,productionPlanDetails.getTimeLossFirstHour());
			    	ps.setInt(4,productionPlanDetails.getProdPlanSecondHour());
			    	ps.setInt(5,productionPlanDetails.getActualProdSecondHour());
			    	ps.setInt(6,productionPlanDetails.getTimeLossSecondHour());
			    	ps.setInt(7,productionPlanDetails.getProdPlanThirdHour());
			    	ps.setInt(8,productionPlanDetails.getActualProdThirdHour());
			    	ps.setInt(9,productionPlanDetails.getTimeLossThirdHour());
			    	ps.setInt(10,productionPlanDetails.getProdPlanFourthHour());
			    	ps.setInt(11,productionPlanDetails.getActualProdFourthHour());
			    	ps.setInt(12,productionPlanDetails.getTimeLossFourthHour());
			    	ps.setInt(13,productionPlanDetails.getProdPlanFifthHour());
			    	ps.setInt(14,productionPlanDetails.getActualProdFifthHour());
			    	ps.setInt(15,productionPlanDetails.getTimeLossFifthHour());
			    	ps.setInt(16,productionPlanDetails.getProdPlanSixthHour());
			    	ps.setInt(17,productionPlanDetails.getActualProdSixthHour());
			    	ps.setInt(18,productionPlanDetails.getTimeLossSixthHour());
			    	ps.setInt(19,productionPlanDetails.getProdPlanSeventhHour());
			    	ps.setInt(20,productionPlanDetails.getActualProdSeventhHour());
			    	ps.setInt(21,productionPlanDetails.getTimeLossSeventhHour());
			    	ps.setInt(22,productionPlanDetails.getProdPlanEightthHour());
			    	ps.setInt(23,productionPlanDetails.getActualProdEightthHour());
			    	ps.setInt(24,productionPlanDetails.getTimeLossEightthHour());
			    	ps.setInt(25,productionPlanDetails.getTotalProductionPlan());
			    	ps.setInt(26,productionPlanDetails.getTotalActualProduction());
			    	ps.setInt(27,productionPlanDetails.getTotalTimeLoss());
			    	ps.setInt(28, lastUpdatedBy);
			    	ps.setInt(29,productionPlanDetails.getProductionPlanId());
			    	
			        return ps.executeUpdate();  
			              
			    }  
			    });  
		
	}
	
	public int updateProductionLossReport(List<OEELossReport> oeeLossReport,int lastUpdatedBy) {
		
		int updateStatus =0;
		int failedCount=0;
		int affectedCount=0;
		
		String query ="UPDATE ecopack_oee_loss_report SET firstHour=?, secondHour=?, thirdHour=?, fourthHour=?, fifthHour=?, sixthHour=?, seventhHour=?, eightthHour=?,totalHours=?,last_updated_by=?"
				+ " WHERE oee_loss_report_id=?";
		
		int[] affectedRows = jdbcTemplate.batchUpdate(query,new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	                ps.setInt(1, oeeLossReport.get(i).getLossReport().getFirstHour());  
	                ps.setInt(2, oeeLossReport.get(i).getLossReport().getSecondHour());  
	                ps.setInt(3, oeeLossReport.get(i).getLossReport().getThirdHour());  
	                ps.setInt(4, oeeLossReport.get(i).getLossReport().getFourthHour());  
	                ps.setInt(5, oeeLossReport.get(i).getLossReport().getFifthHour());  
	                ps.setInt(6, oeeLossReport.get(i).getLossReport().getSixthHour());  
	                ps.setInt(7, oeeLossReport.get(i).getLossReport().getSeventhHour());  
	                ps.setInt(8, oeeLossReport.get(i).getLossReport().getEightthHour());  
	                ps.setInt(9, oeeLossReport.get(i).getLossReport().getTotalHours());  
	                ps.setInt(10, lastUpdatedBy);
	                ps.setInt(11, oeeLossReport.get(i).getLossReport().getLossReportId()); 
	            }

	            @Override
	            public int getBatchSize() {
	                return oeeLossReport.size();
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
			updateStatus = 1;
			System.out.println("No of updated Rows : "+affectedCount);
		}else {
			System.out.println("No of failed Rows while updating OEE Report : "+failedCount);
		}
		return updateStatus;
	}
	
	
	

}
