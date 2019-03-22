package com.wolves.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author gf
 */
@ApiModel(description = "保修明细")
public class RepairApplyDetailDTO {

    @ApiModelProperty(name = "applyContent",value = "申请内容")
    private String applyContent;

    @ApiModelProperty(name = "imageUrls",value = "图片链接")
    private String imageUrls;

    @ApiModelProperty(name = "applyStatus",value = "申请状态")
    private Integer applyStatus;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTimne;

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getCreateTimne() {
        return createTimne;
    }

    public void setCreateTimne(Date createTimne) {
        this.createTimne = createTimne;
    }

    @Override
    public String toString() {
        return "RepairApplyDetailDTO{" +
                "applyContent='" + applyContent + '\'' +
                ", imageUrls='" + imageUrls + '\'' +
                ", applyStatus=" + applyStatus +
                ", createTimne=" + createTimne +
                '}';
    }
}
