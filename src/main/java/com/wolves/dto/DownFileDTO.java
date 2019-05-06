package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  下载参数
 * @author gf
 * @date 2019/5/6
 */
@ApiModel(description = "下载参数信息")
public class DownFileDTO {

    @ApiModelProperty(name = "url",value = "OSS链接")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
