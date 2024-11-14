package com.learn.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.entity.Auth;
import com.learn.entity.User;
import com.learn.exception.ServiceException;
import com.learn.service.AuthService;
import com.learn.util.TokenUtils;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        // 没有token,未登录
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        // 虚假token。
        if (!redisTemplate.hasKey(token)) {
            throw new ServiceException("token非法");
        }
        User currentUser = tokenUtils.getCurrentUser(token);
        List<Auth> auths = authService.listUserAuthById(currentUser.getUserId());
        List<SimpleGrantedAuthority> collect = auths.stream()
                .map(Auth::getAuthCode)
                .filter(StringUtils::isNotEmpty)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(currentUser,null,collect);
        // 认证信息存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request,response);
    }
}
