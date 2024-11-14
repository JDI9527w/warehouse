package com.learn.filter;

/*import com.alibaba.fastjson2.JSON;
import com.learn.DTO.Result;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginFilter implements Filter {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();
        List<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        urlList.add("/product/img-upload");
        String url = request.getServletPath();
        if (urlList.contains(servletPath) || url.contains("/img/upload")) {
            chain.doFilter(req, resp);
            return;
        }
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if (StringUtils.hasText(token) && redisTemplate.hasKey(token)) {
            chain.doFilter(req, resp);
            return;
        }
        Result err = Result.err(Result.CODE_ERR_UNLOGINED, "请登陆后再试.");
        String jsonString = JSON.toJSONString(err);
        response.setContentType("Application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}*/
