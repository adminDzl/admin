package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 房间信息
 * @author Administrator
 * @date 2019/4/11
 */
@ApiModel(description = "房间信息")
public class RoomDTO {

    @ApiModelProperty(name = "id",value = "id")
    private String id;

    @ApiModelProperty(name = "name",value = "房间")
    private String name;

    @ApiModelProperty(name="bodyname", value="楼体")
    private String bodyname;
    /**
     * 不展示
     */
    @JsonIgnore
    private String floormanId;

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

    public String getFloormanId() {
        return floormanId;
    }

    public void setFloormanId(String floormanId) {
        this.floormanId = floormanId;
    }
    

    public String getBodyname() {
		return bodyname;
	}

	public void setBodyname(String bodyname) {
		this.bodyname = bodyname;
	}

	@Override
    public String toString() {
        return "RoomDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
