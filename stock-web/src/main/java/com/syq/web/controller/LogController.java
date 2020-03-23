package com.syq.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.bo.AccountBo;
import com.syq.biz.service.ILogService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.AccountPo;
import com.syq.pagination.common.LogPo;
import com.syq.pagination.helper.PaginationHelper;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(LogController.class);
  
  @Autowired
  private ILogService logService;
  
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(AccountPo accountPo) {
    
    ModelAndView view = new ModelAndView("/log/list");
    
    return view;
  }
  
  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(LogPo logPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    PaginationHelper.paginationHandle(logPo, (IAdapterService<AccountBo>)logService,view);
    view.addObject(logPo);
    return view;
  }
}
