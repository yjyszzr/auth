package com.dl.shop.auth.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dl.base.result.BaseResult;
import com.dl.shop.auth.dto.AuthInfoDTO;
import com.dl.shop.auth.dto.InvalidateTokenDTO;

/**
 * 鉴权远程接口定义
 *
 * @create 2017/12/19 19:33
 **/
@FeignClient("auth-service")
public interface IAuthService {
    /**
     * 生成token信息
     *
     * @param authInfoDTO 用户登陆信息
     * @return
     */
    @PostMapping("/auth/genToken")
    BaseResult<String> genToken(@RequestBody AuthInfoDTO authInfoDTO);

    /**
     * token 失效接口
     *
     * @param invalidateTokenDTO token信息
     * @return 结果
     */
    @PostMapping("/auth/invalidate")
    BaseResult<Integer> invalidate(@RequestBody InvalidateTokenDTO invalidateTokenDTO);

    @PostMapping("/auth/getUserIdByToken")
    public BaseResult<Integer> getUserIdByToken(@RequestBody InvalidateTokenDTO invalidateTokenDTO);
}
