package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2019/4/11.
 */
@ApiModel(description = "楼栋信息")
public class BuildDTO {

    @ApiModelProperty(name = "id",value = "ID")
    private String id;

    @ApiModelProperty(name = "name",value = "楼栋")
    private String name;

    @ApiModelProperty(name = "buildMasterName",value = "楼栋长")
    private String buildMasterName;

    @ApiModelProperty(name = "masterTel",value = "号码")
    private String masterTel;

    private List<FloorDTO> floorDTOs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildMasterName() {
        return buildMasterName;
    }

    public void setBuildMasterName(String buildMasterName) {
        this.buildMasterName = buildMasterName;
    }

    public String getMasterTel() {
        return masterTel;
    }

    public void setMasterTel(String masterTel) {
        this.masterTel = masterTel;
    }

    public List<FloorDTO> getFloorDTOs() {
        return floorDTOs;
    }

    public void setFloorDTOs(List<FloorDTO> floorDTOs) {
        this.floorDTOs = floorDTOs;
    }
}
