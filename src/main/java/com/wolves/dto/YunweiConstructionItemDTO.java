package com.wolves.dto;

import io.swagger.annotations.ApiModelProperty;

public class YunweiConstructionItemDTO {
	@ApiModelProperty(name = "items",value = "项目")
	private String items;
	@ApiModelProperty(name = "type",value = "类型")
	private String type;
	@ApiModelProperty(name = "amount",value = "数量")
	private String amount;
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
