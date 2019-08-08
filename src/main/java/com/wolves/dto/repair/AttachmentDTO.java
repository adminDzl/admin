package com.wolves.dto.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/8/8.
 */
@ApiModel(description = "附件")
public class AttachmentDTO {

    @ApiModelProperty(name = "Att_name",value = "附件名称")
    private String Att_name;

    @ApiModelProperty(name = "Att_url",value = "附件地址")
    private String Att_url;

    public String getAtt_name() {
        return Att_name;
    }

    public void setAtt_name(String att_name) {
        Att_name = att_name;
    }

    public String getAtt_url() {
        return Att_url;
    }

    public void setAtt_url(String att_url) {
        Att_url = att_url;
    }
}
