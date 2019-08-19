package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/8/19.
 */
@ApiModel(description = "月卡续费")
public class UserCarRenewalDTO {

    @ApiModelProperty(name = "userCarMonthCardId",value = "Id")
    private String userCarMonthCardId;

    @ApiModelProperty(name = "month",value = "缴费时长")
    private Integer month;

    public String getUserCarMonthCardId() {
        return userCarMonthCardId;
    }

    public void setUserCarMonthCardId(String userCarMonthCardId) {
        this.userCarMonthCardId = userCarMonthCardId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "UserCarRenewalDTO{" +
                "userCarMonthCardId='" + userCarMonthCardId + '\'' +
                ", month=" + month +
                '}';
    }
}
