package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.GENERATE_TASK_CODE;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.service.ITaskCodeService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.AccountPo;
import com.syq.pagination.common.TaskCodePo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.web.helper.TaskCodeHelper;

/**
 * 编码
 * 
 * created by Shaoyuqi on 2016年10月2日 下午1:29:52
 */
@Controller
@RequestMapping("/taskCode")
public class TaskCodeController extends BaseController{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskCodeController.class);
  
  @Autowired
  private ITaskCodeService taskCodeService;
  
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(AccountPo accountPo) {
    
    ModelAndView view = new ModelAndView("/taskCode/list");
    
    return view;
  }
  
  @RequestMapping(value = "/ajaxList", method = RequestMethod.POST)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(TaskCodePo taskCodePo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    PaginationHelper.paginationHandle(taskCodePo, (IAdapterService<TaskCodeBo>)taskCodeService,view);
    view.addObject(taskCodePo);
    return view;
  }
  
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public ModelAndView add(int codeNum) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    List<TaskCodeBo> boes = TaskCodeHelper.generate(codeNum, getLoginName());
    try{
      taskCodeService.addBatch(boes);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, GENERATE_TASK_CODE);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/addForTask", method = RequestMethod.GET)
  public ModelAndView addForTask() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    List<TaskCodeBo> boes = TaskCodeHelper.generate(1, getLoginName());
    try{
      taskCodeService.addBatch(boes);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, GENERATE_TASK_CODE);
    }
    
    view.addObject("code", boes.get(0).getTaskCode());
    return view;
  }
  
}
