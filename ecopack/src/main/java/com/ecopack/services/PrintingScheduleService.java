package com.ecopack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopack.domains.MachineMaster;
import com.ecopack.domains.PrintingScheduleAll;
import com.ecopack.repository.PrintingScheduleRepository;

@Service
public class PrintingScheduleService {

	@Autowired
	PrintingScheduleRepository printingScheduleRepository;
	
	public List<MachineMaster> getAllPrintingMachine() {
		
		return printingScheduleRepository.getAllPrintingMachine();
	}
	
	public List<PrintingScheduleAll> getAllPrintingSchedule(){
		 
		return printingScheduleRepository.getAllPrintingSchedule();
	}
	
	public int updatePrintingSchedule(PrintingScheduleAll printingScheduleAll){
		 
		return printingScheduleRepository.updatePrintingSchedule(printingScheduleAll);
	}
	
	public int updatePrintingSchedulePriority(List<PrintingScheduleAll> printingScheduleAll){
		
		return printingScheduleRepository.updatePrintingSchedulePriority(printingScheduleAll);
	}
}
