package com.wolves.dto.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 报修流程明细
 * Created by Administrator on 2019/8/8.
 */
@ApiModel(description = "报修流程明细")
public class ProcessLogDTO {

    @ApiModelProperty(name = "handleStaff",value = "处理人")
    private String handleStaff;

    @ApiModelProperty(name = "linkName",value = "环节名称")
    private String linkName;

    @ApiModelProperty(name = "actionName",value = "操作名称")
    private String actionName;

    @ApiModelProperty(name = "opinion",value = "备注意见")
    private String opinion;

    @ApiModelProperty(name = "handleTimes",value = "处理时长")
    private Integer handleTimes;

    @ApiModelProperty(name = "startTime",value = "进入时间")
    private Date startTime;

    public String getHandleStaff() {
        return handleStaff;
    }

    public void setHandleStaff(String handleStaff) {
        this.handleStaff = handleStaff;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Integer getHandleTimes() {
        return handleTimes;
    }

    public void setHandleTimes(Integer handleTimes) {
        this.handleTimes = handleTimes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
