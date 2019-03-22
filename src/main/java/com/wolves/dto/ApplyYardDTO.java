package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2019/3/9
 */
@ApiModel(description = "申请场地信息")
public class ApplyYardDTO {

    @ApiModelProperty(name = "yardId",value = "ID")
    private String yardId;

    @ApiModelProperty(name = "placeDate",value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date placeDate;

    @ApiModelProperty(name = "beginTime",value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    @ApiModelProperty(name = "endTime",value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(name = "duration",value = "时长")
    private String duration;

    @ApiModelProperty(name = "amount",value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(name = "note",value = "备注")
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
