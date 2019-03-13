package com.wolves.dto;

import java.math.BigDecimal;

/**
 * Created by gf on 2019/3/13.
 */
public class OrderDTO {

    private BigDecimal amount;

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
}
