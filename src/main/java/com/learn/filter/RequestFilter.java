package com.learn.filter;

import com.learn.DTO.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            RequestContext.setRequest((HttpServletRequest) request);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }
}
