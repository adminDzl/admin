package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Administrator
 * @date 2019/3/8
 */
@ApiModel(description = "申请场地信息")
public class FloorManAllDTO {

    @ApiModelProperty(name = "floormanId",value = "ID")
    private String floormanId;

    @ApiModelProperty(name = "floor",value = "楼层")
    private String floor;

    @ApiModelProperty(name = "floorMasterName",value = "楼层负责人")
    private String floorMasterName;

    @ApiModelProperty(name = "masterTel",value = "联系号码")
    private String masterTel;

    public String getFloormanId() {
        return floormanId;
    }

    public void setFloormanId(String floormanId) {
        this.floormanId = floormanId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloorMasterName() {
        return floorMasterName;
    }

    public void setFloorMasterName(String floorMasterName) {
        this.floorMasterName = floorMasterName;
    }

    public String getMasterTel() {
        return masterTel;
    }

    public void setMasterTel(String masterTel) {
        this.masterTel = masterTel;
    }

    @Override
    public String toString() {
        return "FloorManAllDTO{" +
                "floormanId='" + floormanId + '\'' +
                ", floor='" + floor + '\'' +
                ", floorMasterName='" + floorMasterName + '\'' +
                ", masterTel='" + masterTel + '\'' +
                '}';
    }
}
