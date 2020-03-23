package com.syq.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.syq.web.annotation.Auth;
import com.syq.web.utils.LoginConstants;

public class AuthInterceptor extends HandlerInterceptorAdapter {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod method = (HandlerMethod) handler;
    Auth auth = method.getMethod().getAnnotation(Auth.class);
    if (auth == null || auth.verifyLogin()) {
      // 如果session存在则放过请求
      if (request.getSession().getAttribute(LoginConstants.SESSION_MANAGER) != null) {
        return true;
      }
      response.setStatus(HttpStatus.FOUND.value());
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return false;

    }
    // 验证权限,待实现
    if (auth == null || auth.verifyURL()) {
    }
    return true;

  }

}
