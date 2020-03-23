package com.syq.pagination.common;

import java.util.Date;


public class TaskCodePo extends PaginationPo{
  
  private String taskCode;
  
  private Date createBeginTime;
  
  private Date createEndTime;
  
  private int status;
  
  private String userName;

  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  public Date getCreateBeginTime() {
    return createBeginTime;
  }

  public void setCreateBeginTime(Date createBeginTime) {
    this.createBeginTime = createBeginTime;
  }

  public Date getCreateEndTime() {
    return createEndTime;
  }

  public void setCreateEndTime(Date createEndTime) {
    this.createEndTime = createEndTime;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  
}
