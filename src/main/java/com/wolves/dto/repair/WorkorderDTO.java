package com.wolves.dto.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 报修查询列表
 * Created by Administrator on 2019/8/8.
 */
@ApiModel(description = "报修查询列表")
public class WorkorderDTO {

    @ApiModelProperty(name = "wjbiid",value = "工单ID,隐藏的唯一标识(终止工单使用)")
    private String wjbiid;

    @ApiModelProperty(name = "creator",value = "创建人")
    private String creator;

    @ApiModelProperty(name = "workoderId",value = "工单编号")
    private String workoderId;

    @ApiModelProperty(name = "acceptor",value = "受理人")
    private String acceptor;

    @ApiModelProperty(name = "procId",value = "流程实例ID(终止工单使用)")
    private String procId;

    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "ssiid",value = "服务条目ID")
    private String ssiid;

    @ApiModelProperty(name = "describes",value = "描述")
    private String describes;

    @ApiModelProperty(name = "orderState",value = "状态")
    private Integer orderState;

    @ApiModelProperty(name = "App_Id",value = "提交工单的APP用户ID")
    private String App_Id;

    @ApiModelProperty(name = "assignmentTime",value = "分派时间")
    private String assignmentTime;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    private String createTime;

    @ApiModelProperty(name = "taskId",value = "环节ID  (终止工单使用)")
    private String taskId;

    @ApiModelProperty(name = "group",value = "受理组")
    private String group;

    @ApiModelProperty(name = "authType",value = "审批操作标识")
    private String authType;

    public String getWjbiid() {
        return wjbiid;
    }

    public void setWjbiid(String wjbiid) {
        this.wjbiid = wjbiid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getWorkoderId() {
        return workoderId;
    }

    public void setWorkoderId(String workoderId) {
        this.workoderId = workoderId;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSsiid() {
        return ssiid;
    }

    public void setSsiid(String ssiid) {
        this.ssiid = ssiid;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getApp_Id() {
        return App_Id;
    }

    public void setApp_Id(String app_Id) {
        App_Id = app_Id;
    }

    public String getAssignmentTime() {
        return assignmentTime;
    }

    public void setAssignmentTime(String assignmentTime) {
        this.assignmentTime = assignmentTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
