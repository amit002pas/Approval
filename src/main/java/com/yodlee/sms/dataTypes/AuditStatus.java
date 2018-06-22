package com.yodlee.sms.dataTypes;

public class AuditStatus {
	
	private long total;
	private long approved;
	private long rejected;
	private long pending;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getApproved() {
		return approved;
	}
	public void setApproved(long approved) {
		this.approved = approved;
	}
	public long getRejected() {
		return rejected;
	}
	public void setRejected(long rejected) {
		this.rejected = rejected;
	}
	public long getPending() {
		return pending;
	}
	public void setPending(long pending) {
		this.pending = pending;
	}
	
	

}
