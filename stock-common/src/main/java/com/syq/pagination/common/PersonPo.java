package com.syq.pagination.common;

import java.util.Date;

public class PersonPo extends PaginationPo{
  
  private String userName;

  private Date createBeginTime;
  
  private Date createEndTime;

  private String operatorId;

  private Date operatorTime;

  private String contactPhone;

  private String mobile;

  private String contactEmail;
  
  private String deptName;
  
  private Long taskId;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
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

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
}
