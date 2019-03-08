package com.wolves.dto;

/**
 * Created by Administrator on 2019/3/8.
 */
public class FloorManAllDTO {

    private String floormanId;

    private String floor;

    private String floorMasterName;

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
