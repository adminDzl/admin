package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 *
 * @author gf
 * @date 2019/3/13
 */
@ApiModel(description = "申请场地信息")
public class OrderDTO {

    @ApiModelProperty(name = "amount",value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(name = "type",value = "类型")
    private Integer type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "amount=" + amount +
                ", type=" + type +
                '}';
    }
}
