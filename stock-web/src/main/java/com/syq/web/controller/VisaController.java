package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE;
import static com.syq.web.common.ErrorCode.COUNTRY_ADD;
import static com.syq.web.common.ErrorCode.COUNTRY_DELETE;
import static com.syq.web.common.ErrorCode.COUNTRY_UPDATE;
import static com.syq.web.common.WebConst.ERROR_MSG;
import static com.syq.web.common.WebConst.ERROR_PAGE;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.base.Strings;
import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.ExpressDeliveryBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.VisaAttributeBo;
import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.bo.VisaPrincipalBo;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.ITaskService;
import com.syq.biz.service.IVisaCountryService;
import com.syq.biz.service.IVisaService;
import com.syq.pagination.common.AccountPo;
import com.syq.pagination.common.TaskPo;
import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.PrincipalField;
import com.syq.util.constant.PassportStatus;
import com.syq.util.constant.TaskStatus;
import com.syq.util.constant.VisaStatus;
import com.syq.visa.PrincipalFieldValueBean;
import com.syq.web.common.WebConst;
import com.syq.web.helper.FieldValidateHelper;
import com.syq.web.helper.VisaCountryHelper;
import com.syq.web.utils.LoginConstants;

@Controller
@RequestMapping("/visa")
public class VisaController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaController.class);

  @Autowired
  private IVisaCountryService visaCountryService;
  @Autowired
  private ITaskService taskService;
  @Autowired
  private IPersonService personService;

  private static final String EDIT_COUNTRY_BEFORE = "/visa/country/edit";
  private static final String APPLY_VISA_PAGE = "/visa/manager/beforeApply";
  private static final String VISA_STATUS_LIST_PAGE = "/visa/manager/statusList";
  private static final String DELIVERY_PAGE = "/visa/manager/expressDeliveryInfo";



  @Autowired
  private IVisaService visaService;
  
  @Autowired
  private IPassportService passportService;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public ModelAndView list(AccountPo accountPo) {

    ModelAndView view = new ModelAndView("/visa/index");

    return view;
  }

  @RequestMapping(value = "/ajaxCountryTree", method = RequestMethod.GET)
  public ModelAndView ajaxList() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    List<VisaCountryBo> boes = visaCountryService.selectAll();
    Map<String, Object> boMap = VisaCountryHelper.visaCountryBoToMap(boes);

    view.addAllObjects(boMap);
    return view;
  }


  @RequestMapping(value = "/countryIsNotExit", method = RequestMethod.GET)
  @ResponseBody
  public boolean countryIsNotExit(String code) {
    VisaCountryBo bo = visaCountryService.selectByCode(code);
    return bo == null;
  }

  @RequestMapping(value = "/addCountry", method = RequestMethod.POST)
  public ModelAndView addCountry(VisaCountryBo bo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      if (!visaCountryService.save(bo)) {
        view.addObject(SUCCESS_KEY, false);
      }
    } catch (Exception e) {
      LOG.error("添加国家错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, COUNTRY_ADD);
    }

    return view;
  }

  @RequestMapping(value = "/deleteCountry", method = RequestMethod.GET)
  public ModelAndView deleteCountry(long id) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      if (!visaCountryService.deleteById(id)) {
        view.addObject(SUCCESS_KEY, false);
      }
    } catch (Exception e) {
      LOG.error("删除国家错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, COUNTRY_DELETE);
    }

    return view;
  }

  @RequestMapping(value = "/editCountryBefore", method = RequestMethod.GET)
  public ModelAndView editCountryBefore(long id) {
    ModelAndView view = new ModelAndView(ERROR_PAGE);

    try {
      VisaCountryBo bo = visaCountryService.selectById(id);
      if (bo != null) {
        view.addObject("country", bo);
        view.setViewName(EDIT_COUNTRY_BEFORE);
      }
    } catch (Exception e) {
      LOG.error("查询国家错误", e);
    }

    return view;
  }

  @RequestMapping(value = "/editCountry", method = RequestMethod.POST)
  public ModelAndView editCountry(VisaCountryBo bo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      if (!visaCountryService.update(bo)) {
        view.addObject(SUCCESS_KEY, false);
      }
    } catch (Exception e) {
      LOG.error("修改国家错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, COUNTRY_UPDATE);
    }

    return view;
  }
  
  
  @RequestMapping(value = "/applyVisaInfo", method = RequestMethod.GET)
  public ModelAndView applyVisaInfo(Long visaPrincipalId,HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/visa/manager/applyVisaInfo");
    HttpSession session = request.getSession();

    String taskCode = (String) session.getAttribute(LoginConstants.SESSION_TASKCODE);
    if (Strings.isNullOrEmpty(taskCode)) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
      return view;
    }
    try {
      
      VisaPrincipalBo visaPrincipalBo = visaService.getVisaPrincipal(visaPrincipalId);

      TaskBo taskBo = taskService.selectByCode(taskCode);
      if (null == taskBo) {
        LOG.warn("can not find task by Code");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据taskCode没有找到符合条件的任务");
        return view;
      }
      
      PersonBo  personBo = personService.selectById(visaPrincipalBo.getPersonId());
      if(null == personBo) {
        LOG.warn("can not find Person by personId");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据personId没有找到符合条件的出差人员");
        return view;
      }
      String taskCountryCodes = visaPrincipalBo.getTemplateCode();
      List<VisaCountryBo> countrys = visaCountryService.queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));
      
      visaService.assertVisaPrincipal(visaPrincipalBo.getPersonId(),taskBo.getId(),Arrays.asList(taskCountryCodes.split(",")));

      view.addObject("person", personBo);
      view.addObject("task", taskBo);
      view.addObject("countrys", countrys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "申请签证资料错误：" + e.getMessage());
    }
    return view;
  }


  @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
  public ModelAndView getInfo(long id,String code,long taskId) {

    ModelAndView view = new ModelAndView("/visa/manager/visaInfo");

    Map userFirstDisplayMsg = new HashMap();
    List userCustomMsg;
    List userSysMsg;
    String msg;
    VisaPrincipalBo principal = null;
    try {
      
      principal = visaService.getVisaPrincipal(id,taskId,code);
      if(principal == null) {
        view.addObject(ERROR_MSG, "VisaPrincipal记录不存在");
        view.addObject(SUCCESS_KEY, false);
        return view;
      }
      // 获取系统用户属性
      // List<FieldCategory> sysFieldCategorys = visaService.getSysFieldCategorys();
      // 获取自定义属性
      List<FieldCategory> fieldCategorys = visaService.getFieldCategorys(code);


      // replaceFieldCategorysByValue(principal, sysFieldCategorys, true);
      replaceFieldCategorysByValue(principal, fieldCategorys, false);

      userCustomMsg = fieldCategorys;
      // userSysMsg = sysFieldCategorys;

    } catch (Exception e) {
      LOG.error(e.getMessage(), e, new String[0]);
      return view;
    }
    view.addObject("firstDisplayMsg", userFirstDisplayMsg);
    // view.addObject("systemMsg", userSysMsg);
    view.addObject("customMsg", userCustomMsg);
    view.addObject("personId", id);
    view.addObject("code", code);
    view.addObject("principalId", principal.getId());
    view.addObject("principal", principal);

    return view;
  }

  private void replaceFieldCategorysByValue(VisaPrincipalBo principal,
      List<FieldCategory> fieldCategorys, boolean systemField) throws Exception {
    if(principal == null) {
      return;
    }
    FieldCategory fieldCategory = null;
    for (int i = 0; i < fieldCategorys.size(); i++) {
      fieldCategory = fieldCategorys.get(i);
      List<PrincipalField> principalFields = fieldCategory.getFields();
      for (PrincipalField pf : principalFields) {
         VisaAttributeBo visaAttributeBo = principal.getAttribute(fieldCategory.getId()+"_"+pf.getId());
         if(visaAttributeBo != null) {
           String pfFieldValue =  visaAttributeBo.getAttrValue();
           if (pfFieldValue != null) {
             visaService.replaceDefaultValueWithUserValue(pf, pfFieldValue);
           }
         }
         
      }

    }
  }

  /**
   * 获取任务，并获取出差人员
   * 
   * @param taskCode
   * @param request
   * @return created by Shaoyuqi on 2017年2月12日 上午10:49:05
   */
  @RequestMapping(value = "/beforeApplyVisa", method = RequestMethod.GET)
  public ModelAndView beforeApplyVisa(String taskCode, int none,HttpServletRequest request) {
    ModelAndView view = new ModelAndView(APPLY_VISA_PAGE);
    HttpSession session = request.getSession();

    taskCode = (String) session.getAttribute(LoginConstants.SESSION_TASKCODE);
    if (Strings.isNullOrEmpty(taskCode)) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
      return view;
    }
    try {

      TaskBo taskBo = taskService.selectByCode(taskCode);
      if (null == taskBo) {
        LOG.warn("can not find task by Code");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据taskCode没有找到符合条件的任务");
        return view;
      }
      String taskCountryCodes = taskBo.getTaskCountry();
      taskBo.setCountrys(getVisaCountrys(taskCountryCodes, visaCountryService));
      taskBo.setTaskBeginTimeStr(dateToString(taskBo.getTaskBeginTime()));
      taskBo.setTaskEndTimeStr(dateToString(taskBo.getTaskEndTime()));
      // 获取出差人员
      List<AuditPersonBo> persons = personService.getPersonsByTaskId(taskBo.getId());
      view.addObject("persons", persons);
      view.addObject("task", taskBo);
      //为了展示页面效果
      view.addObject("none",none);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "外事人员查看任务错误：" + e.getMessage());
    }

    return view;
  }


  /**
   * 更新签证资料息AJAX
   * 
   * @return
   */
  @RequestMapping(value = "/applyVisaInfoAJAX", method = RequestMethod.POST)
  public ModelAndView applyVisaInfoAJAX(HttpServletRequest request, HttpServletResponse response,String code,long visaPrincipalId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try{
      applyVisaInfo0(request,code);
      //修改状态
      visaService.updateVisaStatus(visaPrincipalId, VisaStatus.AUDIT_WAIT);
      view.addObject(SUCCESS_KEY, true);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "异步提交签证资料错误!!");
    }
    return view;
  }
  
  @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
  public ModelAndView updateStatus(HttpServletRequest request, HttpServletResponse response,int status,long visaPrincipalId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try{
      //修改状态
      visaService.updateVisaStatus(visaPrincipalId, VisaStatus.from(status));
      view.addObject(SUCCESS_KEY, true);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "修改签证状态成功!!");
    }
    return view;
  }
  
  
  @RequestMapping(value = "/taskListForAuditVisa", method = RequestMethod.GET)
  public ModelAndView taskListForAuditVisa() {
    ModelAndView view = new ModelAndView("/visa/manager/auditTasklist");

    return view;

  }
  
  
  @RequestMapping(value = "/taskListForSigned", method = RequestMethod.GET)
  public ModelAndView taskListForSigned() {
    ModelAndView view = new ModelAndView("/visa/manager/signedTasklist");

    return view;

  }
  
  @RequestMapping(value = "/auditTaskAjaxList", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView auditTaskAjaxList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    taskPo.setFlowStatus(VisaStatus.AUDIT_WAIT.getCode());
    if(taskPo.getPageNo() == null || taskPo.getPageNo() < 0) {
      taskPo.setPageNo(1);
    }
    
    Integer total = visaService.getTaskCount(taskPo);
    Integer totalPage = (total + taskPo.getMaxResults() - 1) / taskPo.getMaxResults();
    
    if(totalPage > 0 && taskPo.getPageNo() > totalPage) {
      taskPo.setPageNo(totalPage);
    }
    
    
    view.addObject("total", total);
    view.addObject("pageNo", taskPo.getPageNo());
    view.addObject("rows", visaService.selectTaskByCondition(taskPo));
    view.addObject("limit", taskPo.getMaxResults());
    view.addObject("start", taskPo.getStart());
    view.addObject(taskPo);
    return view;
  }
  
  
  @RequestMapping(value = "/signedTaskAjaxList", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView signedTaskAjaxList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    if(taskPo.getPageNo() == null || taskPo.getPageNo() < 0) {
      taskPo.setPageNo(1);
    }
    
//    taskPo.setSearchStatus(VisaStatus.getValuesList());
    Integer total = visaService.getVisaPrincipalCount(taskPo);
    Integer totalPage = (total + taskPo.getMaxResults() - 1) / taskPo.getMaxResults();
    
    if(totalPage > 0 && taskPo.getPageNo() > totalPage) {
      taskPo.setPageNo(totalPage);
    }
    
    
    view.addObject("total", total);
    view.addObject("pageNo", taskPo.getPageNo());
    view.addObject("rows", visaService.selectVisaPrincipalByCondition(taskPo));
    view.addObject("limit", taskPo.getMaxResults());
    view.addObject("start", taskPo.getStart());
    view.addObject(taskPo);
    //统计数量
    return view;
  }
  
  
  @RequestMapping(value = "/personListForAuditVisa", method = RequestMethod.GET)
  public ModelAndView personListForAuditVisa(Long taskId) {
    ModelAndView view = new ModelAndView("/visa/manager/auditPersonList");
    
    view.addObject("taskId", taskId);
    return view;

  }


  /**
   * 设置签证资料
   * 
   * @return
   */
  public String applyVisaInfo0(HttpServletRequest request,String code) {
    String visaPrincipalId;
    try {
      visaPrincipalId = request.getParameter("visaPrincipalId");
      VisaPrincipalBo visaPrincipalBo = visaService.getById(Long.parseLong(visaPrincipalId));
      String ip = null;
      StringBuilder validatePass = new StringBuilder();
      List<PrincipalFieldValueBean> principalFieldValueBeans =
          validateAllValue(request, validatePass,code);
      // 更新实体bean
      visaService.uploadVisaAttribute(visaPrincipalBo, principalFieldValueBeans);
      // 更新数据库
      // visaService.updateVisaPrincipal(visaPrincipalBo);

      return "personalInfoJSP";
    } catch (Exception e) {
      String msg = e.getMessage();
      LOG.error(msg, e, new String[0]);
      return "personalInfoJSP";
    }

  }
  
  /**
   * 审核签证
   * @param personId
   * @param request
   * @return
   * created by Shaoyuqi on 2017年3月4日 下午7:02:31
   */
  @RequestMapping(value = "/beforeAuditVisaInfo", method = RequestMethod.GET)
  public ModelAndView beforeAuditVisaInfo(Long taskId,Long personId,HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/visa/manager/auditVisaInfo");

    try {

      TaskBo taskBo = taskService.selectById(taskId);
      if (null == taskBo) {
        LOG.warn("can not find task by Code");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据taskCode没有找到符合条件的任务");
        return view;
      }
      
      PersonBo  personBo = personService.selectById(personId);
      if(null == personBo) {
        LOG.warn("can not find Person by personId");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据personId没有找到符合条件的出差人员");
        return view;
      }
      String taskCountryCodes = taskBo.getTaskCountry();
      List<VisaCountryBo> countrys = visaCountryService.queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));

      view.addObject("person", personBo);
      view.addObject("task", taskBo);
      view.addObject("countrys", countrys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "申请签证资料错误：" + e.getMessage());
    }
    return view;
  }


  @SuppressWarnings("all")
  private List<PrincipalFieldValueBean> validateAllValue(HttpServletRequest request,
      StringBuilder validatePass,String code) throws Exception {
    List principalFieldValueBeans = new ArrayList();
    String validateErrorMsg = null;
    List<FieldCategory> fieldCategorys = visaService.getFieldCategorys(code);
    if (fieldCategorys == null)
      throw new Exception("签证模板自定义字段为空");
    try {
      principalFieldValueBeans.addAll(validateFieldCategory(request, fieldCategorys, false));
    } catch (Exception e) {
      validateErrorMsg = e.getMessage();
    }
    validatePass.append(validateErrorMsg);
    return principalFieldValueBeans;
  }

  @SuppressWarnings("all")
  private List<PrincipalFieldValueBean> validateFieldCategory(HttpServletRequest request,
      List<FieldCategory> fieldCategorys, boolean isSystem) throws Exception {
    List principalFieldValueBeans = new ArrayList();
    StringBuilder validateErrorMsg = new StringBuilder();
    for (int categoryIndex = 0; categoryIndex < fieldCategorys.size(); categoryIndex++) {
      FieldCategory fc = fieldCategorys.get(categoryIndex);
      if (fc != null) {
        List pfs = fc.getFields();
        if (pfs != null) {
          PrincipalField principalField = null;
          for (int fieldIndex = 0; fieldIndex < pfs.size(); fieldIndex++) {
            principalField = (PrincipalField) pfs.get(fieldIndex);
            if (principalField != null) {
              String fieldId = fc.getId() + "_" + principalField.getId();
              String[] fieldValues = request.getParameterValues(fieldId);
              String onevalidateErrorMsg = null;
              try {
                PrincipalFieldValueBean bean =
                    FieldValidateHelper.validateCustomField(principalField, fieldValues, fieldId);
                if (bean != null)
                  principalFieldValueBeans.add(bean);
              } catch (Exception e) {
                onevalidateErrorMsg = e.getMessage();
                throw new Exception(validateErrorMsg.append(onevalidateErrorMsg).toString());
              }
            }

          }
        }
      }
    }
    return principalFieldValueBeans;
  }
  
  
  @RequestMapping(value = "/getAuditInfo", method = RequestMethod.GET)
  public ModelAndView getAuditInfo(long id,String code,long taskId) {

    ModelAndView view = new ModelAndView("/visa/manager/getAuditInfo");

    Map userFirstDisplayMsg = new HashMap();
    List userCustomMsg;
    List userSysMsg;
    String msg;
    VisaPrincipalBo principal = null;
    try {
      
      principal = visaService.getVisaPrincipal(id,taskId,code);
      if(principal == null) {
        view.addObject(ERROR_MSG, "VisaPrincipal记录不存在");
        view.addObject(SUCCESS_KEY, false);
        return view;
      }
      List<FieldCategory> fieldCategorys = visaService.getFieldCategorys(code);

      replaceFieldCategorysByValue(principal, fieldCategorys, false);

      userCustomMsg = fieldCategorys;

    } catch (Exception e) {
      LOG.error(e.getMessage(), e, new String[0]);
      return view;
    }
    view.addObject("firstDisplayMsg", userFirstDisplayMsg);
    view.addObject("customMsg", userCustomMsg);
    view.addObject("personId", id);
    view.addObject("code", code);
    view.addObject("principal", principal);

    return view;
  }
  
  @RequestMapping(value = "/getDaibanAuditInfo", method = RequestMethod.GET)
  public ModelAndView getDaibanAuditInfo(long visaPrincipalId) {

    ModelAndView view = new ModelAndView("/visa/manager/getAuditInfo");

    Map userFirstDisplayMsg = new HashMap();
    List userCustomMsg;
    List userSysMsg;
    String msg;
    VisaPrincipalBo principal = null;
    try {
      
      principal = visaService.getVisaPrincipal(visaPrincipalId);
      if(principal == null) {
        view.addObject(ERROR_MSG, "VisaPrincipal记录不存在");
        view.addObject(SUCCESS_KEY, false);
        return view;
      }
      List<FieldCategory> fieldCategorys = visaService.getFieldCategorys(principal.getTemplateCode());

      replaceFieldCategorysByValue(principal, fieldCategorys, false);

      userCustomMsg = fieldCategorys;

    } catch (Exception e) {
      LOG.error(e.getMessage(), e, new String[0]);
      return view;
    }
    view.addObject("firstDisplayMsg", userFirstDisplayMsg);
    view.addObject("customMsg", userCustomMsg);
    view.addObject("personId", principal.getPersonId());
    view.addObject("code", principal.getTemplateCode());
    view.addObject("principal", principal);

    return view;
  }
  
  @RequestMapping(value = "/audit", method = RequestMethod.POST)
  public ModelAndView audit(long principalId,int status,String auditMsg) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
       visaService.audit(principalId, status, auditMsg);
    } catch (Exception e) {
      LOG.error("修改国家错误", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, COUNTRY_UPDATE);
    }

    return view;
  }
  
  @RequestMapping(value = "/beforeDelivery", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView beforeDelivery(Long taskId) {
    ModelAndView view = new ModelAndView(DELIVERY_PAGE);
    TaskBo taskBo = taskService.selectById(taskId);
    ExpressDeliveryBo deliveryBo = visaService.selectExpressDeliveryByTaskId(taskId);
    
    view.addObject("task", taskBo);
    view.addObject("delivery", deliveryBo);
    return view;
  }
  
  
  @RequestMapping(value = "/deliveryUpdate", method = RequestMethod.POST)
  public ModelAndView deliveryUpdate(ExpressDeliveryBo expressDeliveryBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
   
    try{
      
      if(null == expressDeliveryBo.getId() || 0 == expressDeliveryBo.getId().intValue()) {
         visaService.deliverySave(expressDeliveryBo);
      }else{
         visaService.deliveryUpdate(expressDeliveryBo);
      }
      
      //任务状态完成
      TaskBo taskBo = new TaskBo();
      taskBo.setId(expressDeliveryBo.getTaskId());
      taskBo.setStatus(TaskStatus.DONE.getCode());
      taskService.update(taskBo);
      //护照状态借出
      passportService.updatePassportStatus(taskBo.getId(), PassportStatus.LOAN);
      //签证状态交付
      visaService.updateVisaStatusByTaskId(taskBo.getId(),VisaStatus.DELIVER);
    }catch(Exception e) {
      LOG.error("modify account error ",e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE);
    }
    
    return view;
  }
  
  /**
   * 待办任务签证列表
   * @param taskPo
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   * created by Shaoyuqi on 2017年4月4日 下午5:39:09
   */
  @RequestMapping(value = "/ajaxListToVisa", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView ajaxListToVisa(TaskPo taskPo,String type,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    if(taskPo.getPageNo() == null || taskPo.getPageNo() < 0) {
      taskPo.setPageNo(1);
    }
    
    Integer total = visaService.getVisaPrincipalCount(taskPo);
    Integer totalPage = (total + taskPo.getMaxResults() - 1) / taskPo.getMaxResults();
    
    if(totalPage > 0 && taskPo.getPageNo() > totalPage) {
      taskPo.setPageNo(totalPage);
    }
    
    
    view.addObject("total", total);
    view.addObject("pageNo", taskPo.getPageNo());
    view.addObject("rows", visaService.selectVisaPrincipalByCondition(taskPo));
    view.addObject("limit", taskPo.getMaxResults());
    view.addObject("start", taskPo.getStart());
    view.addObject(taskPo);
    //统计数量
    return view;
  }
  
  
  /**
   * 审核签证
   * @param personId
   * @param request
   * @return
   * created by Shaoyuqi on 2017年3月4日 下午7:02:31
   */
  @RequestMapping(value = "/daibanAuditVidaInfo", method = RequestMethod.GET)
  public ModelAndView daibanAuditVidaInfo(Long visaPrincipalId,HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/visa/manager/daibanAuditVisaInfo");

    try {

      VisaPrincipalBo bo = visaService.getVisaPrincipal(visaPrincipalId);
      
      String taskCountryCodes = bo.getTemplateCode();
      List<VisaCountryBo> countrys = visaCountryService.queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));

      view.addObject("visaPrincipal", bo);
      view.addObject("countrys", countrys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "申请签证资料错误：" + e.getMessage());
    }
    return view;
  }
  
  
  /**
   * 待办任务签证列表
   * @param taskPo
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   * created by Shaoyuqi on 2017年4月4日 下午5:39:09
   */
  @RequestMapping(value = "/daiBanAjaxList", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView daiBanAjaxList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    if(taskPo.getPageNo() == null || taskPo.getPageNo() < 0) {
      taskPo.setPageNo(1);
    }
    
    Integer total = visaService.getVisaPrincipalCountForToDo(taskPo);
    Integer totalPage = (total + taskPo.getMaxResults() - 1) / taskPo.getMaxResults();
    
    if(totalPage > 0 && taskPo.getPageNo() > totalPage) {
      taskPo.setPageNo(totalPage);
    }
    
    
    view.addObject("total", total);
    view.addObject("pageNo", taskPo.getPageNo());
    view.addObject("rows", visaService.selectVisaPrincipalByConditionForToDo(taskPo));
    view.addObject("limit", taskPo.getMaxResults());
    view.addObject("start", taskPo.getStart());
    view.addObject(taskPo);
    return view;
  }
  
  
  @RequestMapping(value = "/ajaxStatusList", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView ajaxStatusList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    if(taskPo.getPageNo() == null || taskPo.getPageNo() < 0) {
      taskPo.setPageNo(1);
    }
    
    taskPo.getSearchStatus().addAll(VisaStatus.getValuesList());
    
    Integer total = visaService.getVisaPrincipalCount(taskPo);
    Integer totalPage = (total + taskPo.getMaxResults() - 1) / taskPo.getMaxResults();
    
    if(totalPage > 0 && taskPo.getPageNo() > totalPage) {
      taskPo.setPageNo(totalPage);
    }
    
    
    view.addObject("total", total);
    view.addObject("pageNo", taskPo.getPageNo());
    view.addObject("rows", visaService.selectVisaPrincipalByCondition(taskPo));
    view.addObject("limit", taskPo.getMaxResults());
    view.addObject("start", taskPo.getStart());
    view.addObject(taskPo);
    return view;
  }

  
  @RequestMapping(value = "/statusList", method = RequestMethod.GET)
  public ModelAndView statusList(HttpServletRequest request) {
    ModelAndView view = new ModelAndView(VISA_STATUS_LIST_PAGE);
    HttpSession session = request.getSession();

    String taskCode = (String) session.getAttribute(LoginConstants.SESSION_TASKCODE);
    if (Strings.isNullOrEmpty(taskCode)) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
      return view;
    }
    try {

      TaskBo taskBo = taskService.selectByCode(taskCode);
      if (null == taskBo) {
        LOG.warn("can not find task by Code");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据taskCode没有找到符合条件的任务");
        return view;
      }
      view.addObject("task", taskBo);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "外事人员查看任务错误：" + e.getMessage());
    }

    return view;
  }
}
