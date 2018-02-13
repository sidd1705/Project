package com.ecopack.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.services.PrintingScheduleService;

@RestController
public class PrintingScheduleController {

	
	@Autowired
	PrintingScheduleService printingScheduleService;

	@RequestMapping(value = "/loadPrintingMachines", method = RequestMethod.GET)
	public List<MachineMaster> listAllMachine() {

		return printingScheduleService.getAllPrintingMachine();
	}
	

	@RequestMapping(value = "/getAllPrintingSchedule", method = RequestMethod.GET)
	public List<PrintingScheduleAll> getAllPrintingSchedule() {

		return printingScheduleService.getAllPrintingSchedule();
	}

	@RequestMapping(value = "/updatePrintingScheduleDetails",method=RequestMethod.POST)
	public  @ResponseBody int updatePrintingSchedule(@RequestBody PrintingScheduleAll printingScheduleAll){
		
		return printingScheduleService.updatePrintingSchedule(printingScheduleAll);
	}
	
	
	@RequestMapping(value = "/updatePrintingSchedulePriority",method=RequestMethod.POST)
	public  @ResponseBody int updatePrintingSchedulePriority(@RequestBody List<PrintingScheduleAll> printingScheduleAll){
		
		return printingScheduleService.updatePrintingSchedulePriority(printingScheduleAll);
	}

}
