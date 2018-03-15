package com.dl.shop.auth.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * token 失效DTO
 *
 * @create 2017/12/19 19:23
 **/
@Data
public class InvalidateTokenDTO implements Serializable {

    private static final long serialVersionUID = -7075786119892078735L;

    @ApiModelProperty("token 字符串")
    private String token;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("失效类型, 1、用户当前会话 2、用户全部会话")
    private Integer invalidateType;


}
