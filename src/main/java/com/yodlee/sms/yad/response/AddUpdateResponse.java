package com.yodlee.sms.yad.response;


public class AddUpdateResponse {
	private AddUpdateAppResponse appResponse;

	public AddUpdateAppResponse getAddUpdateAppResponse() {
		return appResponse;
	}

	public void setAddUpdateAppResponse(AddUpdateAppResponse appResponse) {
		this.appResponse = appResponse;
	}

	@Override
	public String toString() {
		return "AddUpdateResponse [addUpdateAppResponse=" + appResponse + "]";
	}
}
