package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gf
 */
@ApiModel(description = "用户车辆信息")
public class UserCarBindDTO implements Serializable{

    @ApiModelProperty(name = "userCarBindId",value = "ID")
    private String userCarBindId;

    @ApiModelProperty(name = "userId",value = "用户ID")
    private String userId;

    @ApiModelProperty(name = "carNo",value = "车牌号")
    private String carNo;

    @ApiModelProperty(name = "status",value = "状态")
    private String status;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getUserCarBindId() {
        return userCarBindId;
    }

    public void setUserCarBindId(String userCarBindId) {
        this.userCarBindId = userCarBindId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserCarBindDTO{" +
                "userCarBindId='" + userCarBindId + '\'' +
                ", userId='" + userId + '\'' +
                ", carNo='" + carNo + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
