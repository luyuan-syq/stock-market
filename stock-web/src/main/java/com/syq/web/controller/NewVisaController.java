package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.WebConst.ERROR_MSG;
import static com.syq.web.common.WebConst.ERROR_PAGE;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.base.Strings;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.bo.VisaFileFlowBo;
import com.syq.biz.bo.VisaFileTemplateBo;
import com.syq.biz.bo.VisaPrincipalBo;
import com.syq.biz.domain.VisaFileTemplate;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.ITaskService;
import com.syq.biz.service.IVisaCountryService;
import com.syq.biz.service.IVisaFileTemplateService;
import com.syq.biz.service.IVisaService;
import com.syq.exception.SYQException;
import com.syq.pagination.common.TaskPo;
import com.syq.pagination.common.VisaFileTemplatePo;
import com.syq.util.JsonUtil;
import com.syq.util.constant.FileStatus;
import com.syq.util.constant.FileType;
import com.syq.util.constant.VisaStatus;
import com.syq.web.common.WebConst;
import com.syq.web.utils.BaseBeanToJsonView;
import com.syq.web.utils.LoginConstants;
import com.syq.web.utils.UploadHelper;

@Controller
@RequestMapping("/newVisa")
public class NewVisaController extends BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(NewVisaController.class);

  protected final static String FILE_TEMPLATE_MANAGER = "/visa/manager/newFileTemplateManager";
  protected final static String TEXT_LIST_MANAGER = "/visa/manager/newTextListManager";

  /** 图片后缀名 */
  protected final static String FILE_TEMPLATE_Ext = "xlsx";

  @Autowired
  private IVisaFileTemplateService visaFileTemplateService;
  @Autowired
  private IVisaCountryService visaCountryService;
  @Autowired
  private ITaskService taskService;
  @Autowired
  private IPersonService personService;
  @Autowired
  private IVisaService visaService;

  @Autowired
  private IPassportService passportService;

  /**
   * 文件模板管理
   * 
   * @return created by Shaoyuqi on 2017年4月30日 上午10:54:59
   */
  @RequestMapping(value = "/ftManager", method = RequestMethod.GET)
  public ModelAndView ftManager() {
    ModelAndView view = new ModelAndView(FILE_TEMPLATE_MANAGER);
    return view;
  }


  @RequestMapping(value = "/ajaxListToFT", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView ajaxListToVisa(VisaFileTemplatePo vidaFileTemplate, HttpServletRequest request)
      throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    vidaFileTemplate.setType(0);
    // 查询所有文件清单
    view.addObject("rows",
        visaFileTemplateService.selectVisaFileTemplateByCondition(vidaFileTemplate));

    return view;
  }


  @RequestMapping(value = "/uploadFileTemplate", method = RequestMethod.POST)
  @ResponseBody
  public String uploadPic(MultipartHttpServletRequest request, HttpServletResponse response)
      throws Exception {
    MultipartFile fileUpload = request.getFile("Filedata");
    response.setCharacterEncoding("UTF-8");
    String extensionName = null;
    String fileNameLong = null;
    // 文件名
    String fileName = null;
    String url = null;


    // 判断是否有文件
    if (!fileUpload.isEmpty()) {
      // 获取文件上传路径名称
      fileNameLong = fileUpload.getOriginalFilename();
      fileName = fileNameLong.substring(0, fileNameLong.lastIndexOf("."));
      // 获取文件扩展名
      extensionName = fileNameLong.substring(fileNameLong.lastIndexOf(".") + 1);
//      if (("," + FILE_TEMPLATE_Ext.toLowerCase() + ",").indexOf("," + extensionName.toLowerCase()
//          + ",") < 0) {
//        printInfo(response, "不允许上传此类型的文件", null);
//        return null;
//      }
    } else {
      printInfo(response, "上传文件不能为空", null);
      return null;
    }
    
    //判断文件是否存在
//    String uuid = UUID.randomUUID().toString();
    url = fileName + "."+extensionName;
    List<VisaFileTemplate> vftList = visaFileTemplateService.selectVisaFileTemplateByCondition(buildVisaFileTemplate(fileName));
    if(!CollectionUtils.isEmpty(vftList)) {
      printInfo(response, "已经存在此文件", null);
      return null;
    }
    InputStream image = null;
    try {
      image = fileUpload.getInputStream();
      UploadHelper.saveFile(fileUpload, null, url);

      // 保存文件
      VisaFileTemplate vft = buildVisaFileTemplate(fileName,fileName, extensionName);
      Long result = visaFileTemplateService.save(vft);
      if (result == null) {
        throw new SYQException("保存文件模板错误");
      }
    } catch (Exception e) {
      LOG.error("上传文件模板错误:" + e.getMessage(), e);
      printInfo(response, "上传文件模板错误", null);
      return null;
    } finally {
      if (image != null) {
        image.close();
      }
    }
    printInfoWithName(response, null, url + "." + extensionName, fileName);
    return null;
  }
  
  private VisaFileTemplatePo buildVisaFileTemplate(String url) {
    VisaFileTemplatePo po = new VisaFileTemplatePo();
    po.setName(url);
    return po;
  }

  @RequestMapping(value = "/deleteFT", method = RequestMethod.GET)
  public ModelAndView delete(Long[] id) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      
      int count = visaFileTemplateService.selectCountCAFByFileId( id[0]);
      if(count > 0) {
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, "改文件被占用不能删除");
        return view;
      }
      
      visaFileTemplateService.deleteByIds(id);
    } catch (Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "删除标准文件模板错误 "+e.getMessage());
    }

    return view;
  }


  private VisaFileTemplate buildVisaFileTemplate(String fileName,String encodeFileName, String extensionName) {
    VisaFileTemplate vft = new VisaFileTemplate();
    vft.setName(fileName);
    vft.setUrl(encodeFileName +  "." + extensionName);
    vft.setCreateTime(new Date());
    vft.setOperatorId(getLoginName());
    return vft;
  }

  /**
   * 发送消息
   * 
   * @param response
   * @param err
   * @param success
   * @throws IOException
   */
  private void printInfo(HttpServletResponse response, String err, String success)
      throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    BaseBeanToJsonView bt = new BaseBeanToJsonView();
    PrintWriter out = response.getWriter();
    if (err != null) {
      bt.setError(err);
    } else {
      bt.setSuccess(success);
    }
    out.println(JsonUtil.beanToJson(bt));
    out.flush();
    out.close();
  }

  /**
   * 发送消息
   * 
   * @param response
   * @param err
   * @param success
   * @param fileName
   * @throws IOException
   */
  private void printInfoWithName(HttpServletResponse response, String err, String success,
      String fileName) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    BaseBeanToJsonView bt = new BaseBeanToJsonView();
    PrintWriter out = response.getWriter();
    if (err != null) {
      bt.setError(err);
    } else {
      bt.setSuccess(success);
    }
    bt.setFileName(fileName);
    out.println(JsonUtil.beanToJson(bt));
    out.flush();
    out.close();
  }


  @RequestMapping(value = "/tlManager", method = RequestMethod.GET)
  public ModelAndView tlManager() {
    ModelAndView view = new ModelAndView(TEXT_LIST_MANAGER);
    //查询所有的标准模板
    VisaFileTemplatePo po = new VisaFileTemplatePo();
    po.setType(0);
    List<VisaFileTemplate> vfts = visaFileTemplateService.selectVisaFileTemplateByCondition(po);
    view.addObject("vfts",vfts );
    
    return view;
  }

  @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView ajaxList(VisaFileTemplatePo po, HttpServletRequest request)
      throws UnsupportedEncodingException {

    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    if (po.getCountryId() == null || po.getCountryId() == 0) {
      return view;
    }

    // 查询指定国家的清单
    view.addObject("rows", visaFileTemplateService.selectVisaFileTemplateByCountry(po));

    return view;
  }

  @RequestMapping(value = "/applyVisaInfo", method = RequestMethod.GET)
  public ModelAndView applyVisaInfo(Long visaPrincipalId, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/visa/manager/newApplyVisaInfo");
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

      PersonBo personBo = personService.selectById(visaPrincipalBo.getPersonId());
      if (null == personBo) {
        LOG.warn("can not find Person by personId");
        view.setViewName(ERROR_PAGE);
        view.addObject(ERROR_MSG, "根据personId没有找到符合条件的出差人员");
        return view;
      }
      String taskCountryCodes = visaPrincipalBo.getTemplateCode();
      List<VisaCountryBo> countrys =
          visaCountryService
              .queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));

      visaService.assertVisaPrincipal(visaPrincipalBo.getPersonId(), taskBo.getId(),
          Arrays.asList(taskCountryCodes.split(",")));

      view.addObject("person", personBo);
      view.addObject("task", taskBo);
      view.addObject("country", CollectionUtils.isEmpty(countrys) ? null : countrys.get(0));
      view.addObject("principal", visaPrincipalBo);

    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "申请签证资料错误：" + e.getMessage());
    }
    return view;
  }

  @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
  public ModelAndView getInfo(long id, String code, long taskId) {

    ModelAndView view = new ModelAndView("/visa/manager/newVisaInfo");

    VisaPrincipalBo principal = null;
    List<VisaFileTemplate> list = null;
    VisaCountryBo country = null;
    try {

      principal = visaService.getVisaPrincipal(id, taskId, code);
      if (principal == null) {
        view.addObject(ERROR_MSG, "VisaPrincipal记录不存在");
        view.addObject(SUCCESS_KEY, false);
        return view;
      }
      // 获取国家
      country = visaCountryService.selectByCode(code);
      if (country == null) {
        view.addObject(ERROR_MSG, "VisaCountry记录不存在");
        view.addObject(SUCCESS_KEY, false);
        return view;
      }
      // 根据国家获取清单
      VisaFileTemplatePo po = buildVidaFileTemplate(country.getId(),principal.getId());
      list = visaFileTemplateService.selectVisaFileTemplateByCountry(po);

    } catch (Exception e) {
      LOG.error(e.getMessage(), e, new String[0]);
      return view;
    }
    // view.addObject("systemMsg", userSysMsg);
    view.addObject("personId", id);
    view.addObject("code", code);
    view.addObject("list", list);
    view.addObject("principalId", principal.getId());
    view.addObject("principal", principal);

    return view;
  }

  private VisaFileTemplatePo buildVidaFileTemplate(Long id,Long principalId) {
    VisaFileTemplatePo po = new VisaFileTemplatePo();
    po.setPrincipalId(principalId);
    po.setCountryId(id);
    return po;
  }

  @RequestMapping(value = "/ajaxListForSubmit", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView ajaxListForSubmit(VisaFileTemplatePo po, HttpServletRequest request)
      throws UnsupportedEncodingException {

    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    if (po.getPrincipalId() == null || po.getPrincipalId() == 0) {
      return view;
    }

    List<VisaFileTemplateBo> boes = visaFileTemplateService.selectForSubmit(po);

    view.addObject("rows", boes);

    return view;
  }


  @RequestMapping(value = "/updateVisaFileStatus", method = RequestMethod.POST)
  public ModelAndView updateStatus(HttpServletRequest request, HttpServletResponse response,VisaFileFlowBo visaFileFlowBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if (visaFileFlowBo.getCafileId() == null) {
      view.addObject(MESSAGE_KEY, "参数错误!!");
      return view;
    }
    try {
      //查询VisaPrincipal
      VisaPrincipalBo principal = visaService.getVisaPrincipal(visaFileFlowBo.getPersonId(),visaFileFlowBo.getTaskId(),visaFileFlowBo.getCountryCode());
      if(principal == null) {
        view.addObject(ERROR_MSG, "VisaPrincipal记录不存在");
        return view;
      }
      
      visaFileFlowBo.setPrincipalId(principal.getId());
      
      buildVisaFileFlow(visaFileFlowBo);
      // 插入一条记录
      visaFileTemplateService.assertVisaFileFlow(visaFileFlowBo);
      // 修改状态
      visaFileTemplateService.updateFileStatus(visaFileFlowBo);
      // 修改visaPrincipal进入审核状态
      boolean allPass = visaFileTemplateService.assertAllFlowAudit(visaFileFlowBo);
      
      if(allPass) {
        LOG.info("签证资料全部提交审核,进入待审核状态");
        visaService.updateVisaStatus(principal.getId(), VisaStatus.AUDIT_WAIT);
      }else{
        LOG.info("签证资料未全部审核通过！！！");
      }
     
      //修改状态,此时只有一条进入审核状态,如果全部审核通过，则进入办理状态
      //只有全部状态都变成大于提交状态的时候，principal的状态才能进入审核
      //visaService.updateVisaStatus(principal.getId(), VisaStatus.AUDIT_WAIT);
      
      view.addObject(SUCCESS_KEY, true);
    } catch (Exception e) {

      view.addObject(MESSAGE_KEY, "修改签证状态失败!!");
    }
    return view;
  }

  private VisaFileFlowBo buildVisaFileFlow(VisaFileFlowBo bo) {
    bo.setCreateTime(new Date());
    bo.setOperatorId(getLoginName());
    return bo;
  }
  
  
  @RequestMapping(value = "/daibanAuditVidaInfo", method = RequestMethod.GET)
  public ModelAndView daibanAuditVidaInfo(Long visaPrincipalId,HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/visa/manager/newDaibanAuditVisaInfo");

    try {

      VisaPrincipalBo bo = visaService.getVisaPrincipal(visaPrincipalId);
      
      String taskCountryCodes = bo.getTemplateCode();
      List<VisaCountryBo> countrys = visaCountryService.queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));

      view.addObject("visaPrincipal", bo);
      view.addObject("country", CollectionUtils.isEmpty(countrys) ? null : countrys.get(0));
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "申请签证资料错误：" + e.getMessage());
    }
    return view;
  }
  
  
  @RequestMapping(value = "/supplementVidaInfo", method = RequestMethod.GET)
  public ModelAndView supplementVidaInfo(Long visaPrincipalId,HttpServletRequest request) {
    ModelAndView view = new ModelAndView("/visa/manager/newSupplementVisaInfo");

    try {

      VisaPrincipalBo bo = visaService.getVisaPrincipal(visaPrincipalId);
      
      String taskCountryCodes = bo.getTemplateCode();
      List<VisaCountryBo> countrys = visaCountryService.queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));

      view.addObject("visaPrincipal", bo);
      view.addObject("country", CollectionUtils.isEmpty(countrys) ? null : countrys.get(0));
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      view.setViewName(ERROR_PAGE);
      view.addObject(ERROR_MSG, "申请签证资料错误：" + e.getMessage());
    }
    return view;
  }
  
  
  
  @RequestMapping(value = "/ajaxListVisaFileFlow", method = RequestMethod.GET)
  @SuppressWarnings("all")
  public ModelAndView ajaxListVisaFileFlow(VisaFileFlowBo bo, HttpServletRequest request)
      throws UnsupportedEncodingException {

    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    List<VisaFileFlowBo> boes = visaFileTemplateService.selectVisaFileFlow(bo);

    view.addObject("rows", boes);

    return view;
  }

  
  @RequestMapping(value = "/updateVisaFileFlowStatus", method = RequestMethod.POST)
  public ModelAndView updateVisaFileFlowStatus(HttpServletRequest request, HttpServletResponse response,VisaFileFlowBo visaFileFlowBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if (visaFileFlowBo.getId() == null) {
      view.addObject(MESSAGE_KEY, "参数错误!!");
      return view;
    }
    try {
      
      // 修改状态
      visaFileTemplateService.updateFileStatus(visaFileFlowBo);
      // 修改visaPrincipal进入审核状态
     
      //修改状态
      //visaService.updateVisaStatus(principal.getId(), VisaStatus.AUDIT_WAIT);
      
      view.addObject(SUCCESS_KEY, true);
    } catch (Exception e) {

      view.addObject(MESSAGE_KEY, "修改签证状态失败!!");
    }
    return view;
  }
  
  @RequestMapping(value = "/updatePassVisaFileFlowStatus", method = RequestMethod.POST)
  public ModelAndView updatePassVisaFileFlowStatus(HttpServletRequest request, HttpServletResponse response,VisaFileFlowBo visaFileFlowBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if (visaFileFlowBo.getId() == null) {
      view.addObject(MESSAGE_KEY, "参数错误!!");
      return view;
    }
    try {
      VisaFileFlowBo vffBo = visaFileTemplateService.selectVisaFileFlowById(visaFileFlowBo.getId());
      // 修改状态
      visaFileTemplateService.updateFileStatus(visaFileFlowBo);
      // 修改visaPrincipal进入审核状态
      boolean allPass = visaFileTemplateService.assertAllFlowPass(vffBo);
      
      if(allPass) {
        LOG.info("签证资料全部审核通过,进入办理中状态");
        visaService.updateVisaStatus(vffBo.getPrincipalId(), VisaStatus.HADNDLING);
      }else{
        LOG.info("签证资料未全部审核通过！！！");
      }
      
      view.addObject(SUCCESS_KEY, true);
    } catch (Exception e) {

      view.addObject(MESSAGE_KEY, "修改签证状态失败!!");
    }
    return view;
  }
  
  
  @RequestMapping(value = "/updateRejectVisaFileFlowStatus", method = RequestMethod.POST)
  public ModelAndView updateRejectVisaFileFlowStatus(HttpServletRequest request, HttpServletResponse response,VisaFileFlowBo visaFileFlowBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if (visaFileFlowBo.getId() == null) {
      view.addObject(MESSAGE_KEY, "参数错误!!");
      return view;
    }
    try {
      
      // 修改状态
      visaFileTemplateService.updateFileStatus(visaFileFlowBo);
      // 修改visaPrincipal进入审核状态
     
      //修改状态
      //visaService.updateVisaStatus(principal.getId(), VisaStatus.AUDIT_WAIT);
      
      view.addObject(SUCCESS_KEY, true);
    } catch (Exception e) {

      view.addObject(MESSAGE_KEY, "修改签证状态失败!!");
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
    
    Integer total = visaService.getNewVisaPrincipalCount(taskPo);
    Integer totalPage = (total + taskPo.getMaxResults() - 1) / taskPo.getMaxResults();
    
    if(totalPage > 0 && taskPo.getPageNo() > totalPage) {
      taskPo.setPageNo(totalPage);
    }
    
    
    view.addObject("total", total);
    view.addObject("pageNo", taskPo.getPageNo());
    view.addObject("rows", visaService.selectNewVisaPrincipalByCondition(taskPo));
    view.addObject("limit", taskPo.getMaxResults());
    view.addObject("start", taskPo.getStart());
    view.addObject(taskPo);
    //统计数量
    return view;
  }
  
  @RequestMapping(value = "/addVisaFileTemplateToCountry", method = RequestMethod.POST)
  @SuppressWarnings("all")
  public ModelAndView addVisaFileTemplateToCountry(Long countryId,Long fileId) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if(countryId == null || countryId < 1) {
      view.addObject(MESSAGE_KEY, "countryId参数错误!!");
      return view;
    }
    
    if(fileId == null || fileId < 1) {
      view.addObject(MESSAGE_KEY, "visaFileTemplateId参数错误!!");
      return view;
    }
    
    //查看是否已经存在
    Long cafId = visaFileTemplateService.selectCountryAndFile(countryId,fileId);
    if(cafId != null) {
      view.addObject(MESSAGE_KEY, "已经存在文件");
      return view;
    }
    visaFileTemplateService.saveCountryAndFile(countryId,fileId);
    view.addObject(SUCCESS_KEY, true);
    //统计数量
    return view;
  }
  
  
  @RequestMapping(value = "/addVisaTextListToCountry", method = RequestMethod.POST)
  @SuppressWarnings("all")
  public ModelAndView addVisaTextListToCountry(Long countryId,String value) throws UnsupportedEncodingException {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if(countryId == null || countryId < 1) {
      view.addObject(MESSAGE_KEY, "countryId参数错误!!");
      return view;
    }
    
    if(Strings.isNullOrEmpty(value)) {
      view.addObject(MESSAGE_KEY, "value参数错误!!");
      return view;
    }
    VisaFileTemplate visaFileTemplate = buildVisaFileTemplate(value,value,"");
    visaFileTemplate.setType(1);
    Long fileId = visaFileTemplateService.save(visaFileTemplate);
    if(fileId == null) {
      view.addObject(MESSAGE_KEY, "保存非标准文件错误!!");
      return view;
    }
    
    //查看是否已经存在
    Long cafId = visaFileTemplateService.selectCountryAndFile(countryId,fileId);
    if(cafId != null) {
      view.addObject(MESSAGE_KEY, "已经存在文件");
      return view;
    }
    visaFileTemplateService.saveCountryAndFile(countryId,fileId);
    view.addObject(SUCCESS_KEY, true);
    //统计数量
    return view;
  }
  
  /**
   * 提交资料
   * @param request
   * @param response
   * @return
   * @throws Exception
   * created by Shaoyuqi on 2017年5月3日 上午6:57:36
   */
  @RequestMapping(value = "/uploadVisaValue", method = RequestMethod.POST)
  @ResponseBody
  public String uploadVisaValue(MultipartHttpServletRequest request, HttpServletResponse response)
      throws Exception {
    MultipartFile fileUpload = request.getFile("Filedata");
    response.setCharacterEncoding("UTF-8");
    String extensionName = null;
    String fileNameLong = null;
    // 文件名
    String fileName = null;
    String url = null;


    // 判断是否有文件
    if (!fileUpload.isEmpty()) {
      // 获取文件上传路径名称
      fileNameLong = fileUpload.getOriginalFilename();
      fileName = fileNameLong.substring(0, fileNameLong.lastIndexOf("."));
      // 获取文件扩展名
      extensionName = fileNameLong.substring(fileNameLong.lastIndexOf(".") + 1);
//      if (("," + FILE_TEMPLATE_Ext.toLowerCase() + ",").indexOf("," + extensionName.toLowerCase()
//          + ",") < 0) {
//        printInfo(response, "不允许上传此类型的文件", null);
//        return null;
//      }
    } else {
      printInfo(response, "上传文件不能为空", null);
      return null;
    }
    
    //判断文件是否存在
    String uuid = UUID.randomUUID().toString();
    url = uuid + "."+extensionName;
    InputStream image = null;
    try {
      image = fileUpload.getInputStream();
      UploadHelper.saveFile(fileUpload, null, url);

      
    } catch (Exception e) {
      LOG.error("上传文件模板错误:" + e.getMessage(), e);
      printInfo(response, "上传文件模板错误", null);
      return null;
    } finally {
      if (image != null) {
        image.close();
      }
    }
    printInfoWithName(response, null, url, fileName);
    return null;
  }
  
  /**
   * 提交签证资料
   * @param countryId
   * @param value
   * @return
   * @throws UnsupportedEncodingException
   * created by Shaoyuqi on 2017年5月7日 上午8:16:27
   */
  @RequestMapping(value = "/submitVisaInfo", method = RequestMethod.POST)
  @SuppressWarnings("all")
  public ModelAndView submitVisaInfo(HttpServletRequest request, HttpServletResponse response,VisaFileFlowBo visaFileFlowBo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, false);
    if (visaFileFlowBo.getCafileId() == null) {
      view.addObject(MESSAGE_KEY, "参数错误!!");
      return view;
    }
    try {
      //查询VisaPrincipal
      VisaPrincipalBo principal = visaService.getVisaPrincipal(visaFileFlowBo.getPersonId(),visaFileFlowBo.getTaskId(),visaFileFlowBo.getCountryCode());
      if(principal == null) {
        view.addObject(ERROR_MSG, "VisaPrincipal记录不存在");
        return view;
      }
      
      visaFileFlowBo.setPrincipalId(principal.getId());
      
      buildVisaFileFlow(visaFileFlowBo);
      // 插入一条记录
      visaFileTemplateService.assertVisaFileFlow(visaFileFlowBo);
      // 修改状态
      visaFileTemplateService.updateFileStatus(visaFileFlowBo);
      // 修改visaPrincipal进入审核状态
      
     // 修改visaPrincipal进入审核状态
      boolean allPass = visaFileTemplateService.assertAllFlowAudit(visaFileFlowBo);
      
      if(allPass) {
        LOG.info("签证资料全部进入待审状态,签证流程进入待审核状态");
        visaService.updateVisaStatus(principal.getId(), VisaStatus.AUDIT_WAIT);
      }else{
        LOG.info("签证资料未全部待审状态！！！");
      }
      //修改状态,此时只有一条进入审核状态,如果全部审核通过，则进入办理状态
      //只有全部状态都变成大于提交状态的时候，principal的状态才能进入审核
      //visaService.updateVisaStatus(principal.getId(), VisaStatus.AUDIT_WAIT);
      
      view.addObject(SUCCESS_KEY, true);
    } catch (Exception e) {

      view.addObject(MESSAGE_KEY, "修改签证状态失败!!");
    }
    return view;
  }
  
  
  
  
  @RequestMapping(value = "/deleteTV", method = RequestMethod.GET)
  public ModelAndView delete(Long countryId,Long fileId) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      VisaFileTemplateBo vftBo = visaFileTemplateService.selectVisaFileTemplateBoById(fileId);
      if(vftBo == null) {
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, "文件模板不存在");
        return view;
      }
      
      //文件模板
      if(FileType.FILE_TEMPLATE.getCode() == vftBo.getType()) {
        visaFileTemplateService.deleteCountryAndFile(countryId,fileId);
      }
      //非标准文件模板
      else if(FileType.TEXT_VALUE.getCode() == vftBo.getType()) {
        visaFileTemplateService.deleteTextValue(countryId,fileId);
      }else{
        view.addObject(SUCCESS_KEY, false);
        view.addObject(MESSAGE_KEY, "模板类型非法");
      }
    } catch (Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "删除非标准文件模板错误 "+e.getMessage());
    }

    return view;
  }

}
