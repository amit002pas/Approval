package com.yodlee.sms.yad.response;


public class ResponseDataAddUpdate {

	private long maintenanceId;

	public long getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(long maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	@Override
	public String toString() {
		return "ResponseDataAddUpdate [maintenanceId=" + maintenanceId + "]";
	}
}
