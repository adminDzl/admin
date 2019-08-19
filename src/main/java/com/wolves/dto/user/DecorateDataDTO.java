package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一申请
 * @author gf
 * @date 2019/4/6
 */
@ApiModel(description = "一卡通申请参数")
public class DecorateDataDTO {

    @ApiModelProperty(name = "type",value = "业务类型")
    private String type;

    @ApiModelProperty(name = "name",value = "姓名")
    private String name;

    @ApiModelProperty(name = "sex",value = "男或女")
    private String sex;

    @ApiModelProperty(name = "idCard",value = "身份证")
    private String idCard;

    @ApiModelProperty(name = "phone",value = "手机号码")
    private String phone;

    @ApiModelProperty(name = "access",value = "权限")
    private String access;

    @ApiModelProperty(name = "content",value = "备注")
    private String content;

    private String fileUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "DecorateDataDTO{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", access='" + access + '\'' +
                ", content='" + content + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
