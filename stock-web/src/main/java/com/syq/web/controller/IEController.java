package com.syq.web.controller;

import static com.syq.web.common.ErrorCode.ACCOUNT_ADD;
import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.syq.biz.bo.IEPassportBo;
import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.IETaskBo;
import com.syq.biz.bo.PassportBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.domain.Passport;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.impl.PassportService;
import com.syq.biz.service.impl.TaskService;
import com.syq.biz.service.impl.VisaCountryService;
import com.syq.pagination.common.PassportPo;
import com.syq.pagination.common.TaskPo;
import com.syq.util.constant.CostSourceType;
import com.syq.web.helper.IEPassportHelper;
import com.syq.web.helper.IETaskHelper;

@Controller
public class IEController extends BaseController {

  private static final long maxSize = 10000000l;
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(IEController.class);

  private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  private static final String[] headers = {"批件号", "任务名称", "团长姓名", "团长身份证号", "组团单位", "出差开始时间",
      "出差结束时间", "出访国家", "出访目的", "费用来源", "出批件日期", "是否有集团领导班子成员", "是否列入外事计划", "经办人"};

  private static final String[] passportHeader = {"护照编码", "姓名", "性别(男、女)", "身份证号",
      "颁发日期", "过期日期", "籍贯地址", "注册地址"};


  @Autowired
  private VisaCountryService visaCountryService;

  @Autowired
  private TaskService taskService;
  @Autowired
  private PassportService passportService;

  @RequestMapping(value = "/task/testData")
  public ModelAndView testData() {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);

