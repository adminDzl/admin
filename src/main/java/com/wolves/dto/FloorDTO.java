package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2019/4/11.
 */
@ApiModel(description = "楼栋信息")
public class FloorDTO {

    @ApiModelProperty(name = "id",value = "ID")
    private String id;

    @ApiModelProperty(name = "name",value = "楼层")
    private String name;

    private List<RoomDTO> roomDTOs;

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

    public List<RoomDTO> getRoomDTOs() {
        return roomDTOs;
    }

    public void setRoomDTOs(List<RoomDTO> roomDTOs) {
        this.roomDTOs = roomDTOs;
    }
}
