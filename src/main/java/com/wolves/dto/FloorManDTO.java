package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 客户数据列表
 * @author gf
 * @date 2019/3/6
 */
@ApiModel(description = "申请场地信息")
public class FloorManDTO {

    @ApiModelProperty(name = "buildNo",value = "楼栋号")
    private String buildNo;

    @ApiModelProperty(name = "buildMasterName",value = "楼栋负责人")
    private String buildMasterName;

    @ApiModelProperty(name = "masterTel",value = "联系号码")
    private String masterTel;

    @ApiModelProperty(name = "floor",value = "楼层")
    private String floor;

    @ApiModelProperty(name = "floorMasterName",value = "楼层负责人")
    private String floorMasterName;

    @ApiModelProperty(name = "tel",value = "联系号码")
    private String tel;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getBuildMasterName() {
        return buildMasterName;
    }

    public void setBuildMasterName(String buildMasterName) {
        this.buildMasterName = buildMasterName;
    }

    public String getMasterTel() {
        return masterTel;
    }

    public void setMasterTel(String masterTel) {
        this.masterTel = masterTel;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FloorManDTO{" +
                "buildNo='" + buildNo + '\'' +
                ", buildMasterName='" + buildMasterName + '\'' +
                ", masterTel='" + masterTel + '\'' +
                ", floor='" + floor + '\'' +
                ", floorMasterName='" + floorMasterName + '\'' +
                ", tel='" + tel + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
