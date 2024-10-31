package com.learn.DTO;

import javax.servlet.http.HttpServletRequest;

public class RequestContext {
    private static final ThreadLocal<HttpServletRequest> REQUEST_HOLDER = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        REQUEST_HOLDER.set(request);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_HOLDER.get();
    }

    public static void clear() {
        REQUEST_HOLDER.remove();
    }
}
