package com.wolves.dto.decorate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 装修
 * Created by Administrator on 2019/8/25.
 */
public class DecorationApplyDTO {

    @ApiModelProperty(name = "decorationApplyId",value = "ID")
    private String decorationApplyId;

    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;

    @ApiModelProperty(name = "title",value = "标题信息")
    private String title;

    @ApiModelProperty(name = "fadingren",value = "法定代表人")
    private String fadingren;

    @ApiModelProperty(name = "fadingPhone",value = "法定联系电话")
    private String fadingPhone;

    @ApiModelProperty(name = "zuling",value = "租赁时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date zuling;

    @ApiModelProperty(name = "xianchangren",value = "现场负责人")
    private String xianchangren;

    @ApiModelProperty(name = "xianchangPhone",value = "现场联系电话")
    private String xianchangPhone;

    @ApiModelProperty(name = "zhuangxiuName",value = "装修单位")
    private String zhuangxiuName;

    @ApiModelProperty(name = "zhizhaoNum",value = "执照号码")
    private String zhizhaoNum;

    @ApiModelProperty(name = "zhuangxiuren",value = "装修负责人")
    private String zhuangxiuren;

    @ApiModelProperty(name = "zhuangxiuPhone",value = "装修联系电话")
    private String zhuangxiuPhone;

    @ApiModelProperty(name = "zhuangxiuTime",value = "装修时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date zhuangxiuTime;

    @ApiModelProperty(name = "zhuangxirenshu",value = "装修人数")
    private String zhuangxirenshu;

    @ApiModelProperty(name = "zhuangxiufangwei",value = "装修范围 复选-多个选择使用,分隔")
    private String zhuangxiufangwei;

    @ApiModelProperty(name = "procId",value = "流程id")
    private String procId;

    @ApiModelProperty(name = "wjbiid",value = "工单唯一标识")
    private String wjbiid;

    @ApiModelProperty(name = "taskId",value = "用户环节ID")
    private String taskId;

    @ApiModelProperty(name = "status",value = "状态（0：正常，1：删除）")
    private Integer status;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "更新时间")
    private Date updateTime;

    public String getDecorationApplyId() {
        return decorationApplyId;
    }

    public void setDecorationApplyId(String decorationApplyId) {
        this.decorationApplyId = decorationApplyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFadingren() {
        return fadingren;
    }

    public void setFadingren(String fadingren) {
        this.fadingren = fadingren;
    }

    public String getFadingPhone() {
        return fadingPhone;
    }

    public void setFadingPhone(String fadingPhone) {
        this.fadingPhone = fadingPhone;
    }

    public Date getZuling() {
        return zuling;
    }

    public void setZuling(Date zuling) {
        this.zuling = zuling;
    }

    public String getXianchangren() {
        return xianchangren;
    }

    public void setXianchangren(String xianchangren) {
        this.xianchangren = xianchangren;
    }

    public String getXianchangPhone() {
        return xianchangPhone;
    }

    public void setXianchangPhone(String xianchangPhone) {
        this.xianchangPhone = xianchangPhone;
    }

    public String getZhuangxiuName() {
        return zhuangxiuName;
    }

    public void setZhuangxiuName(String zhuangxiuName) {
        this.zhuangxiuName = zhuangxiuName;
    }

    public String getZhizhaoNum() {
        return zhizhaoNum;
    }

    public void setZhizhaoNum(String zhizhaoNum) {
        this.zhizhaoNum = zhizhaoNum;
    }

    public String getZhuangxiuren() {
        return zhuangxiuren;
    }

    public void setZhuangxiuren(String zhuangxiuren) {
        this.zhuangxiuren = zhuangxiuren;
    }

    public String getZhuangxiuPhone() {
        return zhuangxiuPhone;
    }

    public void setZhuangxiuPhone(String zhuangxiuPhone) {
        this.zhuangxiuPhone = zhuangxiuPhone;
    }

    public Date getZhuangxiuTime() {
        return zhuangxiuTime;
    }

    public void setZhuangxiuTime(Date zhuangxiuTime) {
        this.zhuangxiuTime = zhuangxiuTime;
    }

    public String getZhuangxirenshu() {
        return zhuangxirenshu;
    }

    public void setZhuangxirenshu(String zhuangxirenshu) {
        this.zhuangxirenshu = zhuangxirenshu;
    }

    public String getZhuangxiufangwei() {
        return zhuangxiufangwei;
    }

    public void setZhuangxiufangwei(String zhuangxiufangwei) {
        this.zhuangxiufangwei = zhuangxiufangwei;
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

    @Override
    public String toString() {
        return "DecorationApplyDTO{" +
                "decorationApplyId='" + decorationApplyId + '\'' +
                ", title='" + title + '\'' +
                ", fadingren='" + fadingren + '\'' +
                ", fadingPhone='" + fadingPhone + '\'' +
                ", zuling=" + zuling +
                ", xianchangren='" + xianchangren + '\'' +
                ", xianchangPhone='" + xianchangPhone + '\'' +
                ", zhuangxiuName='" + zhuangxiuName + '\'' +
                ", zhizhaoNum='" + zhizhaoNum + '\'' +
                ", zhuangxiuren='" + zhuangxiuren + '\'' +
                ", zhuangxiuPhone='" + zhuangxiuPhone + '\'' +
                ", zhuangxiuTime=" + zhuangxiuTime +
                ", zhuangxirenshu='" + zhuangxirenshu + '\'' +
                ", zhuangxiufangwei='" + zhuangxiufangwei + '\'' +
                ", procId='" + procId + '\'' +
                ", wjbiid='" + wjbiid + '\'' +
                ", taskId='" + taskId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
