package com.syq.biz.bo;

import java.util.Date;

public class TaskCodeBo {
  
  private long id;
  
  private String taskCode;
  
  private Date createTime;
  
  private String operatorId;
  
  private Date operatorTime;
  
  private int status;
  
  private String userName;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
