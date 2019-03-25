package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/3/25.
 */
@ApiModel(description = "快速预定场地信息")
public class FastAppointDTO {

    @ApiModelProperty(name = "type",value = "场地类型")
    private Integer type;

    @ApiModelProperty(name = "startTime",value = "开始时间(yyyy-MM-dd HH:mm:ss)")
    private String startTime;

    @ApiModelProperty(name = "endTime",value = "结束时间(yyyy-MM-dd HH:mm:ss)")
    private String endTime;

    @ApiModelProperty(name = "address",value = "ID")
    private String address;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "FastAppointDTO{" +
                "type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", address='" + address + '\'' +
                '}';
    }
}
