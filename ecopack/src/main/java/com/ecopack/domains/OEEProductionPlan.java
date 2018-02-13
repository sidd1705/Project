package com.ecopack.domains;



public class OEEProductionPlan {

	private int productionPlanId;
	
	private int machineTypeId;
	private int machineId;
	private int shift;
	private String oeeReportDate;
	private int productId1;
	private int productId2;
	
	private int prodPlanFirstHour;
	private int actualProdFirstHour;
	private int timeLossFirstHour;

	private int prodPlanSecondHour;
	private int actualProdSecondHour;
	private int timeLossSecondHour;

	private int prodPlanThirdHour;
	private int actualProdThirdHour;
	private int timeLossThirdHour;
	
	private int prodPlanFourthHour;
	private int actualProdFourthHour;
	private int timeLossFourthHour;
	
	private int prodPlanFifthHour;
	private int actualProdFifthHour;
	private int timeLossFifthHour;

	private int prodPlanSixthHour;
	private int actualProdSixthHour;
	private int timeLossSixthHour;
	
	private int prodPlanSeventhHour;
	private int actualProdSeventhHour;
	private int timeLossSeventhHour;
	
	private int prodPlanEightthHour;
	private int actualProdEightthHour;
	private int timeLossEightthHour;

	private int totalProductionPlan;
	private int totalActualProduction;

	private int totalTimeLoss;
	
	private String operator;
	private String maintenanceOperator;
	private String 	productionSupervisor;
	private String 	productionManager;
	
