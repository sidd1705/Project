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
public class OtpDashboardController {
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
   @RequestMapping(value ="/getOtpDashboard", method = RequestMethod.GET)
   
   public List<PrintingScheduleAll> getProductionSchedule(){

		
		String is_mfg_required ="Yes";
		
		
		String query="SELECT"
				+ " orderDetails.header_id,"
				+ "orderDetails.order_number,"
				+ "orderDetails.ordered_date,"
				+ "orderDetails.due_date,"
				+ "lineItems.item_id,"
				+ "lineItems.mfg_required,"
		
				+ "itemMaster.item_name,"
				
				+ "printing.printing_line_id,"
				+ "printing.actual_printing_date,"
                + "printing.ready_date,"
				+ "printing.dispatch_date"
				
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
				//+ " printing.flow_status_code IN ('"+flow_status_code+"','"+flow_status_code2+"') AND"
				+ " production.printing_line_id IS NULL;";
		
		List<PrintingScheduleAll> orderLinePrinting= jdbcTemplate.query(query, new RowMapper<PrintingScheduleAll>() {

			@Override
			public PrintingScheduleAll mapRow(ResultSet rs, int rownumber) throws SQLException {
				PrintingScheduleAll printingScheduleAll=new PrintingScheduleAll();

				printingScheduleAll.setHeaderId(rs.getInt(1));
				printingScheduleAll.setOrderNumber(rs.getInt(2));
				printingScheduleAll.setOrderedDate(rs.getString(3));
				printingScheduleAll.setDueDate(rs.getString(4));
				printingScheduleAll.setItemId(rs.getInt(5));
				printingScheduleAll.setMfgRequired(rs.getString(6));

				
				printingScheduleAll.setItemName(rs.getString(7));
								
			    printingScheduleAll.setPrintingLineId(rs.getInt(8));
				printingScheduleAll.setActualPrintingDate(rs.getString(9));
				printingScheduleAll.setReadyDate(rs.getString(10));
				printingScheduleAll.setDispatchDate(rs.getString(11));
			
				return printingScheduleAll;
			}
		});
		
		return orderLinePrinting;
	}


}
