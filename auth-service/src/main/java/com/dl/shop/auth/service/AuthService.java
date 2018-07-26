package com.dl.shop.auth.service;

import com.dl.base.util.jwt.IJWTInfo;
import com.dl.base.util.jwt.JWTHelper;
import com.dl.base.util.jwt.JWTInfo;
import com.dl.shop.auth.dto.AuthInfoDTO;
import com.dl.shop.auth.dto.InvalidateTokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权服务
 *
 * @create 2017/12/19 18:26
 **/
@Slf4j
@Service
public class AuthService {

    private final static String USER_SESSION_PREFIX = "US:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.expire:360000}")
    private int expire;
    @Value("${jwt.pri-key.path:pri.key}")
    private String priKeyPath;
    @Value("${jwt.pub-key.path:pub.key}")
    private String pubKeyPath;

    /**
     * 生成token
     *
     * @param authInfoDTO 用户登陆信息
     * @return token字符串
     */
    public String genToken(AuthInfoDTO authInfoDTO) {
        String id = authInfoDTO.getLoginType() + UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(USER_SESSION_PREFIX + authInfoDTO.getUserId(), id, "" + System.currentTimeMillis());
        stringRedisTemplate.expire(USER_SESSION_PREFIX + authInfoDTO.getUserId(), 60*60*24*5, TimeUnit.SECONDS);
        try {
            return JWTHelper.generateToken(new JWTInfo(id, "" + authInfoDTO.getUserId()), priKeyPath);
        } catch (Exception e) {
            log.error("token 生成失败，参数为：" + authInfoDTO, e);
            return null;
        }
    }

    /**
     * 用户推出登陆
     *
     * @param invalidateTokenDTO 请求信息
     */
    public void invalidate(InvalidateTokenDTO invalidateTokenDTO) {
        try {
            if (null == invalidateTokenDTO.getInvalidateType()) {
                invalidateTokenDTO.setInvalidateType(1);
            }
            switch (invalidateTokenDTO.getInvalidateType()) {
                case 2: {
                    stringRedisTemplate.delete(USER_SESSION_PREFIX + invalidateTokenDTO.getUserId());
                } break;
                default: {
                    IJWTInfo info = JWTHelper.getInfoFromToken(invalidateTokenDTO.getToken(), pubKeyPath);
                    stringRedisTemplate.opsForHash().delete(USER_SESSION_PREFIX + info.getUserId(), info.getUnique());
                }
            }
        } catch (Exception e) {
            log.error("token 失效失败，请求信息为：" + invalidateTokenDTO, e);
        }

    }


}
