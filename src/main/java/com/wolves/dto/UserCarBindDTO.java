package com.wolves.dto;

import java.util.Date;

public class UserCarBindDTO {

    private String userCarBindId;

    private String userId;

    private String carNo;

    private String status;

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
}
