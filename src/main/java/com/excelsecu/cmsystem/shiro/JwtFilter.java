package com.excelsecu.cmsystem.shiro;

import com.excelsecu.cmsystem.common.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends AccessControlFilter {

    // 设置是否有权限访问
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 拦截用户认证信息 - 子类必须实现的方法
     * ShiroConfig配置-流程JwtFilter拦截->JwtRealm中进行校验
     * */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        log.info("servlet request path: {}", req.getServletPath());

        // 过滤headers头是否有Authorization
        String authorization = req.getHeader(Constants.AUTHORIZATION_HEADER);
        if (authorization != null) {
            JwtToken token = new JwtToken(authorization);
            /*
             * 进行登录，在JwtRealm中进行校验
             * 用户登录的时候已经生成一个token并保存到了redis
             * 调用login(token)触发doGetAuthenticationInfo会从redis取出token做一下登录校验
             * */
            getSubject(request, response).login(token);
            return true;
        }

        onLoginFailed(response);
        return false;
    }

    // 登录认证失败，回响应
    private void onLoginFailed(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=utf-8");
    }

}
