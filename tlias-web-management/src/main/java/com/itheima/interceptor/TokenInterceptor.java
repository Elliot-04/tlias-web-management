package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 令牌校验拦截器
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*// 1.获取请求路径
        String requestURI = request.getRequestURI();

        // 2.判断是否是登录请求，如果是登录请求，直接放行
        if (requestURI.contains("/login")) {
            log.info("登录请求，直接放行");
            return true;
        }*/

        // 3.获取请求头中的token
        String token = request.getHeader("token");

        // 4.判断token是否存在，如果不存在 -> 返回401状态码
        if (token == null || token.isEmpty()) {
            log.warn("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401状态码
            return false;
        }

        // 5.如果token存在，校验token，如果校验失败 -> 返回401状态码
        try {
            JwtUtils.parseJWT(token);
        } catch (Exception e) {
            log.warn("令牌非法，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401状态码
            return false;
        }

        // 6.校验通过，放行请求
        log.info("令牌合法，放行请求");
        return true;
    }
}
