package com.syq.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.domain.MailInfo;
import com.syq.biz.service.IMailInfoService;
import com.syq.pagination.common.AccountPo;

@Controller
@RequestMapping("/mail")
public class MailInfoController extends BaseController{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(MailInfoController.class);
  
  @Autowired
  private IMailInfoService mailInfoService;
  
  @RequestMapping(value = "/view", method = RequestMethod.GET)
  public ModelAndView list(AccountPo accountPo) {
    
    ModelAndView view = new ModelAndView("/mail/view");
    
    MailInfo mailInfo = mailInfoService.select();
    view.addObject("mailInfo", mailInfo);
    
    return view;
  }
  
  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public ModelAndView info() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    
    
    return view;
  }
}
