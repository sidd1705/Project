package com.ecopack.domains;

public class OEELossReport {
	
	
	LossTypesMaster lossTypes;
	LossReport lossReport;
	
	public OEELossReport(LossTypesMaster lossTypes, LossReport lossReport) {
		super();
		this.lossTypes = lossTypes;
		this.lossReport = lossReport;
	}
	
	public OEELossReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LossTypesMaster getLossTypes() {
		return lossTypes;
	}
	public void setLossTypes(LossTypesMaster lossTypes) {
		this.lossTypes = lossTypes;
	}
	public LossReport getLossReport() {
		return lossReport;
	}
	public void setLossReport(LossReport lossReport) {
		this.lossReport = lossReport;
	}

	
}
