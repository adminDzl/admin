package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author gf
 * @date 2019/3/10
 */
@ApiModel(description = "分页")
public class PageDataDTO {

    @ApiModelProperty(name = "page",value = "页数")
    private Integer page;

    @ApiModelProperty(name = "size",value = "条数")
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageDataDTO{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
