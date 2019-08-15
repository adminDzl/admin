package com.wolves.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wolves.dto.user.RepairParamsDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author gf
 * @date 2019/3/24
 */
public class Repair extends RepairParamsDTO {

    @ApiModelProperty(name = "repairApplyId",value = "保修申请ID")
    private String repairApplyId;

    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;

    @ApiModelProperty(name = "procId",value = "流程实例ID")
    private String procId;

    @ApiModelProperty(name = "wjbiid",value = "工单唯一标识")
    private String wjbiid;

    @ApiModelProperty(name = "taskId",value = "用户环节ID")
    private String taskId;

    @ApiModelProperty(name = "imageUrls",value = "图片")
    private String imageUrls;

    @ApiModelProperty(name = "status",value = "状态")
    private Integer status;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public String getRepairApplyId() {
        return repairApplyId;
    }

    public void setRepairApplyId(String repairApplyId) {
        this.repairApplyId = repairApplyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getWjbiid() {
        return wjbiid;
    }

    public void setWjbiid(String wjbiid) {
        this.wjbiid = wjbiid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairApplyId='" + repairApplyId + '\'' +
                ", userId='" + userId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
