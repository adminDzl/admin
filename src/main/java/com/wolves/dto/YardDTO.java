package com.wolves.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/3/9.
 */
public class YardDTO {

    private String yardId;

    private Integer placeType;

    private String position;

    private String imageUrl;

    private String equipment;

    private BigDecimal rentFee;

    private Date createTime;

    public String getYardId() {
        return yardId;
    }

    public void setYardId(String yardId) {
        this.yardId = yardId;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "YardDTO{" +
                "yardId='" + yardId + '\'' +
                ", placeType=" + placeType +
                ", position='" + position + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", equipment='" + equipment + '\'' +
                ", rentFee=" + rentFee +
                ", createTime=" + createTime +
                '}';
    }
}
