package com.syq.biz.bo;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskWordBo {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskWordBo.class);
  
  private static Calendar calendar =Calendar.getInstance();
  
  private String taskCode;
  
  private String instructionNo;
  
  private String headerName;
  
  private String deptName;
  
  private String year;
  
  private String userName;
  
  private String personCount;
  
  private String beginYear;
  
  private String beginMonth;
  
  private String beginDay;
  
  private String endYear;
  
  private String endMonth;
  
  private String endDay;
  
  private String taskCountry;
  
  private String taskName;
  
  private String stickDay;
  
  private String costSource;

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPersonCount() {
    return personCount;
  }

  public void setPersonCount(String personCount) {
    this.personCount = personCount;
  }

  public String getBeginYear() {
    return beginYear;
  }

  public void setBeginYear(String beginYear) {
    this.beginYear = beginYear;
  }

  public String getBeginMonth() {
    return beginMonth;
  }

  public void setBeginMonth(String beginMonth) {
    this.beginMonth = beginMonth;
  }

  public String getBeginDay() {
    return beginDay;
  }

  public void setBeginDay(String beginDay) {
    this.beginDay = beginDay;
  }

  public String getEndYear() {
    return endYear;
  }

  public void setEndYear(String endYear) {
    this.endYear = endYear;
  }

  public String getEndMonth() {
    return endMonth;
  }

  public void setEndMonth(String endMonth) {
    this.endMonth = endMonth;
  }

  public String getEndDay() {
    return endDay;
  }

  public void setEndDay(String endDay) {
    this.endDay = endDay;
  }

  public String getTaskCountry() {
    return taskCountry;
  }

  public void setTaskCountry(String taskCountry) {
    this.taskCountry = taskCountry;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getStickDay() {
    return stickDay;
  }

  public void setStickDay(String stickDay) {
    this.stickDay = stickDay;
  }

  public String getCostSource() {
    return costSource;
  }

  public void setCostSource(String costSource) {
    this.costSource = costSource;
  }
  
  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }

  public String getHeaderName() {
    return headerName;
  }

  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }
  
  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  public static TaskWordBo getNewInstance(){
       
    TaskWordBo vo = new TaskWordBo();
    vo.setYear("2017");
    vo.setUserName("荆雷");
    vo.setPersonCount("3");
    vo.setBeginYear("2016");
    vo.setBeginMonth("07");
    vo.setBeginDay("07");
    vo.setEndYear("2017");
    vo.setEndMonth("08");
    vo.setEndDay("08");
    vo.setTaskCountry("中国,日本");
    vo.setTaskName("巡查");
    vo.setStickDay("5");
    vo.setCostSource("自己");
    return vo;
  }

  public static TaskWordBo converTo(TaskBo bo){
     TaskWordBo wordBo = getNewInstance();
     if (bo == null){
       return wordBo;
     }
     
     wordBo.setTaskCode(bo.getTaskCode());
     wordBo.setInstructionNo(bo.getInstructionNo());
     wordBo.setYear(truncateDateToYMDString(new Date()));
     wordBo.setUserName(bo.getAccountName());
     wordBo.setTaskCountry(bo.getTaskCountry());
     wordBo.setTaskName(bo.getTaskName());
     wordBo.setCostSource(bo.getCostSource());
     
     String[] beginTime = truncateDateToYMD(bo.getTaskBeginTime());
     wordBo.setBeginYear(beginTime[0]);
     wordBo.setBeginMonth(beginTime[1]);
     wordBo.setBeginDay(beginTime[2]);
     
     String[] endTime = truncateDateToYMD(bo.getTaskEndTime());
     wordBo.setEndYear(endTime[0]);
     wordBo.setEndMonth(endTime[1]);
     wordBo.setEndDay(endTime[2]);
     return wordBo;
  }
  
  private static String[] truncateDateToYMD(Date date){
    String[] s = new String[3];
    if (date == null){
      return s;     
    }
    
    calendar.setTime(date);
    s[0] = String.valueOf(calendar.get(Calendar.YEAR));
    s[1] = String.valueOf(calendar.get(Calendar.MONTH));
    s[2] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    return s;
  }
  
  private static String truncateDateToYMDString(Date date){
    if (date == null){
      return "";     
    }
    
    calendar.setTime(date);
    
    return String.valueOf(calendar.get(Calendar.YEAR)) + " 年 " 
        + String.valueOf(calendar.get(Calendar.MONTH) + 1) + " 月 "
        + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + " 日";
  }
  
}
