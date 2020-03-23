/**
 * com.syq.web.controller.PassportController.java created by Huangye(yuanhan.syq@alibaba-inc.com)
 * on 2016年8月28日 下午2:03:39
 */
package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.ErrorCode.APPLY_PASSPORT_INFO_ADD;
import static com.syq.web.common.ErrorCode.AUDIT_PASSPORT_INFO;
import static com.syq.web.common.WebConst.ERROR_MSG;
import static com.syq.web.common.WebConst.ERROR_PAGE;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.base.Strings;
import com.syq.biz.bo.ApplyPassportInfoBo;
import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.PassportBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.domain.Passport;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.IRenewalPassportService;
import com.syq.biz.service.ITaskService;
import com.syq.biz.service.IVisaService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.AuditInfoPo;
import com.syq.pagination.common.PassportPo;
import com.syq.pagination.common.TaskPo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.util.constant.PassportStatus;
import com.syq.util.constant.VisaStatus;
import com.syq.util.convert.BeanUtil;
import com.syq.web.common.WebConst;
import com.syq.web.utils.LoginConstants;
import com.syq.web.vo.PassportVo;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:03:39
 */
@Controller
@RequestMapping("/passport")
public class PassportController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PassportController.class);
  public static final String VIEW_PAGE = "/passport/view";
  public static final String APPLY_PASSPORT_INFO = "/passport/applyPassportInfo";

  @Autowired
  private IPassportService passportService;
  @Autowired
  private IPersonService personService;
  @Autowired
  private ITaskService taskService;
  @Autowired
  private IVisaService visaService;
  @Autowired
  private IRenewalPassportService renewalPassportService;


  /**
   * 护照待办任务列表
   * 
   * @param taskId
   * @return created by Shaoyuqi on 2017年4月5日 下午3:44:07
   */
  @RequestMapping(value = "/daiBanAjaxList", method = RequestMethod.GET)
  public ModelAndView daiBanAjaxList(Long taskId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      List<AuditPersonBo> persons = personService.getPersonsByTaskIdForToDoList(taskId);
      view.addObject("rows", persons);
    } catch (Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }

    return view;
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(PassportPo passportPo) {
    ModelAndView view = new ModelAndView("/passport/list");

    view.addObject("countExpire", passportPo == null ? false : passportPo.isNeedCountExprie());
    view.addObject("countNotReturn", passportPo == null ? false : passportPo.isNeedCountNotReturn());

    return view;
  }

  @RequestMapping(value = "/todoList", method = RequestMethod.GET)
  public ModelAndView toDolist(PassportPo passportPo,String select) {
    ModelAndView view = new ModelAndView("/todoTask/list");

    view.addObject("countExpire", passportPo == null ? false : passportPo.isNeedCountExprie());
    view.addObject("aboutExpire", passportPo == null ? false : passportPo.isNeedCountAboutExprie());
    view.addObject("countNotReturn", passportPo == null ? false : passportPo.isNeedCountNotReturn());
    view.addObject("select", select);

    return view;
  }


  @RequestMapping(value = "/todoFaffList", method = RequestMethod.GET)
  public ModelAndView todoFaffList(HttpServletRequest request, PassportPo passportPo,String select) {
    ModelAndView view = new ModelAndView("/todoTask/faffList");
    
    try {
      
      HttpSession session = request.getSession();

      String taskCode = (String) session.getAttribute(LoginConstants.SESSION_TASKCODE);
      if (Strings.isNullOrEmpty(taskCode)) {
        view.setViewName(WebConst.ERROR_PAGE);
        view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
        return view;
      }

      TaskBo taskBo = taskService.selectByCode(taskCode);
      if (null == taskBo) {
        LOG.warn("can not find task by Code");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据taskCode没有找到符合条件的任务");
        return view;
      }
      view.addObject("task", taskBo);
      view.addObject("select", select);
      // 为了展示页面效果
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "外事人员查看任务错误：" + e.getMessage());
    }

    return view;
  }

  @RequestMapping(value = "/expireCount", method = RequestMethod.GET)
  public ModelAndView expireCount() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    int count = passportService.expireCount();
    view.addObject("expireCount", count);

    return view;
  }

  @RequestMapping(value = "/todoCount", method = RequestMethod.GET)
  public ModelAndView todoCount(HttpServletRequest request) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    HttpSession session = request.getSession();
    
    String taskCode = (String) session.getAttribute(LoginConstants.SESSION_TASKCODE);
    int count = Strings.isNullOrEmpty(taskCode) ? doAdminCount() : doTaskCount(taskCode);
    view.addObject("todoCount", count);
    
    return view;
  }
  
  private int doAdminCount(){
    return this.countRenewalPassport() + this.countTodoPassport() + this.countTodoVisa();
  }
  
  private int doTaskCount(String taskCode){
    TaskBo taskBo = taskService.selectByCode(taskCode);
    if (taskBo == null){
      LOG.error("invalid taskCode in session {}", taskCode);
      return 0;
    }
    
    return this.countTodoPassportByTaskId(taskBo.getId()) + this.countTodoVisaByTaskCode(taskCode);
  }
  
  @RequestMapping(value = "/notReturnCount", method = RequestMethod.GET)
  public ModelAndView notReturnCount() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    int count = passportService.overtimeNotReturnCount();
    view.addObject("notReturnCount", count);

    return view;
  }

  @RequestMapping(value = "/addBefore", method = RequestMethod.GET)
  public ModelAndView addBefore(PassportPo passportPo) {
    ModelAndView view = new ModelAndView("/passport/add");
    Passport passport = null;
    try {
      passport = passportService.getPassportByIdNumber(passportPo.getIdNumber());
    } catch (Exception e) {
      LOG.error("addBeforePassportInfo error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, AUDIT_PASSPORT_INFO);
    }

    view.addObject("passport", passport);
    return view;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  public ModelAndView ajaxList(PassportPo passportPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    if (passportPo != null) {
      passportPo.buildCountExpireParam();
      passportPo.builCountNotReturnParam();
      passportPo.buildAboutExpiredTime();
    }

    PaginationHelper.paginationHandle(passportPo, (IAdapterService<PassportBo>) passportService,
        view);
    return view;
  }


  @RequestMapping(value = "/auditList", method = RequestMethod.GET)
  public ModelAndView auditList(PassportPo passportPo) {
    ModelAndView view = new ModelAndView("/passport/auditList");
    return view;
  }

  @RequestMapping(value = "/luruList", method = RequestMethod.GET)
  public ModelAndView luruList(PassportPo passportPo, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/passport/luruList");
    addTaskId2Model(request, view);
    return view;
  }

  @RequestMapping(value = "/statusList", method = RequestMethod.GET)
  public ModelAndView statusList(PassportPo passportPo, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/passport/statusList");
    addTaskId2Model(request, view);
    return view;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/auditAjaxList", method = RequestMethod.GET)
  public ModelAndView auditAjaxList(PassportPo passportPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    PaginationHelper.paginationHandle(passportPo, (IAdapterService<PassportBo>) passportService,
        view);
    return view;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/statusAjaxList", method = RequestMethod.GET)
  public ModelAndView statusAjaxList(PassportPo passportPo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    PaginationHelper.paginationHandle(passportPo, (IAdapterService<PassportBo>) passportService,
        view);
    return view;
  }

  @RequestMapping(value = "/insert", method = RequestMethod.GET)
  public ModelAndView create(@ModelAttribute("passportVo") PassportVo passportVo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.setViewName("/passport/insert");
    view.addObject("SUCCESS", "false");
    PassportBo bo = new PassportBo();
    BeanUtil.copyProperties(passportVo, bo);
    Integer passportId = passportService.save(bo);
    if (null != passportId && passportId == 1) {
      view.addObject("SUCCESS", "true");
    }
    return view;
  }

  @RequestMapping(value = "/view", method = RequestMethod.GET)
  public ModelAndView view(Long id) {
    ModelAndView view = new ModelAndView(ERROR_PAGE);
    if (id == null || 0 >= id) {
      view.addObject(ERROR_MSG, "参数不合法");
      return view;
    }

    Passport passport = passportService.selectByPrimaryKey(id);
    if (passport == null) {
      view.addObject(ERROR_MSG, "根据条件没有找到相应的记录");
      return view;
    }

    view = new ModelAndView(VIEW_PAGE);
    view.addObject("passport", passport);
    return view;
  }

  @RequestMapping(value = "/operateStatus", method = RequestMethod.POST)
  public ModelAndView operateStatus(String passportNo, int flowStatus, String msg) {

    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    PassportStatus status = PassportStatus.from(flowStatus);
    if (StringUtils.isEmpty(passportNo) || status == null) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(ERROR_MSG, "参数不合法");
      return view;
    }

    try {
      passportService.updatePassportStatus(passportNo, status, msg);
    } catch (Exception e) {
      LOG.error("operateStatus error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, AUDIT_PASSPORT_INFO);
    }

    return view;
  }

  @RequestMapping(value = "/applyPassportInfo", method = RequestMethod.GET)
  public ModelAndView addFamilyBookBefore(Long personId, Long taskId) {
    ModelAndView view = new ModelAndView(APPLY_PASSPORT_INFO);
    PersonBo personBo = personService.selectById(personId);
    view.addObject("person", personBo);

    ApplyPassportInfoBo applyBo = passportService.getApplyPassportByPersonId(personId);
    if (applyBo != null) {
      view.addObject("applyPassportInfo", applyBo);
    }

    Passport passport = passportService.getPassportByPersonId(personId);
    view.addObject("passport", passport);

    return view;
  }

  @RequestMapping(value = "/auditPassportInfo", method = RequestMethod.GET)
  public ModelAndView auditPassportInfoBefore(Long personId, int dealFlag) {
    ModelAndView view = new ModelAndView("/passport/auditPassportInfo");

    ApplyPassportInfoBo bo = passportService.getApplyPassportByPersonId(personId);
    view.addObject("applyPassportInfo", bo);
    // TODO 待检验优化
    view.addObject("dealFlag", dealFlag);
    return view;
  }

  @RequestMapping(value = "/auditPassport", method = RequestMethod.GET)
  public ModelAndView auditPassportInfoBefore(String idNumber, int dealFlag) {
    ModelAndView view = new ModelAndView("/passport/auditPassportInfo");

    ApplyPassportInfoBo bo = passportService.getApplyPassportByIdNumber(idNumber);
    view.addObject("applyPassportInfo", bo);
    // TODO 待检验优化
    view.addObject("dealFlag", dealFlag);
    return view;
  }

  @RequestMapping(value = "/auditPassportInfo", method = RequestMethod.POST)
  public ModelAndView auditPassportInfo(AuditInfoPo auditInfo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      passportService.auditApplyPassportInfo(auditInfo);
    } catch (Exception e) {
      LOG.error("auditApplyPassportInfo error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, AUDIT_PASSPORT_INFO);
    }

    return view;
  }

  @RequestMapping(value = "/applyPassportInfo", method = RequestMethod.POST)
  public ModelAndView savePassportInfo(ApplyPassportInfoBo applyPassportInfo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      passportService.saveApplyPassportInfo(applyPassportInfo);
    } catch (Exception e) {
      LOG.error("saveApplyPassportInfo error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, APPLY_PASSPORT_INFO_ADD);
    }

    return view;
  }


  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ModelAndView update(@ModelAttribute("passportVo") PassportVo passportVo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, "false");
    PassportBo bo = new PassportBo();
    BeanUtil.copyProperties(passportVo, bo);
    int passportId = passportService.update(bo);
    if (passportId > 0) {
      view.addObject(SUCCESS_KEY, "true");
    }
    return view;
  }

  private ModelAndView addTaskId2Model(HttpServletRequest request, ModelAndView view) {

    Object obj = request.getSession().getAttribute(LoginConstants.SESSION_TASKCODE);

    if (obj != null) {
      String code = obj.toString();
      TaskBo bo = taskService.selectByCode(code);
      if (bo != null && bo.getId() != null) {
        view.addObject("taskId", bo.getId());
      }
    }

    return view;
  }
  
  
  @RequestMapping(value = "/ajaxListTodo", method = RequestMethod.GET)
  public ModelAndView ajaxListTodo(HttpServletRequest request,PassportPo passportPo,String type) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    if (passportPo != null) {
      passportPo.buildCountExpireParam();
      passportPo.builCountNotReturnParam();
      passportPo.buildAboutExpiredTime();
    }

    if(passportPo.getPageNo() == null || passportPo.getPageNo() < 0) {
      passportPo.setPageNo(1);
    }
    
    Integer total = passportService.getCountTodo(passportPo);
    Integer totalPage = (total + passportPo.getMaxResults() - 1) / passportPo.getMaxResults();
    
    if(totalPage > 0 && passportPo.getPageNo() > totalPage) {
      passportPo.setPageNo(totalPage);
    }
    
//    po.setFirstResult((po.getPageNo() - 1) * po.getMaxResults());
    
    view.addObject("total", total);
    view.addObject("pageNo", passportPo.getPageNo());
    view.addObject("rows", passportService.queryTodo(passportPo));
    view.addObject("limit", passportPo.getMaxResults());
    view.addObject("start", passportPo.getStart());
    
    //统计数量
    return view;
  }

  private int countTodoPassport(){
    
    IAdapterService<PassportBo> adapter  = (IAdapterService<PassportBo>) passportService;
    
    PassportPo po = PassportPo.getNewInstance();
    Integer[] passportStatus = {PassportStatus.AUDIT_WAIT.getCode(), PassportStatus.HADNDLING.getCode(), PassportStatus.LOAN.getCode()};
    Integer[] aboutPassportStatus = {PassportStatus.HADNDLING.getCode(), PassportStatus.LOAN.getCode()};
    
    po.setStatus(passportStatus);
    int count = adapter.getCount(po);
    
    po = PassportPo.getNewInstance();
    po.setNeedCountAboutExprie(true);
    po.buildAboutExpiredTime();
    
    po.buildCountExpireParam();
    po.builCountNotReturnParam();
    po.buildAboutExpiredTime();

    
    Integer total = passportService.getCountTodo(po);
    count = count + total;
    
    return count;
  }
  
  private int countTodoVisa(){
    
    TaskPo po = TaskPo.getNewInstance();
    
    List<Integer> searchStatus = new ArrayList<Integer>();
//    searchStatus.add(VisaStatus.HADNDLING.getCode());
    searchStatus.add(VisaStatus.AUDIT_WAIT.getCode());
    po.setSearchStatus(searchStatus);
    
    Integer total = visaService.getVisaPrincipalCount(po);
    TaskPo taskPo = new TaskPo();
    taskPo.setSearchStatus(Arrays.asList(new Integer[]{1}));
    Integer total2 = visaService.getNewVisaPrincipalCount(taskPo);
    
    return total + total2;
  }
  
  private int countRenewalPassport(){
    
    IAdapterService<PassportBo> adapter  = (IAdapterService<PassportBo>) renewalPassportService;
    PassportPo po = PassportPo.getNewInstance();
    po.setNeedCountAboutExprie(true);
    po.buildAboutExpiredTime();
    
    return adapter.getCount(po);
  }
  
  private int countTodoPassportByTaskId(long taskId){
    List<AuditPersonBo> persons = personService.getPersonsByTaskIdForToDoList(taskId);
    return persons == null ? 0 : persons.size();
  }
  
  private int countTodoVisaByTaskCode(String taskCode){
    TaskPo taskPo = new TaskPo();
    taskPo.setTaskCode(taskCode);
    return visaService.getVisaPrincipalCountForToDo(taskPo);
  }
  
  @RequestMapping(value = "/beforeImportPassports", method = RequestMethod.GET)
  public ModelAndView beforeImportPassports() {
    ModelAndView view = new ModelAndView("/passport/importPassports");

    return view;
  }
}
