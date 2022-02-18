package com.dhf.dh.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Allhandler implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(Allhandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果请求路径中包含js 直接放行
        log.debug("请求路径--->{}",request.getRequestURI());
        if (request.getRequestURI().indexOf("js")>=0){
            return true ;
        }
        //如果请求路径中包含 login 或者register 放行
        if (request.getRequestURI().indexOf("login")>=0 || request.getRequestURI().indexOf("register")>=0 || request.getRequestURI().indexOf("insuser")>=0
        ||request.getRequestURI().indexOf("favicon.ico")>=0 ){//|| request.getRequestURI().indexOf("selall")>=0
            if (request.getSession(false)!=null){
                request.getSession().invalidate();
            }
            return true ;
        }
        //如果有session 直接放行
        if (request.getSession(false)!=null){
            return true ;
        }
        //否则直接放回
        log.debug("拦截器---->拦截返回的地址:{}",request.getContextPath()+"login.html");
        response.sendRedirect(request.getContextPath()+"login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
