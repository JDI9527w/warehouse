package com.learn.handle;

import com.alibaba.fastjson2.JSON;
import com.learn.DTO.Result;
import com.learn.DTO.SysUser;
import com.learn.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        String token = tokenUtils.loginSign(sysUser, sysUser.getUser().getUserPwd());
        response.setContentType("Application/json;charset=utf-8");
        Result result = new Result();
        Result.ok("登录成功",token);
        PrintWriter writer = response.getWriter();
        String jsonString = JSON.toJSONString(result);
        writer.println(jsonString);
        writer.flush();
        writer.close();
    }
}
