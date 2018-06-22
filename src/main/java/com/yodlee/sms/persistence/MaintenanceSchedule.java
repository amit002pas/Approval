package com.yodlee.sms.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="maintenance_schedule")
public class MaintenanceSchedule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MAINTENANCE_SCHEDULE_ID")
	private long maintenanceScheduleId;
	@Column(name="SUM_INFO_ID")
	private int sumInfoId;
	@Column(name="START_TIME")
	private String startTime;
	@Column(name="DURATION")
	private String duration;
	@Column(name="EFFECTIVE_DATE")
	private String effectiveDate;
	@Column(name="EXPIRY_DATE")
	private String expiryDate;
	@Column(name="TIME_ZONE")
	private String timeZone;
	@Column(name="ROW_CREATED")
	private String rowCreated;
	@Column(name="ROW_LAST_UPDATED")
	private String rowLastUpdated;
	
	public long getMaintenanceScheduleId() {
		return maintenanceScheduleId;
	}
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	public String getRowCreated() {
		return rowCreated;
	}
	public void setRowCreated(String rowCreated) {
		this.rowCreated = rowCreated;
	}
	public String getRowLastUpdated() {
		return rowLastUpdated;
	}
	public void setRowLastUpdated(String rowLastUpdated) {
		this.rowLastUpdated = rowLastUpdated;
	}
	
}
