package com.ecopack.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecopack.config.DateParserConfig;
import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.PrintingScheduleAll;

@Repository
public class PrintingScheduleRepository {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	DateParserConfig dateParserConfig;
	
	public List<MachineMaster> getAllPrintingMachine() {

		String query = "SELECT machine_id,machine_no from ecopack_machine_master WHERE machine_no IN('P-399','P-400')";

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
	
	public List<PrintingScheduleAll> getAllPrintingSchedule(){
		String flow_status_code="Approved";
		String flow_status_code2="Partially Completed";
		
		String is_printing_required ="Yes";
		
		
		String query="SELECT "
				+ "orderDetails.order_number,"
				+ "orderDetails.ordered_date,"
				+ "lineItems.item_id,"
				+ "lineItems.item_desc,"
				+ "lineItems.ordered_qty,"
				+ "lineItems.printing_required,"
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
				+ "lineItems.header_id,"
				+ "lineItems.line_id,"
				+ "lineItems.pcs_per_sheet,"
				+ "printing.no_of_sheets_to_print,"
				+ "printing.percentage,"
				+ "printing.extra_no_of_sheets_to_print,"
				+ "printing.sheets_in_kg,"
				+ "printing.machine_id,"
				+ "printing.paper_lot_num,"
				+ "printing.operator,"
				+ "printing.no_of_sheets_printed,"
				+ "printing.remaining_sheets_to_print,"
				+ "printing.actual_printing_date,"
				+ "printing.schedule_date,"
				+ "printing.flow_status_code"
				
				+ " FROM ecopack_order_details orderDetails,"
				+ " ecopack_lines_items lineItems,"
				+ " ecopack_item_master itemMaster,"
				+ " ecopack_printing_schedule printing"
				+ " WHERE "
				+ " orderDetails.header_id = lineItems.header_id AND "
				+ " lineItems.header_id = printing.header_id AND "
				+ " lineItems.item_id = itemMaster.item_id AND "
				+ " lineItems.line_id  = printing.line_id AND "
				+ " lineItems.printing_required='"+is_printing_required+"' AND"
				+ " printing.flow_status_code IN ('"+flow_status_code+"','"+flow_status_code2+"') ORDER BY priority_id";
		
		List<PrintingScheduleAll> orderLinePrinting= jdbcTemplate.query(query, new RowMapper<PrintingScheduleAll>() {

			@Override
			public PrintingScheduleAll mapRow(ResultSet rs, int rownumber) throws SQLException {

				PrintingScheduleAll printingScheduleAll=new PrintingScheduleAll();

				printingScheduleAll.setOrderNumber(rs.getInt(1));
				printingScheduleAll.setOrderedDate(rs.getString(2));

				printingScheduleAll.setItemId(rs.getInt(3));
				printingScheduleAll.setItemDesc(rs.getString(4));
				printingScheduleAll.setOrderedQty(rs.getInt(5));
				printingScheduleAll.setPrintingRequired(rs.getString(6));
				printingScheduleAll.setMfgRequired(rs.getString(7));

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
				printingScheduleAll.setHeaderId(rs.getInt(18));
				printingScheduleAll.setLineId(rs.getInt(19));
				printingScheduleAll.setPcsPerSheet(rs.getInt(20));
				printingScheduleAll.setNoOfSheetsToPrint(rs.getDouble(21));
				printingScheduleAll.setPercentage(rs.getString(22));
				printingScheduleAll.setExtraNoOfSheetsToPrint(rs.getDouble(23));
				printingScheduleAll.setSheetsInKg(rs.getDouble(24));
				printingScheduleAll.setMachineId(rs.getInt(25));
				printingScheduleAll.setPaperLotNum(rs.getString(26));
				printingScheduleAll.setOperator(rs.getString(27));
				printingScheduleAll.setNoOfSheetsPrinted(rs.getInt(28));
				printingScheduleAll.setRemainingSheetsToPrint(rs.getInt(29));
				printingScheduleAll.setActualPrintingDate(rs.getString(30));
				printingScheduleAll.setScheduleDate(rs.getString(31));
				printingScheduleAll.setFlowStatusCode(rs.getString(32));
									
				       return  printingScheduleAll;            
				//return new OrderLinePrinting(orderDetails, lineItemsDetail, itemMasterDetails, printingScheduleAll);
			}
		});
		
		return orderLinePrinting;
	}


	public int updatePrintingSchedule(PrintingScheduleAll printingScheduleAll){
		
		String flowStatusCode = null;
		String currentFlowStatus = null;
		double calculatedRemainingSheets=0;
		
		String actualPrintingDate = dateParserConfig.utcToLocaleDateParser(printingScheduleAll.getActualPrintingDate());
		String scheduleDate = dateParserConfig.utcToLocaleDateParser(printingScheduleAll.getScheduleDate());
		
		//double noOfSheetsToPrint = printingScheduleAll.getNoOfSheetsToPrint();
		double noOfSheetsToPrint = printingScheduleAll.getExtraNoOfSheetsToPrint();
		
		double remainingSheetsToPrint = printingScheduleAll.getRemainingSheetsToPrint();
		
		int no_of_sheets_printed= printingScheduleAll.getNoOfSheetsPrinted();
		  
	
		// Remaining Sheets
		if(remainingSheetsToPrint == 0){
			// if remaining sheets are 0 then change formula
			calculatedRemainingSheets = noOfSheetsToPrint - no_of_sheets_printed;
		}
		else if(remainingSheetsToPrint > 0){
			
			calculatedRemainingSheets = remainingSheetsToPrint - no_of_sheets_printed;
		}
		

		if(calculatedRemainingSheets == 0){
			
			flowStatusCode ="Completed";
		}
		else if(calculatedRemainingSheets > 0){
			
			currentFlowStatus ="Done";
			flowStatusCode ="Partially Completed";
		}
			
		
		String query = "update ecopack_printing_schedule SET "
				+" machine_id='"+printingScheduleAll.getMachineId()
				+"',paper_lot_num='"+printingScheduleAll.getPaperLotNum()
				+"',operator='"+ printingScheduleAll.getOperator()
				+"',no_of_sheets_to_print="+printingScheduleAll.getNoOfSheetsToPrint()
				+",percentage='"+printingScheduleAll.getPercentage()
				+"',extra_no_of_sheets_to_print="+printingScheduleAll.getExtraNoOfSheetsToPrint()
				+",sheets_in_kg="+printingScheduleAll.getSheetsInKg()
				+",no_of_sheets_printed="+printingScheduleAll.getNoOfSheetsPrinted()
				+",remaining_sheets_to_print="+calculatedRemainingSheets
				+",actual_printing_date='"+actualPrintingDate
				+"',schedule_date='"+scheduleDate
				+"',flow_status_code='"+ currentFlowStatus +"' WHERE printing_line_id="+printingScheduleAll.getPrintingLineId();
		
		
		int status = jdbcTemplate.update(query);

		if(calculatedRemainingSheets > 0) {
			   String query2 = "insert into ecopack_printing_schedule(header_id,line_id,no_of_sheets_to_print,percentage,extra_no_of_sheets_to_print,sheets_in_kg,remaining_sheets_to_print,flow_status_code) values("
						+printingScheduleAll.getHeaderId()+","+printingScheduleAll.getLineId()+","+printingScheduleAll.getNoOfSheetsToPrint()+",'"+printingScheduleAll.getPercentage()+"',"+printingScheduleAll.getExtraNoOfSheetsToPrint()+","+printingScheduleAll.getSheetsInKg()+","+ calculatedRemainingSheets +",'"+flowStatusCode+"')";
			   jdbcTemplate.update(query2);
		   }
		else if(calculatedRemainingSheets == 0){
			
			 String query3="UPDATE ecopack_printing_schedule SET flow_status_code='"+flowStatusCode+"',"
		   		      + " priority_id =NULL WHERE header_id="+printingScheduleAll.getHeaderId()+" AND printing_line_id="+printingScheduleAll.getPrintingLineId();
		      jdbcTemplate.update(query3);
		}
		
		return status;
	
	}
	
	  public int updatePrintingSchedulePriority(List<PrintingScheduleAll> printingScheduleAll){
		 
		int status = 0;
		for(int i=0;i<printingScheduleAll.size();i++){
			
			int printingLineId=printingScheduleAll.get(i).getPrintingLineId();
			int priorityId=printingScheduleAll.get(i).getPriorityId();
			
			System.out.println(printingLineId +":"+ priorityId);
			
			
			String query="UPDATE ecopack_printing_schedule SET priority_id="+priorityId+" WHERE printing_line_id="+printingLineId;
			status=jdbcTemplate.update(query);
		}
		
		return status;
	}
}
