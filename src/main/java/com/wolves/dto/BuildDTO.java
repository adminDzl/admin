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

    public List<FloorDTO> getFloorDTOs() {
        return floorDTOs;
    }

    public void setFloorDTOs(List<FloorDTO> floorDTOs) {
        this.floorDTOs = floorDTOs;
    }
}
