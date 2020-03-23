package com.syq.pagination.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaskPo extends PaginationPo{
  
  private String taskCode;
  
  private String taskName;
  
  private Date taskBeginTime;
  
  private Date taskEndTime;
  
  private int status;
  
  private String headerName;
  
  private String taskCountry;
  
  private int waitAuditCount;
  
  private Integer flowStatus = -1;
  
  private String instructionNo;
  
  private List<Integer> searchStatus = new ArrayList<Integer>();
  
  private Long taskId;

  private String accountName;
  
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getHeaderName() {
    return headerName;
  }

  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  public String getTaskCountry() {
    return taskCountry;
  }

  public void setTaskCountry(String taskCountry) {
    this.taskCountry = taskCountry;
  }

  public int getWaitAuditCount() {
    return waitAuditCount;
  }

  public void setWaitAuditCount(int waitAuditCount) {
    this.waitAuditCount = waitAuditCount;
  }
 
  public Integer getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(Integer flowStatus) {
    this.flowStatus = flowStatus;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }
  
  public static TaskPo getNewInstance(){
    return new TaskPo();
  }

  public List<Integer> getSearchStatus() {
    return searchStatus;
  }

  public void setSearchStatus(List<Integer> searchStatus) {
    this.searchStatus = searchStatus;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
 
}