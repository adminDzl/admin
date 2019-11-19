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

    private String carNo;

    private String price;

    private Date useTilDate;

    private Date createTime;

    private Date updateTime;
    
    private Integer months;    

    public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

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

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
