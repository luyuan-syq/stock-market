package com.syq.web.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class TaskVo implements Serializable {
  
  /**
   * 
   * created by Shaoyuqi on 2016年8月28日 上午8:29:59
   */
  private static final long serialVersionUID = -9089088108221612481L;
  
  private Long id;
   
  private String instructionNo;
  
  private String taskName;
  
  private String headerName;
  
  private String headerIdCard;
  
  private Date taskBeginTime;
  
  private Date taskEndTime;
  
  private String taskDescription;
  
  private String taskCountry;
  
  private String operatorId;
  
  private Date operatorTime;
  
  private int status;
  
  private String taskCode;
  
  private Date createTime;
  
  private String faffPlanNo;
  
  private int hasGroupMember;
  
  private String costSource;
  
  private List<TaskCountryVo> taskCountrys;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getHeaderName() {
    return headerName;
  }

  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  public String getHeaderIdCard() {
    return headerIdCard;
  }

  public void setHeaderIdCard(String headerIdCard) {
    this.headerIdCard = headerIdCard;
  }

  public Date getTaskBeginTime() {
    return taskBeginTime;
  }

  public void setTaskBeginTime(Date taskBeginTime) {
    this.taskBeginTime = taskBeginTime;
  }

  public Date getTaskEndTime() {
    return taskEndTime;
  }

  public void setTaskEndTime(Date taskEndTime) {
    this.taskEndTime = taskEndTime;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  public String getTaskCountry() {
    return taskCountry;
  }

  public void setTaskCountry(String taskCountry) {
    this.taskCountry = taskCountry;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  public Date getOperatorTime() {
    return operatorTime;
  }

  public void setOperatorTime(Date operatorTime) {
    this.operatorTime = operatorTime;
  }

  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }

  public String getFaffPlanNo() {
    return faffPlanNo;
  }

  public void setFaffPlanNo(String faffPlanNo) {
    this.faffPlanNo = faffPlanNo;
  }

  public List<TaskCountryVo> getTaskCountrys() {
    return taskCountrys;
  }

  public void setTaskCountrys(List<TaskCountryVo> taskCountrys) {
    this.taskCountrys = taskCountrys;
  }

  public int getHasGroupMember() {
    return hasGroupMember;
  }

  public void setHasGroupMember(int hasGroupMember) {
    this.hasGroupMember = hasGroupMember;
  }

  public String getCostSource() {
    return costSource;
  }

  public void setCostSource(String costSource) {
    this.costSource = costSource;
  }  
}
