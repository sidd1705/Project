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

import com.ecopack.domains.OrderDetails;;

@RestController
@RequestMapping("/api/admin")
public class OrderDashboardController {
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
   @RequestMapping(value ="/getOrderDashboard", method = RequestMethod.GET)
   public List<OrderDetails> getAllOrderDetails(){
	
		return jdbcTemplate
				.query("select order_number,"
						+ "customer_type,"
						+ "customer_name,"
						+ "sales_executive_id,"
						+ "ordered_date,due_date,"
						+ "currency,"
						+ "total_order_value,"
						+ "flow_status_code,"
						+ "creation_date,"
						+ "last_update_date from ecopack_order_details "
						+ "as ud LEFT JOIN ecopack_customer_master as cm on"
						+ " ud.customer_id=cm.customer_id",
						new RowMapper<OrderDetails>() {

							@Override
							public OrderDetails mapRow(ResultSet rs, int rownumber) throws SQLException {

								OrderDetails orderDetails = new OrderDetails();
								orderDetails.setOrderNumber(rs.getInt(1));
								orderDetails.setCustomerType(rs.getString(2));
								orderDetails.setCustomerName(rs.getString(3));
								orderDetails.setSalesExecutiveId(rs.getString(4));
								orderDetails.setOrderedDate(rs.getString(5));
								orderDetails.setDueDate(rs.getString(6));
								orderDetails.setCurrency(rs.getString(7));
								orderDetails.setTotalOrderValue(rs.getDouble(8));
								orderDetails.setFlowStatusCode(rs.getString(9));
								orderDetails.setCreationDate(rs.getString(10));
								orderDetails.setLastUpdatedDate(rs.getString(11));
								
								return orderDetails;

							}
						});
		
	}



}
