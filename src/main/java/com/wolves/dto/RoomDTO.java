package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/4/11.
 */
@ApiModel(description = "房间信息")
public class RoomDTO {

    @ApiModelProperty(name = "id",value = "id")
    private String id;

    @ApiModelProperty(name = "name",value = "房间")
    private String name;

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
}
