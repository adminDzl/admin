package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一申请
 * @author gf
 * @date 2019/4/6
 */
@ApiModel(description = "统一申请参数")
public class DecorateDataDTO {

    @ApiModelProperty(name = "type",value = "类型(1-装修申请，2-其他)")
    private Integer type;

    @ApiModelProperty(name = "title",value = "申请标题")
    private String title;

    @ApiModelProperty(name = "content",value = "申请内容")
    private String content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DecorateDataDTO{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
