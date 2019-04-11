package com.wolves.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2019/3/20
 */
@ApiModel(description = "公司信息")
public class CompanyDTO extends BaseCompanyDTO{

    @ApiModelProperty(name = "companyId", value = "企业id")
    private String companyId;
    @ApiModelProperty(name = "type",value = "类型")
    private Integer type;
    @ApiModelProperty(name = "status",value = "审核状态")
    private Integer status;
    @ApiModelProperty(name = "description",value = "手机号码")
    private String description;
    @ApiModelProperty(name = "companyCertify",value = "手机号码")
    private String companyCertify;
    @ApiModelProperty(name = "placeId",value = "办公区域id")
    private String placeId;
    @ApiModelProperty(name = "logo",value = "logo")
    private String logo;
    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(name = "updateTime",value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "companyId='" + companyId + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", companyCertify='" + companyCertify + '\'' +
                ", placeId='" + placeId + '\'' +
                ", logo='" + logo + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
