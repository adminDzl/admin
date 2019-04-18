package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一申请
 * @author gf
 * @date 2019/4/6
 */
@ApiModel(description = "统一申请参数")
public class DecorateDataDTO {

    @ApiModelProperty(name = "type",value = "类型(类型(1-楼宇装修申请，2-出门证申请，3-一卡通申请，4-楼宇施工许可申请，5-楼宇装饰工程竣工验收申请))")
    private Integer type;

    @ApiModelProperty(name = "title",value = "申请标题")
    private String title;

    @ApiModelProperty(name = "content",value = "申请内容")
    private String content;

    @ApiModelProperty(name = "idCard",value = "身份证（一卡通申请时为必填项）")
    private String idCard;

    @ApiModelProperty(name = "fileUrl",value = "需要上传的文件")
    private String fileUrl;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "DecorateDataDTO{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
