package com.syq.pagination.common;

import java.util.Date;

public class ManagerPo extends PaginationPo {

  private String name;

  private String password;

  private Date createBeginTime;

  private Date createEndTime;

  private String operatorId;

  private Date operatorTime;

  private Date lastLoginTime;

  private String contactPhone;

  private String mobile;

  private String contactEmail;

  private int status;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
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

}
