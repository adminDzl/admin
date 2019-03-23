package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2019/3/17
 */
@ApiModel(description = "申请场地信息")
public class UserParkingDTO implements Serializable{

    @ApiModelProperty(name = "beginTime",value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(name = "endTime",value = "结束时间")
    private Date endTime;

    @ApiModelProperty(name = "totalHour",value = "时长")
    private String totalHour;

    @ApiModelProperty(name = "fee",value = "金额")
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

    @Override
    public String toString() {
        return "UserParkingDTO{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", totalHour='" + totalHour + '\'' +
                ", fee=" + fee +
                '}';
    }
}
