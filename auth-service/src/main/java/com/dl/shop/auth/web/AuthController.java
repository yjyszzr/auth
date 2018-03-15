package com.dl.shop.auth.web;

import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.shop.auth.dto.InvalidateTokenDTO;
import com.dl.shop.auth.dto.AuthInfoDTO;
import com.dl.shop.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 鉴权接口
 *
 * @create 2017/12/19 18:26
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    /**
     * 生成token
     *
     * @param authInfoDTO 登陆信息
     * @return
     */
    @PostMapping("/genToken")
    @ApiOperation("生成token信息")
    public BaseResult<String> genToken(@RequestBody AuthInfoDTO authInfoDTO) {
        return ResultGenerator.genSuccessResult("", authService.genToken(authInfoDTO));
    }

    @PostMapping("/invalidate")
    @ApiOperation("用户token失效")
    public BaseResult<Integer> invalidate(@RequestBody InvalidateTokenDTO invalidateTokenDTO) {
        authService.invalidate(invalidateTokenDTO);
        return ResultGenerator.genSuccessResult("");
    }
}
