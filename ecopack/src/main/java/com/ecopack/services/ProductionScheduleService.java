package com.ecopack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.domains.ProductCodeMaster;
import com.ecopack.domains.ProductionSchedule;
import com.ecopack.repository.ProductionScheduleRepository;


@Service
public class ProductionScheduleService {

	
	@Autowired
	ProductionScheduleRepository productionScheduleRepository;
	
	public List<PrintingScheduleAll> getProductionSchedule(){
		
		return productionScheduleRepository.getProductionSchedule();
	}
	
	public List<ProductCodeMaster> getAllProductCode() {
		
		return productionScheduleRepository.getAllProductCode();
	}
	
	public List<MachineMaster> getAllProductionMachine() {
		
		return productionScheduleRepository.getAllProductionMachine();
	}
	
	public int checkMrnNumberIsExists(int mrnNumber) {
	
		return productionScheduleRepository.checkMrnNumberIsExists(mrnNumber);
	}
	public int saveProductionSchedule(ProductionSchedule productionSchedule){
		
		return productionScheduleRepository.saveProductionSchedule(productionSchedule);
	}
}
