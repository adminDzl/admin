package com.wolves.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预约
 * @author gf
 * @date 2019/3/10
 */
public class AppointmentDTO {

    private String yardappointId;

    private Integer placeType;

    private String position;

    private Date placeDate;

    private Date beginTime;

    private Date endTime;

    private String duration;

    private BigDecimal fee;

    private Integer status;

    private String note;

    private Date createTime;

    public String getYardappointId() {
        return yardappointId;
    }

    public void setYardappointId(String yardappointId) {
        this.yardappointId = yardappointId;
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

    public Date getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(Date placeDate) {
        this.placeDate = placeDate;
    }

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
                "yardappointId='" + yardappointId + '\'' +
                ", placeType=" + placeType +
                ", position='" + position + '\'' +
                ", placeDate=" + placeDate +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                ", status=" + status +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
