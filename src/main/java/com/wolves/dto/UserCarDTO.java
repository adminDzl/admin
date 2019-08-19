package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/8/19.
 */
@ApiModel(description = "车辆信息")
public class UserCarDTO {

    @ApiModelProperty(name = "plate",value = "车牌号")
    private String plate;

    @ApiModelProperty(name = "month",value = "购买月卡时长（几个月）")
    private Integer month;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "UserCarDTO{" +
                "plate='" + plate + '\'' +
                ", month=" + month +
                '}';
    }
}
