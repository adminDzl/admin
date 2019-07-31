package com.wolves.dto;

import java.util.Date;

/**
 * @author gf
 */
public class UserCarMonthCardDTO {

    private String userCarMonthCardId;

    private String userId;

    private String userName;

    private String cardNo;

    private String price;

    private Date useTilDate;

    private Integer cardStatus;

    private Date createTime;

    private Date updateTime;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getUseTilDate() {
        return useTilDate;
    }

    public void setUseTilDate(Date useTilDate) {
        this.useTilDate = useTilDate;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserCarMonthCardDTO{" +
                "userCarMonthCardId='" + userCarMonthCardId + '\'' +
                ", userId='" + userId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", price='" + price + '\'' +
                ", useTilDate=" + useTilDate +
                ", cardStatus=" + cardStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
