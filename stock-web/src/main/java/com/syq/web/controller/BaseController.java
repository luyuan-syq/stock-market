package com.syq.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.domain.Manager;
import com.syq.biz.service.IVisaCountryService;
import com.syq.exception.SYQException;
import com.syq.web.utils.LoginConstants;

public class BaseController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger L = LoggerFactory.getLogger(BaseController.class);

  private static String AJAX_HEADER_CONTENT_TYPE = "application/json";

  /**
   * bean可以接受Date类型参数
   * 
   * @return
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // 允许为空
  }

  /** 基于@ExceptionHandler异常处理 */
  // @ExceptionHandler
  // public void exceptionHandler(HttpServletRequest request,HttpServletResponse response, Exception
  // ex) {
  //
  // try {
  //
  // response.setContentType(AJAX_HEADER_CONTENT_TYPE);
  // response.setStatus(HttpServletResponse.SC_OK);
  //
  // PrintWriter printWriter = response.getWriter();
  // printWriter.print(String.format("{\"status\":\"%s\",\"wolongError\":%s,\"success\":%b}",
  // "error",ex.getMessage(), false));
  // printWriter.flush();
  // } catch (IOException e) {
  // L.error("@ExceptionHandler统一处理异常错误错误", e);
  // throw new SYQException("系统内部错误，原因" + e);
  // }
  // }

  protected String getTaskCountryName(String taskCountryCodes,
      IVisaCountryService visaCountryService) {
    String taskCountryName = "";
    String[] codes = taskCountryCodes.split(",");
    List<String> codeList = new ArrayList<String>();
    for (String code : codes) {
      codeList.add(code.trim());
    }
    List<VisaCountryBo> visaCountryBoList =
        visaCountryService.queryVisaCountryNamesForCode(codeList);
    StringBuilder builders = new StringBuilder();
    String countryNames = "";
    for (VisaCountryBo cisaCountryBo : visaCountryBoList) {
      builders.append(cisaCountryBo.getName());
      builders.append(",");
    }
    countryNames = builders.toString();
    if (countryNames.length() > 1) {
      taskCountryName = countryNames.substring(0, countryNames.length() - 1);
    }
    return taskCountryName;
  }
  
  protected List<VisaCountryBo> getVisaCountrys(String taskCountryCodes,
      IVisaCountryService visaCountryService) {
    String[] codes = taskCountryCodes.split(",");
    List<String> codeList = new ArrayList<String>();
    for (String code : codes) {
      codeList.add(code.trim());
    }
    List<VisaCountryBo> visaCountryBoList =
        visaCountryService.queryVisaCountryNamesForCode(codeList);
    
    return visaCountryBoList;
  }

  protected String dateToString(Date time) {
    if(time == null) {
      return null;
    }
    SimpleDateFormat formatter;
    formatter = new SimpleDateFormat("yyyy-MM-dd");
    String ctime = formatter.format(time);
    return ctime;
  }
  
  protected Manager getManager() {
    HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    return (Manager)session.getAttribute(LoginConstants.SESSION_MANAGER);
  }
  
  protected String getLoginName() {
    HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    Manager manager = (Manager)session.getAttribute(LoginConstants.SESSION_MANAGER);
    return manager.getName();
  }
  
  protected void countNum(HttpServletRequest request,String type,Integer total) {
    //统计数量
    HttpSession session = request.getSession();
    session.setAttribute(type,total );
  }

}
