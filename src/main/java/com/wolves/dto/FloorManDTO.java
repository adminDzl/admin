package com.wolves.dto;

import java.util.Date;

/**
 * 客户数据列表
 * @author gf
 * @date 2019/3/6
 */
public class FloorManDTO {

    private String buildNo;

    private String buildMasterName;

    private String masterTel;

    private String floor;

    private String floorMasterName;

    private String tel;

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
