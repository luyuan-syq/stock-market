package com.syq.web.vo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.syq.word.entity.WordPersons;

public class TaskWordVo {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskWordVo.class);
  
  private String instructionNo;
  
  private String headerName;
  
  private String deptName;
  
  private String taskDescription;
  
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
  
  private List<WordPersons> personList;

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

  public List<WordPersons> getPersonList() {
    return personList;
  }

  public void setPersonList(List<WordPersons> personList) {
    this.personList = personList;
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

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }
  
  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public static TaskWordVo getNewInstance(){
    
    List<WordPersons> personList = Lists.newArrayList();
    WordPersons wp1 = new WordPersons();
    wp1.setUserName("荆雷");
    wp1.setIdNumber("12345678");
    wp1.setDeptName("网众");
    
    WordPersons wp2 = new WordPersons();
    wp2.setUserName("邵玉琪");
    wp2.setIdNumber("87654321");
    wp2.setDeptName("安信");
    
    personList.add(wp1);
    personList.add(wp2);
    
    
    TaskWordVo vo = new TaskWordVo();
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
    vo.setPersonList(personList); 
    return vo;
  }
 
  public Map<String, Object> converToWordMap() throws IllegalArgumentException, IllegalAccessException{
    Map<String, Object> resultMap = Maps.newHashMap();
    Field fields[] = this.getClass().getDeclaredFields();
    
    for (int i=0;i<fields.length;i++){
      Field f = fields[i];
      f.setAccessible(true);
      Object o = f.get(this);

      resultMap.put(f.getName(), o == null || StringUtils.isBlank(o.toString())? '无' : o);
    }
    return resultMap;
  }
}