    try {
      List<Object> test = new ArrayList<Object>();
      view.addObject("rows", test);
    } catch (Exception e) {
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, ACCOUNT_ADD);
    }
    return view;
  }


  @RequestMapping(value = "/task/upload")
  @ResponseBody
  public Object upload(MultipartHttpServletRequest request, HttpServletResponse response) {
    MultipartFile file = request.getFile("Filedata");
    response.setCharacterEncoding("UTF-8");
    Map<String, Object> result = new HashMap<String, Object>();
    Long fileSize = file.getSize();
    if (fileSize > maxSize) {
      result.put("msg", "文件大小最大不能超过" + maxSize + "字节!!!");
      return result;
    }
    XSSFWorkbook hssfWorkbook = null;
    try {
      hssfWorkbook = new XSSFWorkbook(file.getInputStream());
    } catch (IOException e) {
      result.put("msg", "解析xlsx错误!!!");
      return result;
    }
    // 如果没有的或者空的单元格则创建一个空的
    hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
    // 处理第一个工作表
    Sheet sheet = hssfWorkbook.getSheetAt(0);
    // 获取最后一个行号，用来分配任务
    int rowNum = sheet.getLastRowNum();
    if (rowNum > 1000) {
      result.put("msg", "不能大于一千条数据!!!");
      return result;
    }
    // 判断上传的文件是否有问题
    // 处理
    try {
      List<IETaskBo> list = new ArrayList<IETaskBo>();
      boolean ifContinue = IETaskHelper.processImportTask(sheet, list);
      if (ifContinue) {
        taskService.batchProcessTask(list, getManager());
      }
      result.put("list", list);
      result.put(SUCCESS_KEY, true);
    } catch (Exception e) {
      result.put(MESSAGE_KEY, e.getMessage());
      return result;
    }
    return result;
  }



  @RequestMapping(value = "/task/download")
  public void upload(TaskPo taskPo, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/xlsx;charset=utf-8");
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=" + URLEncoder.encode("task.xlsx", "UTF-8");
    response.setHeader(headerKey, headerValue);

    // 创建工作文档对象
    Workbook wb = new XSSFWorkbook();
    // 创建sheet对象
    Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
    // 写入头
    Row header = (Row) sheet1.createRow(0);
    for (int i = 0; i < headers.length; i++) {

      Cell cell = header.createCell(i);
      cell.setCellValue(headers[i]);
    }

    taskPo.setMaxResults(1000000);
    List<TaskBo> taskBoes = taskService.query(taskPo);
    // 循环写入行数据
    // {"批件号","任务名称" ,"团长姓名","团长身份证号","组团单位","出差开始时间", "出差结束时间","出访国家" ,"出访目的","费用来源","出批件日期"
    // ,"是否有集团领导班子成员","是否列入外事计划","经办人"
    TaskBo bo = null;
    CellStyle style = wb.createCellStyle();
    DataFormat dataFormat = wb.createDataFormat();
    style.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
    
    for (int i = 0; i < taskBoes.size(); i++) {
      int j = 0;
      Row row = (Row) sheet1.createRow(i + 1);
      bo = taskBoes.get(i);
      // 批件号
      row.createCell(j++).setCellValue(bo.getInstructionNo());
      // 任务名称
      row.createCell(j++).setCellValue(bo.getTaskName());
      // 团长姓名
      row.createCell(j++).setCellValue(bo.getHeaderName());
      // 团长身份证号
      row.createCell(j++).setCellValue(bo.getHeaderIdCard());
      // 组团单位
      row.createCell(j++).setCellValue(bo.getGroupUnit());
      // 出差开始时间
      Cell taskBeginTimeCell = row.createCell(j++);
      taskBeginTimeCell.setCellStyle(style);
      if (bo.getTaskBeginTime() != null){
        taskBeginTimeCell.setCellValue(bo.getTaskBeginTime());
      }

      // 出差结束时间
      Cell taskEndTimeCell = row.createCell(j++);
      taskEndTimeCell.setCellStyle(style);
      if (bo.getTaskEndTime() != null){
      taskEndTimeCell.setCellValue(bo.getTaskEndTime());
      }
      // 出访国家
      row.createCell(j++).setCellValue(getTaskCountryName(bo.getTaskCountry(), visaCountryService));
      // 出访目的
      row.createCell(j++).setCellValue(bo.getVisitPurpose());
      // 费用来源
//      CostSourceType costSourceType = CostSourceType.from(Integer.parseInt(bo.getCostSource()));
      row.createCell(j++).setCellValue(bo.getCostSource());
      
      // 出批件日期
      Cell insTimeCell = row.createCell(j++);
      insTimeCell.setCellStyle(style);
      if (bo.getInstructionTime() != null){
      insTimeCell.setCellValue(bo.getInstructionTime());
      }
      // 是否有集团领导班子成员
      row.createCell(j++).setCellValue(bo.getHasGroupMember() == 0 ? "否" : "是");
      // 是否列入外事计划
      row.createCell(j++).setCellValue(bo.getHasFaffPlan());
      // 经办人
      row.createCell(j++).setCellValue(bo.getTransactor());

    }
    // 创建文件流
    // 写入数据
    wb.write(response.getOutputStream());
    // 关闭文件流
  }


  @RequestMapping(value = "/person/upload")
  @ResponseBody
  public Object personsUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
    MultipartFile file = request.getFile("Filedata");
    response.setCharacterEncoding("UTF-8");
    Map<String, Object> result = new HashMap<String, Object>();
    Long fileSize = file.getSize();
    if (fileSize > maxSize) {
      result.put("msg", "文件大小最大不能超过" + maxSize + "字节!!!");
      return result;
    }
    XSSFWorkbook hssfWorkbook = null;
    try {
      hssfWorkbook = new XSSFWorkbook(file.getInputStream());
    } catch (IOException e) {
      result.put("msg", "解析xlsx错误!!!");
      return result;
    }
    // 如果没有的或者空的单元格则创建一个空的
    hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
    // 处理第一个工作表
    Sheet sheet = hssfWorkbook.getSheetAt(0);
    // 获取最后一个行号，用来分配任务
    int rowNum = sheet.getLastRowNum();
    if (rowNum > 1000) {
      result.put("msg", "不能大于一千条数据!!!");
      return result;
    }
    // 判断上传的文件是否有问题
    // 处理
    try {
      List<IEPersonBo> list = new ArrayList<IEPersonBo>();
      boolean ifContinue = IETaskHelper.processImportTaskPersons(sheet, list);
      if (ifContinue) {
        taskService.batchProcessPersons(list, 0l, getManager());
      }
      result.put("list", list);
      result.put(SUCCESS_KEY, true);
    } catch (Exception e) {
      result.put(MESSAGE_KEY, e.getMessage());
      return result;
    }
    return result;
  }



  @RequestMapping(value = "/passport/upload")
  @ResponseBody
  public Object passportUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
    MultipartFile file = request.getFile("Filedata");
    response.setCharacterEncoding("UTF-8");
    Map<String, Object> result = new HashMap<String, Object>();
    Long fileSize = file.getSize();
    if (fileSize > maxSize) {
      result.put("msg", "文件大小最大不能超过" + maxSize + "字节!!!");
      return result;
    }
    XSSFWorkbook hssfWorkbook = null;
    try {
      hssfWorkbook = new XSSFWorkbook(file.getInputStream());
    } catch (IOException e) {
      result.put("msg", "解析xlsx错误!!!");
      return result;
    }
    // 如果没有的或者空的单元格则创建一个空的
    hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
    // 处理第一个工作表
    Sheet sheet = hssfWorkbook.getSheetAt(0);
    // 获取最后一个行号，用来分配任务
    int rowNum = sheet.getLastRowNum();
    if (rowNum > 1000) {
      result.put("msg", "不能大于一千条数据!!!");
      return result;
    }
    // 判断上传的文件是否有问题
    // 处理
    try {
      List<IEPassportBo> list = new ArrayList<IEPassportBo>();
      boolean ifContinue = IEPassportHelper.processImportPassport(sheet, list);
      if (ifContinue) {
        passportService.batchProcessPassport(list, getManager());
      }
      result.put("list", list);
      result.put(SUCCESS_KEY, true);
    } catch (Exception e) {
      LOG.error("批量导入护照错误",e);
      result.put(MESSAGE_KEY, e.getMessage());
      return result;
    }
    return result;
  }


  @RequestMapping(value = "/passport/download")
  public void passportDownload(PassportPo passportPo, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/xlsx;charset=utf-8");
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=" + URLEncoder.encode("passport.xlsx", "UTF-8");
    response.setHeader(headerKey, headerValue);

    // 创建工作文档对象
    Workbook wb = new XSSFWorkbook();
    // 创建sheet对象
    Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
    // 写入头
    Row header = (Row) sheet1.createRow(0);
    for (int i = 0; i < passportHeader.length; i++) {

      Cell cell = header.createCell(i);

      cell.setCellValue(passportHeader[i]);
    }

    passportPo.setMaxResults(1000000);
    passportPo.setStatus(new Integer[] {4, 5});
    List<Passport> passportBoes = passportService.query(passportPo);
    // 循环写入行数据
    // "护照编码","姓" ,"名","性别(男、女)","身份证号","颁发日期(日期类型)", "过期日期(日期类型)","籍贯地址" ,"注册地址"
    Passport bo = null;
    for (int i = 0; i < passportBoes.size(); i++) {
      int j = 0;
      Row row = (Row) sheet1.createRow(i + 1);
      bo = passportBoes.get(i);
      // 护照编码
      row.createCell(j++).setCellValue(bo.getPassportNo());
      // 姓名
      row.createCell(j++).setCellValue(bo.getName());
      // 性别(男、女)
      String sexStr = "男";
      if(bo.getSex() == 1) {
        sexStr = "女";
      }
      row.createCell(j++).setCellValue(sexStr);
      // 身份证号
      row.createCell(j++).setCellValue(bo.getIdNumber());

      // 颁发日期 过期日期
      Cell dateIssueCell = row.createCell(j++);

      CellStyle style = wb.createCellStyle();
      DataFormat dataFormat = wb.createDataFormat();
      style.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
      dateIssueCell.setCellStyle(style);

      // 颁发日期(日期类型)
      dateIssueCell.setCellValue(bo.getDateIssue());

      Cell dateExpireCell = row.createCell(j++);
      dateIssueCell.setCellStyle(style);
      // 过期日期(日期类型)
      dateExpireCell.setCellStyle(style);
      dateExpireCell.setCellValue(bo.getDateExpire());
      // 籍贯地址
      row.createCell(j++).setCellValue(bo.getPlaceBirth());
      // 注册地址
      row.createCell(j++).setCellValue(bo.getPlaceIssue());

    }
    // 创建文件流
    // 写入数据
    wb.write(response.getOutputStream());
    // 关闭文件流
  }



}
