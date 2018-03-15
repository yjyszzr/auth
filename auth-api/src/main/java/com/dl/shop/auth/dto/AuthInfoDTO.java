package com.dl.shop.auth.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登陆DTO
 *
 * @create 2017/12/19 18:39
 **/
@Data
public class AuthInfoDTO implements Serializable {
    private static final long serialVersionUID = 4532551627996940724L;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("登陆类型")
    private Integer loginType;

}
