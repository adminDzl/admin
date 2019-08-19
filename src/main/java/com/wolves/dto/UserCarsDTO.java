package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Administrator on 2019/8/19.
 */
@ApiModel(description = "我购买月卡的车辆信息")
public class UserCarsDTO {

    @ApiModelProperty(name = "userCarMonthCardId",value = "ID")
    private String userCarMonthCardId;

    @ApiModelProperty(name = "userId",value = "用户ID")
    private String userId;

    @ApiModelProperty(name = "cardNo",value = "月卡号")
    private String cardNo;

    @ApiModelProperty(name = "carNo",value = "车牌号")
    private String carNo;

    @ApiModelProperty(name = "useTilDate",value = "到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date useTilDate;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getUserCarMonthCardId() {
        return userCarMonthCardId;
    }

    public void setUserCarMonthCardId(String userCarMonthCardId) {
        this.userCarMonthCardId = userCarMonthCardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Date getUseTilDate() {
        return useTilDate;
    }

    public void setUseTilDate(Date useTilDate) {
        this.useTilDate = useTilDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserCarsDTO{" +
                "userCarMonthCardId='" + userCarMonthCardId + '\'' +
                ", userId='" + userId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", carNo='" + carNo + '\'' +
                ", useTilDate=" + useTilDate +
                ", createTime=" + createTime +
                '}';
    }
}
