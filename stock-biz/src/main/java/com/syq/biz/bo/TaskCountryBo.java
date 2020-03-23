package com.syq.biz.bo;

import java.util.Date;

public class TaskCountryBo {
  
  private Long id;
  
  private String taskCode;
  
  private String instructionNo;
  
  private String taskCountry;
  
  private String countryName;
  
  private int expectStickDay;
  
  private String comment;
  
  private Date createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }

  public String getTaskCountry() {
    return taskCountry;
  }

  public void setTaskCountry(String taskCountry) {
    this.taskCountry = taskCountry;
  }

  public int getExpectStickDay() {
    return expectStickDay;
  }

  public void setExpectStickDay(int expectStickDay) {
    this.expectStickDay = expectStickDay;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public static TaskCountryBo getNewInstance(String taskCode, String instructionNo){
    TaskCountryBo bo = new TaskCountryBo();
    bo.setTaskCode(taskCode);
    bo.setInstructionNo(instructionNo);
    bo.setCreateTime(new Date());
    return bo;
  }
}
