package com.syq.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.syq.biz.service.IRoleService;
import com.syq.biz.service.ISecurityService;
import com.syq.pagination.common.RolePo;
import com.syq.web.utils.LoginConstants;;

@Controller
public class IndexController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger L = LoggerFactory.getLogger(IndexController.class);

  public static final String INDEX_PAGE = "/index";
  
  public static final String ADMIN_MAIN_URL = "/passport/todoList?needCountAboutExprie=true";
  public static final String FAFF_MAIN_URL = "/passport/todoFaffList";
  
  @Autowired
  private IRoleService roleService;
  @Autowired
  private ISecurityService securityService;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public void index(RolePo rolePo,HttpServletRequest request,HttpServletResponse response) throws IOException {

    //判断登录方式
    Integer loginType = (Integer)request.getSession().getAttribute(LoginConstants.LOGIN_TYPE);
    if(1 == loginType) {
      response.sendRedirect(request.getContextPath()+ADMIN_MAIN_URL);
    }else{
      response.sendRedirect(request.getContextPath()+FAFF_MAIN_URL);
    }
    
  }


  @RequestMapping(value = "/", method = RequestMethod.GET)
  public void main(RolePo rolePo,HttpServletRequest request,HttpServletResponse response) throws IOException {

    response.sendRedirect(request.getContextPath()+"/index");
  }
}
