package com.ecopack.domains;

import java.util.List;

public class OEEReport {

	
	ProductionSchedule productionSchedule;
	OEEProductionPlan oeeProductionPlan;
	LossReport lossReport;
	List<OEELossReport> oeeLossReportList;
	
	private int currentUserId;
	
	public OEEReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OEEReport(ProductionSchedule productionSchedule, OEEProductionPlan oeeProductionPlan,
			LossReport lossReport) {
		super();
		this.productionSchedule = productionSchedule;
		this.oeeProductionPlan = oeeProductionPlan;
		this.lossReport = lossReport;
	}
	public ProductionSchedule getProductionSchedule() {
		return productionSchedule;
	}
	public void setProductionSchedule(ProductionSchedule productionSchedule) {
		this.productionSchedule = productionSchedule;
	}
	public OEEProductionPlan getOeeProductionPlan() {
		return oeeProductionPlan;
	}
	public void setOeeProductionPlan(OEEProductionPlan oeeProductionPlan) {
		this.oeeProductionPlan = oeeProductionPlan;
	}
	public LossReport getLossReport() {
		return lossReport;
	}
	public void setLossReport(LossReport lossReport) {
		this.lossReport = lossReport;
	}
	
	
	public List<OEELossReport> getOeeLossReportList() {
		return oeeLossReportList;
	}
	public void setOeeLossReportList(List<OEELossReport> oeeLossReportList) {
		this.oeeLossReportList = oeeLossReportList;
	}
	public OEEReport(ProductionSchedule productionSchedule, OEEProductionPlan oeeProductionPlan,
			List<OEELossReport> oeeLossReportList) {
		super();
		this.productionSchedule = productionSchedule;
		this.oeeProductionPlan = oeeProductionPlan;
		this.oeeLossReportList = oeeLossReportList;
	}
	
	public int getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	
	
	
	
	
	
	
	
	
}
