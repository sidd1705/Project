package com.ecopack.controllers;


import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.LossTypesMaster;
import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.MachineTypesMaster;
import com.ecopack.domains.OEEReport;
import com.ecopack.domains.ProductionSchedule;
import com.ecopack.services.OEEReportService;

@Transactional(rollbackFor = Exception.class)
@RestController
public class OEEReportController {

	@Autowired
	OEEReportService oeeReportService;
	
	@RequestMapping(value = "/loadMachineTypes", method = RequestMethod.GET)
	public List<MachineTypesMaster> listAllMachineTypes() {

		return oeeReportService.listAllMachineTypes();
	}
	
	@RequestMapping(value = "/loadMachines", method = RequestMethod.GET)
	public List<MachineMaster> listAllMachines() {

		return oeeReportService.getAllMachines();
	}

	@RequestMapping(value = "/loadLossTypesForMachine", method = RequestMethod.GET)
	public @ResponseBody List<LossTypesMaster> loadLossReport(@RequestParam Integer machineTypeId) {
		
		return oeeReportService.loadLossReport(machineTypeId);
	}

	@RequestMapping(value = "/getScheduledProduction", method = RequestMethod.POST)
	public  @ResponseBody List<OEEReport> getScheduledProduction(@RequestBody ProductionSchedule prodSchedule){
	
		return oeeReportService.getScheduledProduction(prodSchedule);
	}
	
	@RequestMapping(value = "/saveOEEReport", method = RequestMethod.POST)
	public  @ResponseBody int saveOEEReport(@RequestBody OEEReport oeeReport) throws ParseException,Exception {
		
		return oeeReportService.saveOEEReport(oeeReport);
	}
	
	@RequestMapping(value = "/updateOEEReport", method = RequestMethod.POST)
	public  @ResponseBody int updateOEEReport(@RequestBody OEEReport oeeReport) throws ParseException{
	
		return oeeReportService.updateOEEReport(oeeReport);
	}

}
