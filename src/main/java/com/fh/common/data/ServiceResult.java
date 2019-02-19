package com.fh.common.data;


import java.io.Serializable;

public class ServiceResult<TServiceResult> implements Serializable {

	private Integer result;
	private String msg;
	private TServiceResult data;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public TServiceResult getData() {
		return data;
	}

	public void setData(TServiceResult data) {
		this.data = data;
	}

}
