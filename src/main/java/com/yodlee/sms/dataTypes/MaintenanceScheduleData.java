package com.yodlee.sms.dataTypes;

public class MaintenanceScheduleData {
	private String auditId;
	private int sumInfoId;
	private String startDateTime;
	private String endDateTime;
	private String expiryDate;
	private String recurrance;
	private static final String notes = "Created by WebService API";
	
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public int getSumInfoId() {
		return sumInfoId;
	}
	public void setSumInfoId(int sumInfoId) {
		this.sumInfoId = sumInfoId;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getRecurrance() {
		return recurrance;
	}
	public void setRecurrance(String recurrance) {
		this.recurrance = recurrance;
	}
	public String getNotes() {
		return notes;
	}
	@Override
	public String toString() {
		return "MaintenanceScheduleData [auditId=" + auditId + ", sumInfoId=" + sumInfoId + ", startDateTime="
				+ startDateTime + ", endDateTime=" + endDateTime + ", expiryDate=" + expiryDate + ", recurrance="
				+ recurrance + "]";
	}
}
