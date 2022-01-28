package com.lzd.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Test实体类", description = "Test对象信息")
public class Test {
    @ApiModelProperty(dataType = "int", required = true)
    private Integer test;
}
