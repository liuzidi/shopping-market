package com.lzd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "响应VO",description = "响应码-响应信息-数据信息")
public class ResultVO {
    @ApiModelProperty(dataType = "int",required = true)
    private int code;
    @ApiModelProperty(dataType = "string",required = true)
    private String msg;
    @ApiModelProperty(dataType = "object",required = true)
    private Object data;
}
