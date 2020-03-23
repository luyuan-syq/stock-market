package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE_IDISBLANK;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE_IDNOTEXIST;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.base.Strings;
import com.syq.biz.bo.ManagerBo;
import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.domain.Manager;
import com.syq.biz.service.IManagerService;
import com.syq.biz.service.ITaskCodeService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.ManagerPo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.web.annotation.Auth;
import com.syq.web.helper.ManagerHelper;
import com.syq.web.utils.LoginConstants;

@Controller
@RequestMapping("/manager")
public class ManagerController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(ManagerController.class);

  private static final String ADD_PAGE = "/manager/add";

  private static final String EDIT_PAGE = "/manager/edit";
  
  @Autowired
  private IManagerService managerService;
  
  @Autowired
  private ITaskCodeService taskCodeService;


  /**
   * 登陆
   */
  @Auth(verifyLogin = false, verifyURL = false)
  @RequestMapping("/login")
  public void login(String username, String password, HttpServletRequest request,
      HttpServletResponse response) throws IOException{
    HttpSession session = request.getSession();
    session.setAttribute(LoginConstants.LOGIN_TYPE, 1);
    
    if(Strings.isNullOrEmpty(username)) {
      session.setAttribute(LoginConstants.ERRORCODE, 1);
      response.sendRedirect(request.getContextPath()+"/login.jsp");
      return;
    }
    if(Strings.isNullOrEmpty(password)) {
      session.setAttribute(LoginConstants.ERRORCODE, 2);
      response.sendRedirect(request.getContextPath()+"/login.jsp");
      return;
    }
    try {

      Manager manager = managerService.findManagerByNameAndPass(username, password);
      if (manager == null) {
        session.setAttribute(LoginConstants.ERRORCODE, 7);
        response.sendRedirect(request.getContextPath()+"/login.jsp");
        return;
      }
      // 判断管理员的状态
      session.setAttribute(LoginConstants.SESSION_MANAGER, manager);
      // 验证通过,查询权限
    } catch (Exception e) {
      LOG.error("用户名/密码登录错误，原因",e);
      session.setAttribute(LoginConstants.ERRORCODE, "密码不能为空!");
      response.sendRedirect(request.getContextPath()+"/login.jsp");
      return;
    }
    response.sendRedirect(request.getContextPath()+"/index");
  }
  
  
  /**
   * 登陆
   */
  @Auth(verifyLogin = false, verifyURL = false)
  @RequestMapping("codeLogin")
  public void codeLogin(String code, HttpServletRequest request,
      HttpServletResponse response) throws IOException{
    
    HttpSession session = request.getSession();
    session.setAttribute(LoginConstants.LOGIN_TYPE, 2);
    try {
      
      if(Strings.isNullOrEmpty(code)) {
        session.setAttribute(LoginConstants.ERRORCODE, 8);
        response.sendRedirect(request.getContextPath()+"/login.jsp");
        return;
      }

      TaskCodeBo taskCodeBo = taskCodeService.findByCode(code);
      if (taskCodeBo == null) {
        session.setAttribute(LoginConstants.ERRORCODE, 9);
        response.sendRedirect(request.getContextPath()+"/login.jsp");
        return;
      }
      //TODO
      Manager manager = new Manager();
      manager.setName(code);
      // 判断管理员的状态
      session.setAttribute(LoginConstants.SESSION_MANAGER, manager);
      session.setAttribute(LoginConstants.SESSION_TASKCODE, code);
      
    } catch (Exception e) {
      session.setAttribute(LoginConstants.ERRORCODE, "任务编码登录异常!");
      response.sendRedirect(request.getContextPath()+"/login.jsp");
      return;
    }
    response.sendRedirect(request.getContextPath()+"/index");
  }
  
  /**
   * 登出
   */
  @Auth(verifyLogin = false, verifyURL = false)
  @RequestMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
    try {
      request.getSession().invalidate();
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    response.sendRedirect(request.getContextPath()+"/index");
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(ManagerPo managerPo) {

    ModelAndView view = new ModelAndView("/manager/list");

    return view;
  }

  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(ManagerPo managerPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    PaginationHelper.paginationHandle(managerPo, (IAdapterService<ManagerBo>) managerService, view);
    view.addObject(managerPo);
    return view;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView create(ManagerBo Managerbo) {
    LOG.debug("添加管理员");
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    ManagerHelper.forCreate(Managerbo);

    try {
      managerService.save(Managerbo);
    } catch (Exception e) {
      LOG.error("添加管理员错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }

    return view;
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ModelAndView modify(ManagerBo managerbo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    if (null == managerbo.getId() || 0 == managerbo.getId().intValue()) {
      LOG.warn("update manger failed,id not allowed null");
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE_IDISBLANK);
      return view;
    }
    try {

      ManagerBo managerBo = managerService.selectById(managerbo.getId());
      if (null == managerBo) {
        LOG.warn("can not find manager by id");
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE_IDNOTEXIST);
        return view;
      }
      managerService.update(managerbo);
    } catch (Exception e) {
      LOG.error("modify account error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE);
    }

    return view;
  }

  @RequestMapping(value = "/addBeforeURL", method = RequestMethod.GET)
  public ModelAndView addBeforeURL() {
    ModelAndView view = new ModelAndView(ADD_PAGE);
    // 添加之前业务逻辑处理
    return view;
  }

  @RequestMapping(value = "/editBeforeURL", method = RequestMethod.GET)
  public ModelAndView edotBeforeURL(Long id) {
    ModelAndView view = new ModelAndView(EDIT_PAGE);
    // 添加之前业务逻辑处理
    if (id != null && 0 != id.intValue()) {
      ManagerBo bo = managerService.selectById(id);
      view.addObject("manager", bo);
    }
    return view;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ModelAndView delete(Long[] id) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      managerService.deleteByIds(id);
    } catch (Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }

    return view;
  }
}
