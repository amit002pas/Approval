package com.yodlee.sms.dataTypes;


public class MaintenanceWindow {
	private int maintenanceScheduleId;
	private int sumInfoId;
	private String cron;
	private int duration;
	private long effectiveDate;
	private long expiryDate;
	private String timezone;
	private long rowCreated;
	private long rowLastUpdated;
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public long getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(long effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public long getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}
	public long getRowCreated() {
		return rowCreated;
	}
	public void setRowCreated(long rowCreated) {
		this.rowCreated = rowCreated;
	}
	public long getRowLastUpdated() {
		return rowLastUpdated;
	}
	public void setRowLastUpdated(long rowLastUpdated) {
		this.rowLastUpdated = rowLastUpdated;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	@Override
	public String toString() {
		return "MaintenanceWindow [maintenanceScheduleId=" + maintenanceScheduleId + ", sumInfoId=" + sumInfoId
				+ ", cron=" + cron + ", duration=" + duration + ", effectiveDate=" + effectiveDate + ", expiryDate="
				+ expiryDate + ", timezone=" + timezone + ", rowCreated=" + rowCreated + ", rowLastUpdated="
				+ rowLastUpdated + "]";
	}
	
}
