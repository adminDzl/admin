package com.wolves.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author gf
 */
@ApiModel(description = "保修信息")
public class RepairApplyDTO {

    @ApiModelProperty(name = "repairApplyId",value = "保修申请ID")
    private String repairApplyId;

    @ApiModelProperty(name = "applyContent",value = "保修申请内容")
    private String applyContent;

    @ApiModelProperty(name = "applyStatus",value = "保修申请状态")
    private Integer applyStatus;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getRepairApplyId() {
        return repairApplyId;
    }

    public void setRepairApplyId(String repairApplyId) {
        this.repairApplyId = repairApplyId;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RepairApplyDTO{" +
                "repairApplyId='" + repairApplyId + '\'' +
                ", applyContent='" + applyContent + '\'' +
                ", applyStatus=" + applyStatus +
                ", createTime=" + createTime +
                '}';
    }
}
