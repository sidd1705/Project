package com.ecopack.controllers;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.UserMaster;

@RestController
public class UserRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value = "/getLoggedInUserDetails" ,method = RequestMethod.GET)
	public UserMaster getLoggedInUserDetails(Principal principal) {
		
		String userName = principal.getName();
		
		String queryString = "SELECT user_id,user_name,roles.role FROM ecopack_employee_master emp,ecopack_user_master user,ecopack_user_roles roles WHERE  emp.emp_id = user.emp_id AND emp.role_id=roles.role_id AND user_name=?";

		return (UserMaster) jdbcTemplate.queryForObject(queryString,new Object[] { userName }, new RowMapper<UserMaster>() {

			@Override
			public UserMaster mapRow(ResultSet rs, int arg1) throws SQLException {
				UserMaster user = new UserMaster();

				user.setUserId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setRole(rs.getString(3));

				return user;
			}

		});
	}
	
	
}


