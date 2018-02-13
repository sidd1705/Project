package com.ecopack.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ecopack.domains.PrintingScheduleAll;

@RestController
@RequestMapping("/api/admin")
public class PrintingReportController {
	
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
   @RequestMapping(value ="/getDashboard", method = RequestMethod.GET)
   public  List<PrintingScheduleAll>selectAllLineItems(){
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
				+ "itemMaster.item_size,"
				+ "itemMaster.design_no,"
				+ "itemMaster.design_name,"
				+ "itemMaster.paper_color,"
				+ "itemMaster.paper_gsm,"
				+ "itemMaster.print_type,"
				+ "itemMaster.color1,"
				+ "itemMaster.color2,"
				+ "printing.printing_line_id,"		
				+ "printing.header_id,"
				+ "printing.line_id,"
				+ "printing.pcs_per_sheet,"
				+ "printing.no_of_sheets_to_print,"
				+ "printing.extra_no_of_sheets_to_print,"
				+ "printing.sheets_in_kg,"
				+ "printing.machine_id,"
				+ "printing.paper_lot_num,"
				+ "printing.operator,"
				+ "printing.no_of_sheets_printed,"
				+ "printing.remaining_sheets_to_print,"
				+ "printing.actual_printing_date,"
				+ "printing.schedule_date"
				+ " FROM ecopack_order_details orderDetails,"
				+ " ecopack_lines_items lineItems,"
				+ " ecopack_item_master itemMaster,"
				+ " ecopack_printing_schedule printing"
				+ " WHERE "
				+ " orderDetails.header_id = lineItems.header_id AND "
				+ " lineItems.header_id = printing.header_id AND "
				+ " lineItems.item_id = itemMaster.item_id AND "
				+ " lineItems.line_id  = printing.line_id AND"
				+ " lineItems.printing_required='"+is_printing_required+"'"
						+ "ORDER BY ordered_date";
	   
	
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
				                    printingScheduleAll.setExtraNoOfSheetsToPrint(rs.getDouble(22));
				                    printingScheduleAll.setSheetsInKg(rs.getDouble(23));
				                    printingScheduleAll.setMachineId(rs.getInt(24));
				                    printingScheduleAll.setPaperLotNum(rs.getString(25));
				                    printingScheduleAll.setOperator(rs.getString(26));
				                    printingScheduleAll.setNoOfSheetsPrinted(rs.getInt(27));
				                    printingScheduleAll.setRemainingSheetsToPrint(rs.getInt(28));
				                    printingScheduleAll.setActualPrintingDate(rs.getString(29));
				                    printingScheduleAll.setScheduleDate(rs.getString(30));
				                
				                    
				return printingScheduleAll;
			}
		});
	 
	
	return orderLinePrinting;
		
	}



}
