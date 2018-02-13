package com.ecopack.services;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopack.domains.LossTypesMaster;
import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.MachineTypesMaster;
import com.ecopack.domains.OEEReport;
import com.ecopack.domains.ProductionSchedule;
import com.ecopack.repository.OEEReportRepository;

@Service
public class OEEReportService {

	@Autowired
	OEEReportRepository oeeReportRepository;

	public List<MachineTypesMaster> listAllMachineTypes() {

		return oeeReportRepository.getAllMachineTypes();
	}
	
	public List<MachineMaster> getAllMachines() {
		
		return oeeReportRepository.getAllMachines();
	}
	
	public List<LossTypesMaster> loadLossReport(int machineTypeId) {
		
		return oeeReportRepository.loadLossReport(machineTypeId);
	}
	
	public  List<OEEReport> getScheduledProduction(ProductionSchedule prodSchedule){
		
		return oeeReportRepository.getScheduledProduction(prodSchedule);
	}
	
	public int saveOEEReport(OEEReport oeeReport) throws ParseException,Exception {
		
		return oeeReportRepository.saveOEEReport(oeeReport);
	}
	
	public int updateOEEReport(OEEReport oeeReport) throws ParseException{
		
		return oeeReportRepository.updateOEEReport(oeeReport);
	}

}
