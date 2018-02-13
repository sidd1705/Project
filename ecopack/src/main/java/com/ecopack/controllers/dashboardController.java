package com.ecopack.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.LineItemsDetail;

@RestController
@RequestMapping("/api/admin")
public class dashboardController {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
   @RequestMapping("/getDashboard")
   public List<LineItemsDetail> selectAllLineItems(){
	
		return jdbcTemplate
				.query("select line_id,header_id,item_id,item_desc,order_value,ordered_qty,printing_required,mfg_required,in_stock from ecopack_lines_items",
						new RowMapper<LineItemsDetail>() {

							@Override
							public LineItemsDetail mapRow(ResultSet rs, int rownumber) throws SQLException {

								LineItemsDetail lineItems = new LineItemsDetail();
								lineItems.setLineId(rs.getInt(1));
								lineItems.setHeaderId(rs.getInt(2));
								lineItems.setItemId(rs.getInt(3));
								lineItems.setItemDesc(rs.getString(4));
								lineItems.setOrderValue(rs.getDouble(5));
								lineItems.setOrderedQty(rs.getInt(6));
								lineItems.setPrintingRequired(rs.getString(7));
								lineItems.setMfgRequired(rs.getString(8));
								lineItems.setInStock(rs.getString(9));
								return lineItems;

							}
						});
		
	}



}
