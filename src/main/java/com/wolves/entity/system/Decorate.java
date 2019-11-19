package com.wolves.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 统一申请
 * @author gf
 * @date 2019/4/6
 */
@ApiModel(description = "统一申请")
public class Decorate {

    @ApiModelProperty(name = "decorateId",value = "ID")
    private String decorateId;

    @ApiModelProperty(name = "decorateNo",value = "编号")
    private String decorateNo;

    @ApiModelProperty(name = "userId",value = "用户Id")
    private String userId;

    @ApiModelProperty(name = "type",value = "身份证")
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

    @ApiModelProperty(name = "buildman",value = "楼栋")
    private String buildman;

    @ApiModelProperty(name = "floor",value = "楼层")
    private String floor;
    
    @ApiModelProperty(name="body",value="楼体")
    private String body;

    @ApiModelProperty(name = "room",value = "房间")
    private String room;

    @ApiModelProperty(name = "content",value = "申请内容")
    private String content;

    @ApiModelProperty(name = "fileUrl",value = "文件")
    private String fileUrl;

    @ApiModelProperty(name = "status",value = "审核状态（0-待审核，1-审核通过，2-已驳回，3-取消）")
    private Integer status;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public String getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(String decorateId) {
        this.decorateId = decorateId;
    }

    public String getDecorateNo() {
        return decorateNo;
    }

    public void setDecorateNo(String decorateNo) {
        this.decorateNo = decorateNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getBuildman() {
        return buildman;
    }

    public void setBuildman(String buildman) {
        this.buildman = buildman;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
