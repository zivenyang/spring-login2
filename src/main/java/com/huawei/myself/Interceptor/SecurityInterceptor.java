package com.huawei.myself.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.huawei.myself.consts.SecurityConst.SESSION_KEY;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SESSION_KEY) != null) {
            System.out.println(SESSION_KEY);
            return true;
        }

        String url = "/login";
        response.sendRedirect(url);
        return false;
    }
}
