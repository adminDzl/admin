package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预约
 * @author gf
 * @date 2019/3/10
 */
@ApiModel(description = "申请场地信息")
public class AppointmentDTO {

    @ApiModelProperty(name = "yardappointId",value = "ID")
    private String yardappointId;

    @ApiModelProperty(name = "placeType",value = "场地类型（1.会议室，2.活动室，3健身房）")
    private Integer placeType;

    @ApiModelProperty(name = "position",value = "所处位置")
    private String position;

    @ApiModelProperty(name = "imageUrl",value = "场地图片")
    private String imageUrl;

    @ApiModelProperty(name = "placeDate",value = "场地ID")
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

    @ApiModelProperty(name = "fee",value = "金额")
    private BigDecimal fee;

    @ApiModelProperty(name = "status",value = "状态")
    private Integer status;

    @ApiModelProperty(name = "note",value = "备注")
    private String note;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
