package com.yodlee.sms.yad.response;

public class FetchMaintenanceResponse {

	private String status;
	private String description;
	private ResponseDataFetch responseData;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ResponseDataFetch getResponseData() {
		return responseData;
	}
	public void setResponseData(ResponseDataFetch responseData) {
		this.responseData = responseData;
	}
	@Override
	public String toString() {
		return "FetchMaintenanceResponse [status=" + status + ", description=" + description + ", responseData="
				+ responseData + "]";
	}

} 