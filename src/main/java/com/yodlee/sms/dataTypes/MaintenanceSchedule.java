package com.yodlee.sms.dataTypes;

public class MaintenanceSchedule {
	private int sumInfoId;
	private String agentName;
	private String startTime;
	private String startDateTime;
	private String endDateTime;
	private String duration;
	private Integer durationHours;
	private Integer durationMinutes;
	private String effectiveDate;
	private String expiryDate;
	private String timeZone;
	private String endTime;
	private String type = "maintenanceSchedule";
	private int inputMessageID;
	public int getSumInfoId() {
		return sumInfoId;
	}
	public void setSumInfoId(int sumInfoId) {
		this.sumInfoId = sumInfoId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getDurationHours() {
		return durationHours;
	}
	public void setDurationHours(Integer durationHours) {
		this.durationHours = durationHours;
	}
	public Integer getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getInputMessageID() {
		return inputMessageID;
	}
	public void setInputMessageID(int inputMessageID) {
		this.inputMessageID = inputMessageID;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	@Override
	public String toString() {
		return "MaintenanceSchedule [sumInfoId=" + sumInfoId + ", agentName=" + agentName + ", startTime=" + startTime
				+ ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", duration=" + duration
				+ ", durationHours=" + durationHours + ", durationMinutes=" + durationMinutes + ", effectiveDate="
				+ effectiveDate + ", expiryDate=" + expiryDate + ", timeZone=" + timeZone + ", endTime=" + endTime
				+ ", type=" + type + ", inputMessageID=" + inputMessageID + "]";
	} 
}
