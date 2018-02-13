package com.ecopack.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.EmployeeMaster;
import com.ecopack.domains.UserRolesMaster;
import com.ecopack.services.EmployeeMasterService;

@RestController
public class EmployeeMasterController {

	
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@RequestMapping(value = "/getAllManagers", method = RequestMethod.POST)
	public  @ResponseBody  List<EmployeeMaster> getAllManagers(){

		return employeeMasterService.getAllManagers();
	}

	@RequestMapping(value = "/getAllUserRoles", method = RequestMethod.POST)
	public  @ResponseBody List<UserRolesMaster> getAllUserRoles(){
		
		return employeeMasterService.getAllUserRoles();
	}

	@RequestMapping(value = "/getAllEmployeeDetails", method = RequestMethod.POST)
	public  @ResponseBody  List<EmployeeMaster> getAllEmployeeDetails(){

		return employeeMasterService.getAllEmployeeDetails();
	}
	
	@RequestMapping(value = "/saveEmployeeDetails", method = RequestMethod.POST)
	public @ResponseBody int saveEmployeeDetails(@RequestBody EmployeeMaster employeeMaster) throws ParseException,Exception{
		 
		return employeeMasterService.saveEmployeeDetails(employeeMaster);
	}
	
	@RequestMapping(value = "/updateEmployeeDetails", method = RequestMethod.POST)
	public @ResponseBody int updateEmployeeDetails(@RequestBody EmployeeMaster employeeMaster) throws ParseException,Exception {

		return employeeMasterService.updateEmployeeDetails(employeeMaster);
	 }

	
}
