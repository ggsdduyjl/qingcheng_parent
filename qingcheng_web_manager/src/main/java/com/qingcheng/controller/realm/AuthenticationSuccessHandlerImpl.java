package com.qingcheng.controller.realm;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.system.LoginLog;
import com.qingcheng.service.system.LoginLogService;
import com.qingcheng.utils.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 登录成功会进入此类
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Reference
    private LoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println(authentication.getName() + "登录成功...");
        // 记录登录日志
        LoginLog log = new LoginLog();
        log.setLoginTime(new Date());
        log.setLoginName(authentication.getName());
        log.setIp(httpServletRequest.getRemoteAddr());
        log.setLocation(WebUtil.getCityByIP(httpServletRequest.getRemoteAddr()));
        log.setBrowserName(WebUtil.getBrowserName(httpServletRequest.getHeader("user-agent")));
        loginLogService.add(log);
        // 重定向到首页
        httpServletResponse.sendRedirect("/main.html");
    }
}
