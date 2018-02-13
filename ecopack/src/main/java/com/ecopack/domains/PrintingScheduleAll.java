package com.ecopack.domains;

public class PrintingScheduleAll extends ProductionSchedule{
	
	public int printingLineId;
	//public int headerId;
	//public int lineId;
	//public int lineNumber;
	//public int orgId;
	public int noOfSheetsPrinted;
	public double remainingSheetsToPrint;
	public String paperLotNum;
	public String actualPrintingDate;
	public String scheduleDate;
	public String operator;
	//public int pcsPerSheet;
	public double noOfSheetsToPrint;
	public String percentage;
	public double extraNoOfSheetsToPrint;
	public double sheetsInKg;
	//public String machineId;
	//public String flowStatusCode;
	public int priorityId;	
	public String dispatchedDate;
	
	
	
	public int getPrintingLineId() {
		return printingLineId;
	}
	public void setPrintingLineId(int printingLineId) {
		this.printingLineId = printingLineId;
	}
	public int getNoOfSheetsPrinted() {
		return noOfSheetsPrinted;
	}
	public void setNoOfSheetsPrinted(int noOfSheetsPrinted) {
		this.noOfSheetsPrinted = noOfSheetsPrinted;
	}
	public double getRemainingSheetsToPrint() {
		return remainingSheetsToPrint;
	}
	public void setRemainingSheetsToPrint(double remainingSheetsToPrint) {
		this.remainingSheetsToPrint = remainingSheetsToPrint;
	}
	public String getPaperLotNum() {
		return paperLotNum;
	}
	public void setPaperLotNum(String paperLotNum) {
		this.paperLotNum = paperLotNum;
	}
	public String getActualPrintingDate() {
		return actualPrintingDate;
	}
	public void setActualPrintingDate(String actualPrintingDate) {
		this.actualPrintingDate = actualPrintingDate;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public double getNoOfSheetsToPrint() {
		return noOfSheetsToPrint;
	}
	public void setNoOfSheetsToPrint(double noOfSheetsToPrint) {
		this.noOfSheetsToPrint = noOfSheetsToPrint;
	}
	public double getExtraNoOfSheetsToPrint() {
		return extraNoOfSheetsToPrint;
	}
	public void setExtraNoOfSheetsToPrint(double extraNoOfSheetsToPrint) {
		this.extraNoOfSheetsToPrint = extraNoOfSheetsToPrint;
	}
	public double getSheetsInKg() {
		return sheetsInKg;
	}
	public void setSheetsInKg(double sheetsInKg) {
		this.sheetsInKg = sheetsInKg;
	}
//	public String getMachineId() {
//		return machineId;
//	}
//	public void setMachineId(String machineId) {
//		this.machineId = machineId;
//	}
	public int getPriorityId() {
		return priorityId;
	}
	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}
	public String getDispatchedDate() {
		return dispatchedDate;
	}
	public void setDispatchedDate(String dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	

}
