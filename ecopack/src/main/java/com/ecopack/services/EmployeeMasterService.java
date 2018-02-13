package com.ecopack.services;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.UserRolesMaster;
import com.ecopack.repository.EmployeeMasterRepository;

@Service
public class EmployeeMasterService {

	
	@Autowired
	EmployeeMasterRepository  employeeMasterRepository;
	
	public List<EmployeeMaster> getAllManagers(){
		
		return employeeMasterRepository.getAllManagers();
	}
	
	public  List<UserRolesMaster> getAllUserRoles(){
		
		return employeeMasterRepository.getAllUserRoles();
	}
	
	public List<EmployeeMaster> getAllEmployeeDetails(){
		
		return employeeMasterRepository.getAllEmployeeDetails();
	}
	
	public int saveEmployeeDetails(EmployeeMaster employeeMaster) throws ParseException,Exception{
		
		return employeeMasterRepository.saveEmployeeDetails(employeeMaster);
	}
	
	public int updateEmployeeDetails(EmployeeMaster employeeMaster) throws ParseException,Exception {

		return employeeMasterRepository.updateEmployeeDetails(employeeMaster);
	}
	
	
}
