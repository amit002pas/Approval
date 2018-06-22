package com.yodlee.sms.yad.request;


public class Request {
	private String feature;
	private String operation;
	private RequestDataParam data;
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public RequestDataParam getData() {
		return data;
	}
	public void setData(RequestDataParam data) {
		this.data = data;
	}
} 