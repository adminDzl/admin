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

    @ApiModelProperty(name = "content",value = "保修内容")
    private String content;

    @ApiModelProperty(name = "imageUrl",value = "图片")
    private List<String> imageUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "RepairParamsDTO{" +
                "content='" + content + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
