package com.yodlee.sms.yad.response;


public class AppResponse {
	private FetchMaintenanceResponse appResponse;

	public FetchMaintenanceResponse getAppResponse() {
		return appResponse;
	}

	public void setAppResponse(FetchMaintenanceResponse appResponse) {
		this.appResponse = appResponse;
	}

	@Override
	public String toString() {
		return "AppResponse [appResponse=" + appResponse + "]";
	}
	
} 