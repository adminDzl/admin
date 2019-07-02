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

    @ApiModelProperty(name = "idCard",value = "身份证")
    private String idCard;

    @ApiModelProperty(name = "buildmanId",value = "楼栋")
    private String buildmanId;

    @ApiModelProperty(name = "sex",value = "男或女")
    private String sex;

    @ApiModelProperty(name = "floor",value = "楼层")
    private String floor;

    @ApiModelProperty(name = "room",value = "所在房间")
    private String room;

    @ApiModelProperty(name = "content",value = "备注")
    private String content;

    private String fileUrl;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBuildmanId() {
        return buildmanId;
    }

    public void setBuildmanId(String buildmanId) {
        this.buildmanId = buildmanId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    @Override
    public String toString() {
        return "DecorateDataDTO{" +
                "idCard='" + idCard + '\'' +
                ", buildmanId='" + buildmanId + '\'' +
                ", floor='" + floor + '\'' +
                ", room='" + room + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
