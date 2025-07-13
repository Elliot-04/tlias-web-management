package com.itheima.filter;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Currency;

@Slf4j
@WebFilter(urlPatterns = "/*") // 拦截所有请求
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1.获取请求路径
        String requestURI = request.getRequestURI();

        // 2.判断是否是登录请求，如果是登录请求，直接放行
        if (requestURI.contains("/login")) {
            log.info("登录请求，直接放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 3.获取请求头中的token
        String token = request.getHeader("token");

        // 4.判断token是否存在，如果不存在 -> 返回401状态码
        if (token == null || token.isEmpty()) {
            log.warn("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401状态码
            return;
        }

        // 5.如果token存在，校验token，如果校验失败 -> 返回401状态码
        try {
            Claims claims = JwtUtils.parseJWT(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId); // 将员工ID存入ThreadLocal
            log.info("当前登录员工ID: {}，将其存入ThreadLocal", empId);
        } catch (Exception e) {
            log.warn("令牌非法，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401状态码
            return;
        }

        // 6.校验通过，放行请求
        log.info("令牌合法，放行请求");
        filterChain.doFilter(servletRequest, servletResponse);

        // 7.清理ThreadLocal中的数据
        CurrentHolder.remove(); // 清理ThreadLocal中的数据
    }
}
