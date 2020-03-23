package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.ErrorCode.PERSON_DELETE;
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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.ITaskService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PersonPo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.util.constant.VisaStatus;
import com.syq.web.helper.PersonHelper;

/**
 * 出国人员
 * 
 * created by Shaoyuqi on 2017年1月12日 上午1:50:59
 */
@Controller
@RequestMapping("/person")
public class PersonController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PersonController.class);


  @Autowired
  private IPersonService personService;
  
  @Autowired
  private ITaskService taskService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(PersonPo personPo) {
    ModelAndView view = new ModelAndView("/person/list");
    if (personPo != null && personPo.getTaskId() != null){
       view.addObject("taskId", personPo.getTaskId());  
    }
    return view;
  }

  @RequestMapping(value = "/ajaxListToTask/", method = RequestMethod.GET)
  public ModelAndView ajaxList(Long taskId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    // TODO 代码待优化
    List<AuditPersonBo> list = personService.getPersonsByTaskId(taskId);
    view.addObject("rows", list);
    
    return view;
  }
  
  @RequestMapping(value = "/ajaxListToVisa/", method = RequestMethod.GET)
  public ModelAndView ajaxList(int status) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    
    VisaStatus visaStatus = VisaStatus.from(status);
    if (visaStatus == null){
      LOG.info("invail status param {}", status);
      view.addObject("rows", Lists.newArrayList());
      return view;
    }
    
    List<AuditPersonBo> list = personService.getPersonsByVisaStatus(visaStatus);
    view.addObject("rows", list);
    
    return view;
  }
  
  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(PersonPo personPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    PaginationHelper.paginationHandle(personPo, (IAdapterService<PersonBo>) personService, view);
    view.addObject(personPo);
    return view;
  }


  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView create(PersonBo personBo, Long taskId) {
    LOG.debug("添加出国人员");

    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    view.addObject(SUCCESS_KEY, true);

    if (Strings.isNullOrEmpty(personBo.getIdNumber())) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "身份证号不能为空");
      return view;
    }
    
//    if (personService.isPersonHasRelateTask(personBo.getIdNumber())){
//      view.addObject(SUCCESS_KEY, false);
//      view.addObject(MESSAGE_KEY, "当前身份证所属人员已绑定其他外事任务");
//      return view;
//    }

    PersonHelper.forCreate(personBo);

    try {
      personService.save(personBo, taskId);
    } catch (Exception e) {
      LOG.error("添加出国人员错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
      return view;
    }

    return view;
  }


  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ModelAndView create(Long personId, Long taskId) {
    LOG.debug("删除出差人员");
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      personService.deleteById(personId, taskId);
    } catch (Exception e) {
      LOG.error("删除出差人员错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, PERSON_DELETE);
    }

    return view;
  }
}
