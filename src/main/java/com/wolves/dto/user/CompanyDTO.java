package com.wolves.dto.user;

import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2019/3/20
 */
public class CompanyDTO extends BaseCompanyDTO{

    private Integer type;

    private Integer status;

    private String description;

    private String companyCertify;

    private String placeId;

    private String logo;

    private Date createTime;

    private Date updateTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyCertify() {
        return companyCertify;
    }

    public void setCompanyCertify(String companyCertify) {
        this.companyCertify = companyCertify;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
}
