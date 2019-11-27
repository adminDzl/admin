package com.wolves.dto;

public class YunweiEmail {
	private String phone;
	private String email;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "YunweiEmail [phone=" + phone + ", email=" + email + "]";
	}
	
	

}
