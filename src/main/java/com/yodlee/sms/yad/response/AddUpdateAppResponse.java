package com.yodlee.sms.yad.response;

public class AddUpdateAppResponse {
	
	private String status;
	private String description;
	private ResponseDataAddUpdate responseData;
	
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
	public ResponseDataAddUpdate getResponseData() {
		return responseData;
	}
	public void setResponseData(ResponseDataAddUpdate responseData) {
		this.responseData = responseData;
	}
	@Override
	public String toString() {
		return "AddUpdateResponse [status=" + status + ", description=" + description + ", responseData=" + responseData
				+ "]";
	}

}
