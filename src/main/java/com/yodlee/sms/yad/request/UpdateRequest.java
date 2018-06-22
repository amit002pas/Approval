package com.yodlee.sms.yad.request;

public class UpdateRequest implements RequestDataParam {
	private int maintenanceScheduleId;
	private int sumInfoId;
	private String startDateTime;	
	private String endDateTime;	
	private String expiryDate;	
	private String recurrance;
	private String notes;
	public int getMaintenanceScheduleId() {
		return maintenanceScheduleId;
	}
	public void setMaintenanceScheduleId(int maintenanceScheduleId) {
		this.maintenanceScheduleId = maintenanceScheduleId;
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
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "UpdateRequest [maintenanceScheduleId=" + maintenanceScheduleId + ", sumInfoId=" + sumInfoId
				+ ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", expiryDate=" + expiryDate
				+ ", recurrance=" + recurrance + ", notes=" + notes + "]";
	}
	
} 
