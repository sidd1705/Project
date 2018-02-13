package com.ecopack.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.domains.ProductCodeMaster;
import com.ecopack.domains.ProductionSchedule;
import com.ecopack.services.ProductionScheduleService;

@RestController
public class ProductionScheduleController {
	

	@Autowired
	ProductionScheduleService  productionScheduleService;
	
	@RequestMapping(value = "/loadProductionMachines", method = RequestMethod.GET)
	public List<MachineMaster> listAllMachine() {

		return productionScheduleService.getAllProductionMachine();
	}
	
	@RequestMapping(value = "/loadProductCodes", method = RequestMethod.GET)
	public List<ProductCodeMaster> getAllProductCode() {
		
		return productionScheduleService.getAllProductCode();
	}
	
	@RequestMapping(value = "/getProductionSchedule",method=RequestMethod.GET)
	public List<PrintingScheduleAll> getProductionSchedule() {
		
		return productionScheduleService.getProductionSchedule();
	}
	@RequestMapping(value = "/checkMrnNumberIsExists", method = RequestMethod.GET)
	public int checkMrnNumberIsExists(@RequestParam int mrnNumber) {
		
		return productionScheduleService.checkMrnNumberIsExists(mrnNumber);
	}
	
	@RequestMapping(value = "/saveProductionScheduleDetails",method=RequestMethod.POST)
	public  @ResponseBody int saveProductionSchedule(@RequestBody ProductionSchedule productionSchedule){
		
		return productionScheduleService.saveProductionSchedule(productionSchedule);
	}

	

}
