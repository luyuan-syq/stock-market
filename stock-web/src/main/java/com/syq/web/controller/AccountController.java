package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE_IDISBLANK;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE_IDNOTEXIST;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.bo.AccountBo;
import com.syq.biz.service.IAccountService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.AccountPo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.web.helper.AccountHelper;
import com.syq.web.vo.AccountVo;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(AccountController.class);
  
  private static final String ADD_PAGE = "/account/add";
  
  private static final String EDIT_PAGE = "/account/edit";
  
  @Autowired
  private IAccountService accountService;
  
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(AccountPo accountPo) {
    
    ModelAndView view = new ModelAndView("/account/list");
    
    return view;
  }
  
  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(AccountPo accountPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    PaginationHelper.paginationHandle(accountPo, (IAdapterService<AccountBo>)accountService,view);
    view.addObject(accountPo);
    return view;
  }
  
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView create(AccountVo accountVo) {
    LOG.debug("添加外事人员");
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    AccountBo bo = AccountHelper.accountVoToBoForCreate(accountVo,getManager());
    view.addObject(SUCCESS_KEY, true);
    
    try{
      accountService.save(bo);
    }catch(Exception e) {
      LOG.error("添加外事人员错误",e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ModelAndView modify(AccountVo accountVo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    if(null == accountVo.getId() || 0 == accountVo.getId().intValue()) {
      LOG.warn("update account failed,id not allowed null");
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE_IDISBLANK);
      return view;
    }
    try{
      
      AccountBo accountBo = accountService.selectById(accountVo.getId());
      if(null == accountBo) {
        LOG.warn("can not find account by id");
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE_IDNOTEXIST);
        return view;
      }
      AccountBo bo = AccountHelper.accountVoToBoForUpdate(accountVo);
      accountService.update(bo);
    }catch(Exception e) {
      LOG.error("modify account error ",e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/addBeforeURL", method = RequestMethod.GET)
  public ModelAndView addBeforeURL() {
    ModelAndView view = new ModelAndView(ADD_PAGE);
    //添加之前业务逻辑处理
    return view;
  }
  
  @RequestMapping(value = "/editBeforeURL", method = RequestMethod.GET)
  public ModelAndView edotBeforeURL(Long id) {
    ModelAndView view = new ModelAndView(EDIT_PAGE);
    //添加之前业务逻辑处理
    if(id != null && 0 != id.intValue()) {
      AccountBo bo = accountService.selectById(id);
      view.addObject("account", bo);
    }
    return view;
  }
  
  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ModelAndView delete(Long[] id) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    try{
      accountService.deleteByIds(id);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    
    return view;
  }
  
  
}
