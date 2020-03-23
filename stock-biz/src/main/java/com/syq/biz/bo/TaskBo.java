package com.syq.biz.bo;

import java.util.Date;
import java.util.List;

import com.syq.util.constant.TaskStatus;


public class TaskBo {
  
  private Long id;
  
  private String taskName;
  
  private String instructionNo;
  
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
  
  private String taskCountryName;
  
  private List<VisaCountryBo> countrys;
  
  private String taskBeginTimeStr;
  
  private String taskEndTimeStr;
  
  private String groupUnit;
  
  private String visitPurpose;
  
  private String costSource;
  
  private Date instructionTime;
  
  private int hasGroupMember;
  
  private String hasFaffPlan;
  
  private String transactor;
  
  private int flowStatus;
  
  private String statusStr;
  
  private String flowMsg;
  
  private String userName;
  
  private String idNumber;
  
  private long visaPrincipalId;
    
  private String personId;
  
  private String accountName;
  
  private String faffPlanNo;
  
  private List<TaskCountryBo> taskCountrys;
  
  private String taskCountryDay;
  
  private String deptName;
  
  private String mobile;
  
  private String contactEmail;
  
  private String countryName;
  
  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getGroupUnit() {
    return groupUnit;
  }

  public void setGroupUnit(String groupUnit) {
    this.groupUnit = groupUnit;
  }

  public String getVisitPurpose() {
    return visitPurpose;
  }

  public void setVisitPurpose(String visitPurpose) {
    this.visitPurpose = visitPurpose;
  }

  public String getCostSource() {
    return costSource;
  }

  public void setCostSource(String costSource) {
    this.costSource = costSource;
  }

  public Date getInstructionTime() {
    return instructionTime;
  }

  public void setInstructionTime(Date instructionTime) {
    this.instructionTime = instructionTime;
  }

  public int getHasGroupMember() {
    return hasGroupMember;
  }

  public void setHasGroupMember(int hasGroupMember) {
    this.hasGroupMember = hasGroupMember;
  }

  public String getHasFaffPlan() {
    return hasFaffPlan;
  }

  public void setHasFaffPlan(String hasFaffPlan) {
    this.hasFaffPlan = hasFaffPlan;
  }

  public String getTransactor() {
    return transactor;
  }

  public void setTransactor(String transactor) {
    this.transactor = transactor;
  }

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

  public String getTaskCountryName() {
    return taskCountryName;
  }

  public void setTaskCountryName(String taskCountryName) {
    this.taskCountryName = taskCountryName;
  }

  public String getTaskBeginTimeStr() {
    return taskBeginTimeStr;
  }

  public void setTaskBeginTimeStr(String taskBeginTimeStr) {
    this.taskBeginTimeStr = taskBeginTimeStr;
  }

  public String getTaskEndTimeStr() {
    return taskEndTimeStr;
  }

  public void setTaskEndTimeStr(String taskEndTimeStr) {
    this.taskEndTimeStr = taskEndTimeStr;
  }
  
  public void buildStatus(TaskStatus status){
    this.status = status.getCode();
    this.operatorTime = new Date();
  }

  public List<VisaCountryBo> getCountrys() {
    return countrys;
  }

  public void setCountrys(List<VisaCountryBo> countrys) {
    this.countrys = countrys;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }

  public int getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(int flowStatus) {
    this.flowStatus = flowStatus;
  }

  public String getFlowMsg() {
    return flowMsg;
  }

  public void setFlowMsg(String flowMsg) {
    this.flowMsg = flowMsg;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public long getVisaPrincipalId() {
    return visaPrincipalId;
  }

  public void setVisaPrincipalId(long visaPrincipalId) {
    this.visaPrincipalId = visaPrincipalId;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getFaffPlanNo() {
    return faffPlanNo;
  }

  public void setFaffPlanNo(String faffPlanNo) {
    this.faffPlanNo = faffPlanNo;
  }

  public List<TaskCountryBo> getTaskCountrys() {
    return taskCountrys;
  }

  public void setTaskCountrys(List<TaskCountryBo> taskCountrys) {
    this.taskCountrys = taskCountrys;
  }

  public String getTaskCountryDay() {
    return taskCountryDay;
  }

  public void setTaskCountryDay(String taskCountryDay) {
    this.taskCountryDay = taskCountryDay;
  }

  public String getStatusStr() {
    return statusStr;
  }

  public void setStatusStr(String statusStr) {
    this.statusStr = statusStr;
  }
}
