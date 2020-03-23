/**
 * com.syq.web.controller.PassportController.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:03:39
 */
package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.AUDIT_PASSPORT_INFO;
import static com.syq.web.common.WebConst.ERROR_MSG;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.bo.PassportBo;
import com.syq.biz.service.IRenewalPassportService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PassportPo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.util.constant.RenewalPassportStatus;
import com.syq.web.utils.LoginConstants;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:03:39
 */
@Controller
@RequestMapping("/renewalPassport")
public class RenewalPassportController extends BaseController{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(RenewalPassportController.class);
  public static final String VIEW_PAGE = "/passport/view";
  public static final String APPLY_PASSPORT_INFO = "/passport/applyPassportInfo";
  
  @Autowired
  private IRenewalPassportService passportService;
  
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(PassportPo passportPo, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/passport/renewalList");
    
    // 任务模式登录
    Object obj = request.getSession().getAttribute(LoginConstants.SESSION_TASKCODE);
    if ( obj!= null){
      view.addObject("taskCode", obj);
    }
    
    return view;
  }
     
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  public ModelAndView ajaxList(HttpServletRequest request,PassportPo passportPo,String type) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
        
    if (passportPo != null){
      passportPo.buildAboutExpiredTime();
    }
    
    PaginationHelper.paginationHandle(passportPo, (IAdapterService<PassportBo>)passportService,view);
    
    //统计数量
    return view;
  }
        
  @RequestMapping(value = "/operateStatus", method = RequestMethod.POST)
  public ModelAndView operateStatus(Long passportId, int flowStatus) {
    
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    RenewalPassportStatus status = RenewalPassportStatus.from(flowStatus);
    if(passportId == null || status == null) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(ERROR_MSG, "参数不合法");
      return view;
    }
   
    try{
      passportService.updatePassportStatus(passportId, status);
    }catch(Exception e) {
      LOG.error("operateStatus error ",e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, AUDIT_PASSPORT_INFO);
    }
    
    return view;
  }
  
}
