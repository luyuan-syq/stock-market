package com.syq.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.bo.AccountBo;
import com.syq.biz.bo.RoleBo;
import com.syq.biz.domain.Role;
import com.syq.biz.domain.WzaxRight;
import com.syq.biz.service.IRoleService;
import com.syq.biz.service.ISecurityService;
import com.syq.biz.util.JsonUtil;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.RolePo;
import com.syq.pagination.helper.PaginationHelper;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger L = LoggerFactory.getLogger(RoleController.class);
  
  public static final String ROLE_LIST_PAGE = "/role/list";
  public static final String ROLE_ADD_PAGE = "/role/add";
  public static final String ROLE_ADD_NEW_PAGE = "/role/addNew";
  public static final String ROLE_EDIT_PAGE = "/role/edit";
  
  @Autowired
  private IRoleService roleService;
  @Autowired
  private ISecurityService securityService;
  
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(RolePo rolePo) {
    
    ModelAndView view = new ModelAndView(ROLE_LIST_PAGE);
    
    return view;
  }
  
  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(RolePo rolePo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    PaginationHelper.paginationHandle(rolePo, (IAdapterService<AccountBo>)roleService,view);
    view.addObject(rolePo);
    return view;
  }
  
  @RequestMapping(value = "/addBeforeURL", method = RequestMethod.GET)
  public ModelAndView addBeforeURL() {
    ModelAndView view = new ModelAndView(ROLE_ADD_PAGE);
    //添加之前业务逻辑处理
    return view;
  }
  
  @RequestMapping(value = "/addNewBeforeURL", method = RequestMethod.GET)
  public ModelAndView addNewBeforeURL(String code) {
    ModelAndView view = new ModelAndView(ROLE_ADD_NEW_PAGE);
    return view;
  }
  
  
  @RequestMapping(value = "/editBeforeUrl", method = RequestMethod.GET)
  public ModelAndView editBeforeUrl(Long id) {
    ModelAndView view = new ModelAndView(ROLE_EDIT_PAGE);
    //获取角色信息
    Role role = roleService.get(id);
    view.addObject("role", role);
    List<WzaxRight> rights = roleService.selectRightByRoleId(id);
    view.addObject("rights", JsonUtil.toJson(rights));
    return view;
  }
  
  @RequestMapping(value = "/loadModuleTree", method = RequestMethod.GET)
  public ModelAndView ajaxList() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try{
      view.addAllObjects(securityService.getRootModulesToJson());
    }catch(Exception e) {
      L.error("获取权限数据错误",e);
    }
    return view;
  }
  
  
  @RequestMapping(value = "/loadRoleTree", method = RequestMethod.GET)
  public ModelAndView loadRoleTree() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try{
      view.addAllObjects(roleService.getRolesToJson());
    }catch(Exception e) {
      L.error("获取角色数据错误",e);
    }
    return view;
  }
  
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView add(RoleBo roleBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    try{
      roleService.addRole(roleBo);
    }catch(Exception e) {
      L.error("添加角色失败",e);
    }
    return view;
  }
  
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ModelAndView edit(RoleBo roleBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    try{
      roleService.editRole(roleBo);
    }catch(Exception e) {
      L.error("添加角色失败",e);
    }
    return view;
  }
  
}
