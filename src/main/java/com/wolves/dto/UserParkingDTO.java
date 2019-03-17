package com.wolves.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/3/17.
 */
public class UserParkingDTO {

    private Date beginTime;

    private Date endTime;

    private String totalHour;

    private BigDecimal fee;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
