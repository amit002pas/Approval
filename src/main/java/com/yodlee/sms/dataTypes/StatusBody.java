package com.yodlee.sms.dataTypes;

public class StatusBody {
	
	private String isApproved;
	private long total;
	
	public StatusBody(String isApproved, long total) {
		super();
		this.isApproved = isApproved;
		this.total = total;
	}
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	

}
