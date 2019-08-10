package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *
 * @author gf
 * @date 2019/3/24
 */
@ApiModel(description = "保修信息")
public class RepairParamsDTO {

    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "louyus",value = "楼宇（智慧大厦,信息港,其他,）")
    private String louyus;

    @ApiModelProperty(name = "loutis",value = "楼体（智慧大厦_主,智慧大厦_裙,信息港_A,信息港_B,其他_其他,智慧大厦_游泳馆）")
    private String loutis;

    @ApiModelProperty(name = "floor",value = "楼层（信息港_A_01,信息港_A_02）")
    private String floor;

    @ApiModelProperty(name = "quyus",value = "区域")
    private String quyus;

    @ApiModelProperty(name = "faultclassify",value = "故障分类（工程,弱电,物业）")
    private String faultclassify;

    @ApiModelProperty(name = "syss",value = "系统分类（办公桌,地板,影院系统,报告厅系统,沙盘系统,监控系统,窗户玻璃,门禁系统）")
    private String syss;

    @ApiModelProperty(name = "describes",value = "详细信息")
    private String describes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLouyus() {
        return louyus;
    }

    public void setLouyus(String louyus) {
        this.louyus = louyus;
    }

    public String getLoutis() {
        return loutis;
    }

    public void setLoutis(String loutis) {
        this.loutis = loutis;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getQuyus() {
        return quyus;
    }

    public void setQuyus(String quyus) {
        this.quyus = quyus;
    }

    public String getFaultclassify() {
        return faultclassify;
    }

    public void setFaultclassify(String faultclassify) {
        this.faultclassify = faultclassify;
    }

    public String getSyss() {
        return syss;
    }

    public void setSyss(String syss) {
        this.syss = syss;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    @Override
    public String toString() {
        return "RepairParamsDTO{" +
                "title='" + title + '\'' +
                ", louyus='" + louyus + '\'' +
                ", loutis='" + loutis + '\'' +
                ", floor='" + floor + '\'' +
                ", quyus='" + quyus + '\'' +
                ", faultclassify='" + faultclassify + '\'' +
                ", syss='" + syss + '\'' +
                ", describes='" + describes + '\'' +
                '}';
    }
}
