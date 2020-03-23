package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE_IDISBLANK;
import static com.syq.web.common.ErrorCode.ACCOUNT_UPDATE_IDNOTEXIST;
import static com.syq.web.common.ErrorCode.AUDIT_PASSPORT_INFO;
import static com.syq.web.common.ErrorCode.GENERATE_TASK_CODE;
import static com.syq.web.common.WebConst.ERROR_MSG;
import static com.syq.web.common.WebConst.ERROR_PAGE;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.syq.biz.bo.AccountBo;
import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.bo.TaskCountryBo;
import com.syq.biz.bo.TaskWordBo;
import com.syq.biz.bo.VisaCountryBoData;
import com.syq.biz.service.IAccountService;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.ITaskCodeService;
import com.syq.biz.service.ITaskService;
import com.syq.biz.service.impl.VisaCountryService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.TaskPo;
import com.syq.pagination.helper.PaginationHelper;
import com.syq.util.JsonUtil;
import com.syq.util.WordUtil;
import com.syq.util.constant.TaskStatus;
import com.syq.web.common.WebConst;
import com.syq.web.helper.TaskCodeHelper;
import com.syq.web.helper.TaskHelper;
import com.syq.web.utils.LoginConstants;
import com.syq.web.vo.TaskCountryVo;
import com.syq.web.vo.TaskVo;
import com.syq.web.vo.TaskWordVo;
import com.syq.word.entity.WordPersons;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskController.class);


  /**
   * 任务管理
   */
  @Autowired
  private ITaskService taskService;
  /**
   * 编码管理
   */
  @Autowired
  private ITaskCodeService taskCodeService;
  
  @Autowired
  private IAccountService accountService;
  
  @Autowired
  private IPersonService personService;
  
  @Autowired
  private IPassportService passportService;
  
  @Autowired
  private VisaCountryService visaCountryService;

  private static final String ADD_PAGE = "/task/add";
  private static final String ADD_NEW_PAGE = "/task/addTask";
  
  private static final String EDIT_PAGE = "/task/edit";
  private static final String BASE_INFO_PAGE = "/task/baseInfo";
  private static final String INFO_PAGE = "/task/info";
  private static final String ASSIGN_PAGE = "/task/assign";
  private static final String IMPORT_TASK_PAGE = "/task/importTask";
  private static final String IMPORT_TASK_PERSONS = "/task/importTaskPersons";
  private static final String ADD_PERSONS_PAGE = "/task/addPersons";
  private static final String INVAILD_STATUS = "未知的任务状态";

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list(TaskPo taskPo) {
    ModelAndView view = new ModelAndView("/task/list");
    return view;
  }
  
  @RequestMapping(value = "/renewalList", method = RequestMethod.GET)
  public ModelAndView renewalList(TaskPo taskPo, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/task/renewalTasklist");
    
    // 任务模式登录
    Object obj = request.getSession().getAttribute(LoginConstants.SESSION_TASKCODE);
    if ( obj!= null){
      view.addObject("taskCode", obj);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/auditList", method = RequestMethod.GET)
  public ModelAndView auditList(TaskPo taskPo) {
    ModelAndView view = new ModelAndView("/task/auditList");
    
    Map<Object, Object> taskWaitAuditCount = passportService.getTaskWaitAuditPassportCount();
    view.addObject("taskWaitAuditCount", JsonUtil.beanToJson(taskWaitAuditCount));
    return view;
  }

  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    PaginationHelper.paginationHandle(taskPo, (IAdapterService<TaskCodeBo>) taskService, view);
    
    view.addObject(taskPo);
    return view;
  }
  
  @RequestMapping(value = "/ajaxrenewalList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxRenewalList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    PaginationHelper.paginationHandle(taskPo, (IAdapterService<TaskCodeBo>) taskService, view);
    view.addObject(taskPo);
    return view;
  }
  
  @RequestMapping(value = "/ajaxAuditList", method = RequestMethod.GET)
  @SuppressWarnings("unchecked")
  public ModelAndView ajaxAuditList(TaskPo taskPo,HttpServletRequest request) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    PaginationHelper.paginationHandle(taskPo, (IAdapterService<TaskCodeBo>) taskService, view);
    view.addObject(taskPo);   
    
    return view;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView create(TaskVo taskVo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    TaskBo bo = TaskHelper.taskVoToBoForCreate(taskVo);
    view.addObject(SUCCESS_KEY, true);
    
    try {
      
      if (validateIsExit(taskVo)){
        LOG.error("add param is exit error ", taskVo);
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, "批件号已存在");
        return view;
      }
      taskService.save(bo);
    } catch (Exception e) {
      LOG.error("save task error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "添加任务失败");
    }

    return view;
  }

  @RequestMapping(value = "/addBeforeURL", method = RequestMethod.GET)
  public ModelAndView addBeforeURL() {
    ModelAndView view = new ModelAndView(ADD_PAGE);
    // 添加之前业务逻辑处理
    return view;
  }
  
  @RequestMapping(value = "/addNewBeforeURL", method = RequestMethod.GET)
  public ModelAndView addNewBeforeURL(String code) {
    ModelAndView view = new ModelAndView(ADD_NEW_PAGE);
    // 添加之前业务逻辑处理
    if(!Strings.isNullOrEmpty(code)) {
      TaskCodeBo taskCode = taskCodeService.findByCode(code);
      if(taskCode == null) {
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG,"根据编码没有找到相应的记录！");
        return view;
      }
      view.addObject("code", code);
    }
    return view;
  }
  
  @RequestMapping(value = "/queryVisaCountryData", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, List<VisaCountryBoData>> queryVisaCountryData() {
    Map<String , List<VisaCountryBoData>> data = new HashMap<String , List<VisaCountryBoData>>();
    List<VisaCountryBoData> visaCountryBoDataList = visaCountryService.queryVisaCountryData();
    data.put("countryDatas", visaCountryBoDataList);
    return data;
  }
  
  @RequestMapping(value = "/editBeforeURL", method = RequestMethod.GET)
  public ModelAndView editBeforeURL(Long id) {
    ModelAndView view = new ModelAndView(EDIT_PAGE);
    //添加之前业务逻辑处理
    if(id != null && 0 != id.intValue()) {
      TaskBo bo = taskService.selectById(id);
      view.addObject("task", bo);
    }
    return view;
  }
  
  @RequestMapping(value = "/editInfoURL", method = RequestMethod.GET)
  public ModelAndView editInfoURL(Integer none,String taskCode, HttpServletRequest request) {
    ModelAndView view = new ModelAndView(EDIT_PAGE);
    HttpSession session = request.getSession();
    
    taskCode = (String)session.getAttribute(LoginConstants.SESSION_TASKCODE);
    if(Strings.isNullOrEmpty(taskCode)) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
      return view;
    }

    TaskBo bo = taskService.selectByCode(taskCode);
    List<TaskCountryBo> boes = taskService.selectTaskCountrysByTaskCode(taskCode);
    view.addObject("taskCountrys", boes);
    view.addObject("task", bo);
    view.addObject("none", none);
    
    return view;
  }
  
  @RequestMapping(value = "/infoByCode", method = RequestMethod.GET)
  public ModelAndView infoByCode(Integer none,String taskCode, HttpServletRequest request) {
    ModelAndView view = new ModelAndView(BASE_INFO_PAGE);
    HttpSession session = request.getSession();
    
    taskCode = (String)session.getAttribute(LoginConstants.SESSION_TASKCODE);
    if(Strings.isNullOrEmpty(taskCode)) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
      return view;
    }

    TaskBo bo = taskService.selectByCode(taskCode);
    bo.setStatusStr(convertStatusToMsg(bo.getStatus()));
    view.addObject("task", bo);
    view.addObject("none", none);
    
    return view;
  }
  
  private String convertStatusToMsg(int status){ 
    TaskStatus taskStatus = TaskStatus.from(status);
    return taskStatus == null ? INVAILD_STATUS : taskStatus.getMsg();
  }
  
  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public ModelAndView info(Long id) {
    ModelAndView view = new ModelAndView(INFO_PAGE);
    
    if(null ==id || 0 == id) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务详情的ID不能为空");
      return view;
    }
    
    try{
      
      TaskBo taskBo = taskService.selectById(id);
      if(null == taskBo) {
        LOG.warn("can not find task by id");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据ID没有找到符合条件的任务");
        return view;
      }
      
      taskBo.setTaskCountryName(getTaskCountryName(taskBo.getTaskCountry(),visaCountryService));
      taskBo.setTaskBeginTimeStr(dateToString(taskBo.getTaskBeginTime()));
      taskBo.setTaskEndTimeStr(dateToString(taskBo.getTaskEndTime()));
      
      PersonBo personBo = personService.getTaskHeaderPerson(id);
      if(personBo != null) {
        taskBo.setHeaderName(personBo.getUserName());
      }
      
      view.addObject("task", taskBo);
    }catch(Exception e) {
      LOG.error(e.getMessage(),e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "任务详情内部错误");
    }
    
    return view;
  }
  
  @RequestMapping(value = "/operateStatus", method = RequestMethod.POST)
  public ModelAndView operateStatus(Long id, int taskStatus) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    TaskStatus status = TaskStatus.from(taskStatus);
    if(id == null || status == null) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(ERROR_MSG, "参数不合法");
      return view;
    }
   
    try{
      taskService.opearteTaskStatus(id, status);
    }catch(Exception e) {
      LOG.error("operateStatus error ",e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, AUDIT_PASSPORT_INFO);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ModelAndView delete(Long[] id) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    try{
      taskService.deleteByIds(id);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    
    return view;
  }
  
  
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ModelAndView modify(TaskVo taskVo,String[] selectTaskCountry,Integer[] expectStickDay,String[] comment) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    if(null == taskVo.getId() || 0 == taskVo.getId().intValue()) {
      LOG.warn("update task failed,id not allowed null");
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE_IDISBLANK);
      return view;
    }
    try{
      
      if(selectTaskCountry == null || expectStickDay == null || comment == null) {
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, "参数不能为空!");
        return view;
      }
      
      if(!(selectTaskCountry.length == expectStickDay.length && expectStickDay.length == comment.length)) {
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, "参数格式错误!");
        return view;
      }
      
      List<TaskCountryVo> taskCountryVoes = new ArrayList<TaskCountryVo>();
      TaskBo taskBo = taskService.selectById(taskVo.getId());
      StringBuilder sb = new StringBuilder();
      for(int i = 0;i<selectTaskCountry.length;i++) {
        TaskCountryVo vo = new TaskCountryVo();
        vo.setTaskCountry(selectTaskCountry[i]);
        vo.setExpectStickDay(expectStickDay[i]);
        vo.setComment(comment[i]);
        taskCountryVoes.add(vo);
        sb.append(selectTaskCountry[i]);
        if(i<selectTaskCountry.length -1 ){
          sb.append(",");
        }
      }
      taskVo.setTaskCountrys(taskCountryVoes);
      
      
      if(null == taskBo) {
        LOG.warn("can not find task by id");
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE_IDNOTEXIST);
        return view;
      }
      
      TaskBo bo = TaskHelper.taskVoToBoForUpdate(taskVo);
      List<TaskCountryBo> taskCountrys = TaskHelper.convertTo(taskBo.getTaskCode(), taskBo.getInstructionNo(), taskVo.getTaskCountrys());
      bo.setTaskCountrys(taskCountrys);
      bo.setTaskCountry(sb.toString());
      taskService.update(bo);
    }catch(Exception e) {
      LOG.error("modify task error ",e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_UPDATE);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/beforeAssign", method = RequestMethod.GET)
  public ModelAndView assign(Long id) {
    ModelAndView view = new ModelAndView(ASSIGN_PAGE);
    if(null ==id || 0 == id) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "分配任务的ID不能为空");
      return view;
    }
    try{
      
      TaskBo taskBo = taskService.selectById(id);
      if(null == taskBo) {
        LOG.warn("can not find task by id");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据ID没有找到符合条件的任务");
        return view;
      }
      taskBo.setTaskCountryName(getTaskCountryName(taskBo.getTaskCountry(),visaCountryService));
      taskBo.setTaskBeginTimeStr(dateToString(taskBo.getTaskBeginTime()));
      taskBo.setTaskEndTimeStr(dateToString(taskBo.getTaskEndTime()));
      view.addObject("task", taskBo);
    }catch(Exception e) {
      LOG.error(e.getMessage(),e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "任务分配错误");
    }
    
    return view;
  }
  
  /**
   * 获取任务，并获取出差人员
   * @param taskCode
   * @param request
   * @return
   * created by Shaoyuqi on 2017年2月12日 上午10:49:05
   */
  @RequestMapping(value = "/beforeAddAccounts", method = RequestMethod.GET)
  public ModelAndView beforeAddAccounts(String taskCode,int none,HttpServletRequest request) {
    ModelAndView view = new ModelAndView(ADD_PERSONS_PAGE);
    HttpSession session = request.getSession();
    
    taskCode = (String)session.getAttribute(LoginConstants.SESSION_TASKCODE);
    if(Strings.isNullOrEmpty(taskCode)) {
      view.setViewName(WebConst.ERROR_PAGE);
      view.addObject(WebConst.ERROR_MSG, "任务编码不能为空");
      return view;
    }
    try{
      
      TaskBo taskBo = taskService.selectByCode(taskCode);
      if(null == taskBo) {
        LOG.warn("can not find task by Code");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据taskCode没有找到符合条件的任务");
        return view;
      }
      String taskCountryCodes = taskBo.getTaskCountry();
      taskBo.setTaskCountryName(getTaskCountryName(taskCountryCodes,visaCountryService));
      taskBo.setTaskBeginTimeStr(dateToString(taskBo.getTaskBeginTime()));
      taskBo.setTaskEndTimeStr(dateToString(taskBo.getTaskEndTime()));
      // 获取出差人员
      List<AuditPersonBo> persons = personService.getPersonsByTaskId(taskBo.getId());
      // 获取已上传资料的人员Id
      List<Long> hasApplyIds = personService.getHasApplyPersonIds(taskBo.getId());
      
      PersonBo personBo = personService.getTaskHeaderPerson(taskBo.getId());
      if(personBo != null) {
        taskBo.setHeaderName(personBo.getUserName());
      }
      
      view.addObject("persons", persons);
      view.addObject("hasApplyPersonIds", hasApplyIds);
      view.addObject("task", taskBo);
      //查询此任务团长identity3
      
      //为了展示页面效果
      view.addObject("none",none);
    }catch(Exception e) {
      LOG.error(e.getMessage(),e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "外事人员查看任务错误："+ e.getMessage());
    }
    
    return view;
  }
  
  
  @RequestMapping(value = "/getAccountsByTaskId", method = RequestMethod.GET)
  public ModelAndView getAccountsByTaskId(Long taskId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    try{
      List<AccountBo> accounts = accountService.getAccountsByTaskId(taskId);
      view.addObject("rows", accounts);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/getPersonsByTaskId", method = RequestMethod.GET)
  public ModelAndView getPersonsByTaskId(Long taskId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    try{
      List<AuditPersonBo> persons = personService.getPersonsByTaskId(taskId);
      view.addObject("rows", persons);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/assign", method = RequestMethod.POST)
  public ModelAndView assign(Long taskId,@RequestParam(value = "accountIds[]",required=false)  Long[] accountIds) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    
    try{
      taskService.assign(taskId,accountIds);
    }catch(Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    
    return view;
  }
  
  @RequestMapping(value = "/importTask", method = RequestMethod.GET)
  public ModelAndView importTask() {
    ModelAndView view = new ModelAndView(IMPORT_TASK_PAGE);
    return view;
  }
  
  @RequestMapping(value = "/generateTaskCode", method = RequestMethod.GET)
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
    if(null != boes && boes.size() > 0){
      view.addObject("taskCode",boes.get(0).getTaskCode());
    }else{
      view.addObject(SUCCESS_KEY, false);
    }
    return view;
  }
  
  @RequestMapping(value = "/beforeImportPersons", method = RequestMethod.GET)
  public ModelAndView beforeImportPersons(Long id) {
    ModelAndView view = new ModelAndView(IMPORT_TASK_PERSONS);
    view.addObject("taskId", id);
    return view;
  }
  
  @RequestMapping(value="/downloadWord",method={RequestMethod.GET})
  public void downloadWord(Long taskId, HttpServletResponse response){
    try{
      if (taskId == null){
        LOG.error("参数为空");
        return;
     }
      
      //TODO 更新生成批件日期
      TaskBo taskBo = taskService.selectById(taskId);
      taskBo.setInstructionTime(new Date());
      taskService.update(taskBo);
      
      TaskWordVo taskWordVo = getTaskWordVo(taskId);
      File wordFile = WordUtil.testCreateWord(taskWordVo.converToWordMap());
      if (wordFile == null){
         LOG.error("生成word文档失败");
         return;
      }
      
      BufferedInputStream bis = null; 
      BufferedOutputStream bos = null; 

      //获取文件的长度
      long fileLength = wordFile.length(); 

      //设置文件输出类型
      response.setContentType("application/octet-stream"); 
      response.setHeader("Content-disposition", "attachment; filename=" 
              + new String(wordFile.getName().getBytes("utf-8"), "ISO8859-1"));
      //设置输出长度
      response.setHeader("Content-Length", String.valueOf(fileLength)); 
      //获取输入流
      bis = new BufferedInputStream(new FileInputStream(wordFile)); 
      //输出流
      bos = new BufferedOutputStream(response.getOutputStream()); 
      byte[] buff = new byte[2048]; 
      int bytesRead; 
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) { 
          bos.write(buff, 0, bytesRead); 
      } 
      //关闭流
      bis.close(); 
      bos.close();
      
     
    } catch (Exception e){
      LOG.error("download word file error {}", e);
    }
   
  }
  
  private TaskWordVo getTaskWordVo(Long taskId){
    TaskWordVo vo = new TaskWordVo();
    TaskWordBo bo = taskService.getTaskWordVoById(taskId);
    if (bo == null){
      return vo;
    }
    BeanUtils.copyProperties(bo, vo);
    buildWordPerson(taskId, vo);
    buildWordCountry(bo.getTaskCode(), vo);
    
    return vo;
  }
  
  private TaskWordVo buildWordPerson(Long taskId, TaskWordVo vo){
    List<AuditPersonBo> persons = personService.getPersonsByTaskId(taskId);
    if (persons == null){
       return vo;
    }
    
    List<WordPersons> personList = Lists.newArrayList();
    for (AuditPersonBo bo : persons){
      if (bo.isColonel()){
        vo.setDeptName(bo.getDeptName());
        vo.setHeaderName(bo.getUserName());
      }
      personList.add(WordPersons.getNewInstance(bo.getUserName(), bo.getIdNumber(), bo.getDeptName()));
    }
    
    vo.setPersonList(personList);
    vo.setPersonCount(String.valueOf(personList.size()));
    return vo;
  }
  
  private TaskWordVo buildWordCountry(String taskCode, TaskWordVo vo){
    if (StringUtils.isBlank(taskCode)){
      return vo;
    }
    
    List<TaskCountryBo> countryList = taskService.selectTaskCountrysByTaskCode(taskCode);
    if (CollectionUtils.isEmpty(countryList)){
      return vo;
    }
    
    StringBuilder taskCountry = new StringBuilder("");
    int taskStickDay = 0;
    
    for (TaskCountryBo bo : countryList){
      if (StringUtils.isNotBlank(bo.getCountryName())){
        taskCountry.append(bo.getCountryName()).append(",");
      }
      taskStickDay +=  bo.getExpectStickDay();
    }
    
    if (taskCountry.length() > 0){
      taskCountry.substring(0, taskCountry.length() - 1);
    }
    
    vo.setTaskCountry(taskCountry.toString());
    vo.setStickDay(String.valueOf(taskStickDay));
    return vo;
  }
  
  
  @SuppressWarnings("unchecked")
  private boolean validateIsExit(TaskVo vo){
    if (vo == null){
      LOG.error("task validate param has null");
      return false;
    }
    
    TaskPo po = new TaskPo();
    po.setInstructionNo(vo.getInstructionNo());
    return PaginationHelper.paginationCountHandle(po, (IAdapterService<TaskCodeBo>) taskService) > 0;
  }
 
}
