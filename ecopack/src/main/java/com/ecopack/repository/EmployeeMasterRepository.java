package com.ecopack.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecopack.config.DateParserConfig;
import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.UserRolesMaster;

@Repository
public class EmployeeMasterRepository {

	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	DateParserConfig dateParserConfig;
	
	String Dob;
	String joinDate;
	String resignDate;	
	
	
	public List<EmployeeMaster> getAllManagers(){

		String role ="MANAGER";
		int active= 1;
		String queryString="SELECT emp.emp_id as manager_id,emp.emp_fullname " + 
				" FROM ecopack_employee_master emp,ecopack_user_roles roles " + 
				" WHERE " + 
				" emp.role_id = roles.role_id AND " + 
				" roles.role =? AND "+
				" emp.is_active=? ";
		
		return jdbcTemplate.query(queryString,  new Object[] { role,active } ,new RowMapper<EmployeeMaster>() {

							@Override
							public EmployeeMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

								EmployeeMaster managers = new EmployeeMaster();
								managers.setManagerId(rs.getInt(1));
								managers.setEmpFullname(rs.getString(2));
								
								return managers;

							}
						});
	}
	
	public  List<UserRolesMaster> getAllUserRoles(){
		
		 String queryString="Select role_id,role from ecopack_user_roles";
			
			return jdbcTemplate.query(queryString, new RowMapper<UserRolesMaster>() {

								@Override
								public UserRolesMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

									UserRolesMaster userRolesMaster = new UserRolesMaster();
									
									userRolesMaster.setRoleId(rs.getInt(1));
									userRolesMaster.setRole(rs.getString(2));
									
									return userRolesMaster;
								}
							});
		
	}
	
	public List<EmployeeMaster> getAllEmployeeDetails(){

		String queryString="select emp_id,emp_number,emp_fullname,emp_address,gender,dob,mobile_number,email_id,join_date,role_id,manager_id,is_active,resign_date"
				+ " FROM ecopack_employee_master";
		
		return jdbcTemplate.query(queryString, new RowMapper<EmployeeMaster>() {

							@Override
							public EmployeeMaster mapRow(ResultSet rs, int rownumber) throws SQLException {

								EmployeeMaster employeeMaster = new EmployeeMaster();
								employeeMaster.setEmpId(rs.getInt(1));
								employeeMaster.setEmpNumber(rs.getString(2));
								employeeMaster.setEmpFullname(rs.getString(3));
								employeeMaster.setEmpAddress(rs.getString(4));
								employeeMaster.setGender(rs.getString(5));
								employeeMaster.setDob(rs.getString(6));
								employeeMaster.setMobileNumber(rs.getString(7));
								employeeMaster.setEmailId(rs.getString(8));
								employeeMaster.setJoinDate(rs.getString(9));
								employeeMaster.setRoleId(rs.getInt(10));
								employeeMaster.setManagerId(rs.getInt(11));
								employeeMaster.setIsActive(rs.getInt(12));
								employeeMaster.setResignDate(rs.getString(13));
								
								return employeeMaster;

							}
						});
	}
	
	
	public int saveEmployeeDetails(EmployeeMaster employeeMaster) throws ParseException,Exception{
		 
		 String stringUtcDob =employeeMaster.getDob();
		 String stringUtcJoinDate =employeeMaster.getJoinDate();
		 String stringUtcResignDate =employeeMaster.getResignDate();
				 
       int isActive =employeeMaster.getIsActive();
       
       Dob = dateParserConfig.utcToLocaleDateParser(stringUtcDob);
       joinDate =dateParserConfig.utcToLocaleDateParser(stringUtcJoinDate);
       //resignDate =dateParserConfig.utcToLocaleDateParser(stringUtcResignDate);
		 
		 if(isActive == 1){
				
			 resignDate =null;
		 }
		 else if(isActive == 0) {
			 
			 resignDate =dateParserConfig.utcToLocaleDateParser(stringUtcResignDate);
		 }
		 
				 
	    String query="insert into ecopack_employee_master(emp_number,emp_fullname,emp_address,"
	    		+ "gender,dob,mobile_number,email_id,join_date,role_id,"
	    		+ "manager_id,is_active,resign_date,created_by) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";  
		
		return  jdbcTemplate.execute(query,new PreparedStatementCallback<Integer>(){  
		    @Override  
		    public Integer doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		        
		    	ps.setString(1, employeeMaster.getEmpNumber());
		    	ps.setString(2,employeeMaster.getEmpFullname());
		    	ps.setString(3,employeeMaster.getEmpAddress());
		    	ps.setString(4,employeeMaster.getGender());
		    	ps.setString(5,Dob);
		    	ps.setString(6,employeeMaster.getMobileNumber());
		    	ps.setString(7,employeeMaster.getEmailId());
		    	ps.setString(8,joinDate);
		    	ps.setInt(9,employeeMaster.getRoleId());
		    	ps.setInt(10,employeeMaster.getManagerId());
		    	ps.setInt(11, employeeMaster.getIsActive());
		    	ps.setString(12,resignDate);
		    	ps.setInt(13, employeeMaster.getCreatedBy());
		    	
		        return ps.executeUpdate(); 
		              
		    }  
		    });  

	}
	
	public int updateEmployeeDetails(EmployeeMaster employeeMaster) throws ParseException,Exception {

		
		 String stringUtcDob =employeeMaster.getDob();
		 String stringUtcJoinDate =employeeMaster.getJoinDate();
		 String stringUtcResignDate =employeeMaster.getResignDate();
				 
		 
       int isActive =employeeMaster.getIsActive();
       
       
       Dob = dateParserConfig.utcToLocaleDateParser(stringUtcDob);
       joinDate =dateParserConfig.utcToLocaleDateParser(stringUtcJoinDate);
      
		 if(isActive == 1){
				
			 resignDate =null;
		 }
		 else if(isActive == 0) {
			 
			 resignDate =dateParserConfig.utcToLocaleDateParser(stringUtcResignDate);
		 }
		 
		
		 String queryString="UPDATE ecopack_employee_master SET emp_fullname =?, emp_address =?, gender =? ,dob =?, mobile_number =?, email_id =?, join_date =?, role_id =?, manager_id =?, is_active=?,"
		 		+ " resign_date=?, last_updated_by=?  WHERE emp_id =?";  
			
		 return  jdbcTemplate.execute(queryString,new PreparedStatementCallback<Integer>(){  
			    @Override  
			    public Integer doInPreparedStatement(PreparedStatement ps)  
			            throws SQLException, DataAccessException {  
			        
			    	ps.setString(1,employeeMaster.getEmpFullname());
			    	ps.setString(2,employeeMaster.getEmpAddress());
			    	ps.setString(3,employeeMaster.getGender());
			    	ps.setString(4,Dob);
			    	ps.setString(5,employeeMaster.getMobileNumber());
			    	ps.setString(6,employeeMaster.getEmailId());
			    	ps.setString(7,joinDate);
			    	ps.setInt(8,employeeMaster.getRoleId());
			    	ps.setInt(9,employeeMaster.getManagerId());
			    	ps.setInt(10,employeeMaster.getIsActive());
			    	ps.setString(11,resignDate);
			    	ps.setInt(12, employeeMaster.getLastUpdatedBy());
			    	ps.setInt(13, employeeMaster.getEmpId());
			    	
			        return ps.executeUpdate();  
			              
			    }  
			    });  
	   
	 }

	
}
