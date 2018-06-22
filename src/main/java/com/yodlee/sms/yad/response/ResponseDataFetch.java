package com.yodlee.sms.yad.response;

import java.util.List;

import com.yodlee.sms.dataTypes.MaintenanceWindow;

public class ResponseDataFetch {
	private List<MaintenanceWindow> maintenanceWindows;

	public List<MaintenanceWindow> getMaintenanceWindows() {
		return maintenanceWindows;
	}

	public void setMaintenanceWindows(List<MaintenanceWindow> maintenanceWindows) {
		this.maintenanceWindows = maintenanceWindows;
	}

	@Override
	public String toString() {
		return "ResponseDataFetch [maintenanceWindows=" + maintenanceWindows + "]";
	}
}
