package com.yodlee.sms.dataTypes;

import com.yodlee.sms.yad.response.AddUpdateResponse;

public class YADADTResponse {
	private String id;
	private AddUpdateResponse addUpdateResponse;
	private String adtResponse;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AddUpdateResponse getAddUpdateResponse() {
		return addUpdateResponse;
	}
	public void setAddUpdateResponse(AddUpdateResponse addUpdateResponse) {
		this.addUpdateResponse = addUpdateResponse;
	}
	public String getAdtResponse() {
		return adtResponse;
	}
	public void setAdtResponse(String adtResponse) {
		this.adtResponse = adtResponse;
	}
	@Override
	public String toString() {
		return "YADADTResponse [id=" + id + ", addUpdateResponse=" + addUpdateResponse + ", adtResponse=" + adtResponse
				+ "]";
	}
}
