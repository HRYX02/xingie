package com.sxx.filter;

import com.alibaba.fastjson.JSON;
import com.sxx.common.BaseContext;
import com.sxx.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SxxStar
 * @description 过滤器，对localhost:8080/*的请求都进行过滤
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    /**
     * @description 路径匹配器
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 忽略需要过滤的请求
        String[] urls = new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
        };
        String requestURI = request.getRequestURI();
        if (check(urls,requestURI)) {
            //如果为true则放行
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employeeResult") != null){
            //如果有登录账户的id则将此id设置为进程id
            BaseContext.setCurrentId((Long)request.getSession().getAttribute("employeeResult"));
            log.info("用户已登录");
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * @description 检查请求路径是否在忽略的路径中，如果在返回true
     */
    public boolean check(String[] urls,String uri){
        for (String url: urls) {
            boolean match = PATH_MATCHER.match(url, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
