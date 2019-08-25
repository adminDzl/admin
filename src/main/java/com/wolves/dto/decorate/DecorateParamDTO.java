package com.wolves.dto.decorate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 装修参数
 * Created by Administrator on 2019/8/25.
 */
@ApiModel(description = "装修参数")
public class DecorateParamDTO {

    @ApiModelProperty(name = "title",value = "标题信息")
    private String title;

    @ApiModelProperty(name = "fadingren",value = "法定代表人")
    private String fadingren;

    @ApiModelProperty(name = "fadingPhone",value = "法定联系电话")
    private String fadingPhone;

    @ApiModelProperty(name = "zuling",value = "租赁时间")
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
    private Date zhuangxiuTime;

    @ApiModelProperty(name = "zhuangxirenshu",value = "装修人数")
    private String zhuangxirenshu;

    @ApiModelProperty(name = "zhuangxiufangwei",value = "装修范围 复选-多个选择使用,分隔")
    private String zhuangxiufangwei;

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
}
