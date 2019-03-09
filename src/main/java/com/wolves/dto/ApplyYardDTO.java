package com.wolves.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/3/9.
 */
public class ApplyYardDTO {
    private String yardId;

    private Date placeDate;

    private Date beginTime;

    private Date endTime;

    private String duration;

    private BigDecimal amount;

    private String note;

    public String getYardId() {
        return yardId;
    }

    public void setYardId(String yardId) {
        this.yardId = yardId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ApplyYardDTO{" +
                "yardId='" + yardId + '\'' +
                ", placeDate=" + placeDate +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", duration='" + duration + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }
}
