package com.wolves.dto;

public class YunweiSyncOrder {
	//本机单号
	private String orderId;
	//运维ID标识
	private String wjbiid;
	//状态
	private String status;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getWjbiid() {
		return wjbiid;
	}
	public void setWjbiid(String wjbiid) {
		this.wjbiid = wjbiid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "YunweiSyncOrder [orderId=" + orderId + ", wjbiid=" + wjbiid + ", status=" + status + "]";
	}
	
	

}