	private String creationDate;
	private String createdBy;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String attribute1;
	private String attribute2;
	
	
	public int getProductionPlanId() {
		return productionPlanId;
	}
	public void setProductionPlanId(int productionPlanId) {
		this.productionPlanId = productionPlanId;
	}
	public int getMachineTypeId() {
		return machineTypeId;
	}
	public void setMachineTypeId(int machineTypeId) {
		this.machineTypeId = machineTypeId;
	}
	public int getMachineId() {
		return machineId;
	}
	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	public String getOeeReportDate() {
		return oeeReportDate;
	}
	public void setOeeReportDate(String oeeReportDate) {
		this.oeeReportDate = oeeReportDate;
	}
	public int getProductId1() {
		return productId1;
	}
	public void setProductId1(int productId1) {
		this.productId1 = productId1;
	}
	public int getProductId2() {
		return productId2;
	}
	public void setProductId2(int productId2) {
		this.productId2 = productId2;
	}
	public int getProdPlanFirstHour() {
		return prodPlanFirstHour;
	}
	public void setProdPlanFirstHour(int prodPlanFirstHour) {
		this.prodPlanFirstHour = prodPlanFirstHour;
	}
	public int getActualProdFirstHour() {
		return actualProdFirstHour;
	}
	public void setActualProdFirstHour(int actualProdFirstHour) {
		this.actualProdFirstHour = actualProdFirstHour;
	}
	public int getTimeLossFirstHour() {
		return timeLossFirstHour;
	}
	public void setTimeLossFirstHour(int timeLossFirstHour) {
		this.timeLossFirstHour = timeLossFirstHour;
	}
	public int getProdPlanSecondHour() {
		return prodPlanSecondHour;
	}
	public void setProdPlanSecondHour(int prodPlanSecondHour) {
		this.prodPlanSecondHour = prodPlanSecondHour;
	}
	public int getActualProdSecondHour() {
		return actualProdSecondHour;
	}
	public void setActualProdSecondHour(int actualProdSecondHour) {
		this.actualProdSecondHour = actualProdSecondHour;
	}
	public int getTimeLossSecondHour() {
		return timeLossSecondHour;
	}
	public void setTimeLossSecondHour(int timeLossSecondHour) {
		this.timeLossSecondHour = timeLossSecondHour;
	}
	public int getProdPlanThirdHour() {
		return prodPlanThirdHour;
	}
	public void setProdPlanThirdHour(int prodPlanThirdHour) {
		this.prodPlanThirdHour = prodPlanThirdHour;
	}
	public int getActualProdThirdHour() {
		return actualProdThirdHour;
	}
	public void setActualProdThirdHour(int actualProdThirdHour) {
		this.actualProdThirdHour = actualProdThirdHour;
	}
	public int getTimeLossThirdHour() {
		return timeLossThirdHour;
	}
	public void setTimeLossThirdHour(int timeLossThirdHour) {
		this.timeLossThirdHour = timeLossThirdHour;
	}
	public int getProdPlanFourthHour() {
		return prodPlanFourthHour;
	}
	public void setProdPlanFourthHour(int prodPlanFourthHour) {
		this.prodPlanFourthHour = prodPlanFourthHour;
	}
	public int getActualProdFourthHour() {
		return actualProdFourthHour;
	}
	public void setActualProdFourthHour(int actualProdFourthHour) {
		this.actualProdFourthHour = actualProdFourthHour;
	}
	public int getTimeLossFourthHour() {
		return timeLossFourthHour;
	}
	public void setTimeLossFourthHour(int timeLossFourthHour) {
		this.timeLossFourthHour = timeLossFourthHour;
	}
	public int getProdPlanFifthHour() {
		return prodPlanFifthHour;
	}
	public void setProdPlanFifthHour(int prodPlanFifthHour) {
		this.prodPlanFifthHour = prodPlanFifthHour;
	}
	public int getActualProdFifthHour() {
		return actualProdFifthHour;
	}
	public void setActualProdFifthHour(int actualProdFifthHour) {
		this.actualProdFifthHour = actualProdFifthHour;
	}
	public int getTimeLossFifthHour() {
		return timeLossFifthHour;
	}
	public void setTimeLossFifthHour(int timeLossFifthHour) {
		this.timeLossFifthHour = timeLossFifthHour;
	}
	public int getProdPlanSixthHour() {
		return prodPlanSixthHour;
	}
	public void setProdPlanSixthHour(int prodPlanSixthHour) {
		this.prodPlanSixthHour = prodPlanSixthHour;
	}
	public int getActualProdSixthHour() {
		return actualProdSixthHour;
	}
	public void setActualProdSixthHour(int actualProdSixthHour) {
		this.actualProdSixthHour = actualProdSixthHour;
	}
	public int getTimeLossSixthHour() {
		return timeLossSixthHour;
	}
	public void setTimeLossSixthHour(int timeLossSixthHour) {
		this.timeLossSixthHour = timeLossSixthHour;
	}
	public int getProdPlanSeventhHour() {
		return prodPlanSeventhHour;
	}
	public void setProdPlanSeventhHour(int prodPlanSeventhHour) {
		this.prodPlanSeventhHour = prodPlanSeventhHour;
	}
	public int getActualProdSeventhHour() {
		return actualProdSeventhHour;
	}
	public void setActualProdSeventhHour(int actualProdSeventhHour) {
		this.actualProdSeventhHour = actualProdSeventhHour;
	}
	public int getTimeLossSeventhHour() {
		return timeLossSeventhHour;
	}
	public void setTimeLossSeventhHour(int timeLossSeventhHour) {
		this.timeLossSeventhHour = timeLossSeventhHour;
	}
	public int getProdPlanEightthHour() {
		return prodPlanEightthHour;
	}
	public void setProdPlanEightthHour(int prodPlanEightthHour) {
		this.prodPlanEightthHour = prodPlanEightthHour;
	}
	public int getActualProdEightthHour() {
		return actualProdEightthHour;
	}
	public void setActualProdEightthHour(int actualProdEightthHour) {
		this.actualProdEightthHour = actualProdEightthHour;
	}
	public int getTimeLossEightthHour() {
		return timeLossEightthHour;
	}
	public void setTimeLossEightthHour(int timeLossEightthHour) {
		this.timeLossEightthHour = timeLossEightthHour;
	}
	public int getTotalProductionPlan() {
		return totalProductionPlan;
	}
	public void setTotalProductionPlan(int totalProductionPlan) {
		this.totalProductionPlan = totalProductionPlan;
	}
	public int getTotalActualProduction() {
		return totalActualProduction;
	}
	public void setTotalActualProduction(int totalActualProduction) {
		this.totalActualProduction = totalActualProduction;
	}
	public int getTotalTimeLoss() {
		return totalTimeLoss;
	}
	public void setTotalTimeLoss(int totalTimeLoss) {
		this.totalTimeLoss = totalTimeLoss;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMaintenanceOperator() {
		return maintenanceOperator;
	}
	public void setMaintenanceOperator(String maintenanceOperator) {
		this.maintenanceOperator = maintenanceOperator;
	}
	public String getProductionSupervisor() {
		return productionSupervisor;
	}
	public void setProductionSupervisor(String productionSupervisor) {
		this.productionSupervisor = productionSupervisor;
	}
	public String getProductionManager() {
		return productionManager;
	}
	public void setProductionManager(String productionManager) {
		this.productionManager = productionManager;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	
	
}