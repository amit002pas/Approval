package com.yodlee.sms.persistence;

import org.springframework.data.mongodb.core.mapping.Document;
import com.yodlee.sms.dataTypes.MaintenanceSchedule;


@Document(collection="smscollection")
public class MaintenanceAudit {
	
	private String type;
	private String maintenanceAuditId;
	private String source;
	private String sourceURL;
	private String message;
	private String isApproved;
	private String startDateTime;
	private String endDateTime;
	private String creationDate;
	private String adtLink;
	private String isExpired;
	private int isTTR;
	private int isILS;
	private String errorMsg;
	
	private MaintenanceSchedule maintenanceSchedule;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMaintenanceAuditId() {
		return maintenanceAuditId;
	}
	public void setMaintenanceAuditId(String maintenanceAuditId) {
		this.maintenanceAuditId = maintenanceAuditId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceURL() {
		return sourceURL;
	}
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
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
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getAdtLink() {
		return adtLink;
	}
	public void setAdtLink(String adtLink) {
		this.adtLink = adtLink;
	}
	public MaintenanceSchedule getMaintenanceSchedule() {
		return maintenanceSchedule;
	}
	public void setMaintenanceSchedule(MaintenanceSchedule maintenanceSchedule) {
		this.maintenanceSchedule = maintenanceSchedule;
	}
	public String getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}
	public int getIsTTR() {
		return isTTR;
	}
	public void setIsTTR(int isTTR) {
		this.isTTR = isTTR;
	}
	public int getIsILS() {
		return isILS;
	}
	public void setIsILS(int isILS) {
		this.isILS = isILS;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Override
	public String toString() {
		return "MaintenanceAudit [type=" + type + ", maintenanceAuditId=" + maintenanceAuditId + ", source=" + source
				+ ", sourceURL=" + sourceURL + ", message=" + message + ", isApproved=" + isApproved
				+ ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", creationDate=" + creationDate
				+ ", adtLink=" + adtLink + ", isExpired=" + isExpired + ", isTTR=" + isTTR + ", isILS=" + isILS
				+ ", errorMsg=" + errorMsg + ", maintenanceSchedule=" + maintenanceSchedule + "]";
	}
}
