package com.syq.biz.domain;

import java.util.Date;

/**
 * 快递单表
 * 
 * created by Shaoyuqi on 2017年3月12日 下午7:04:23
 */
public class ExpressDelivery extends BaseBean{
  
  private Long id;
  
  private Long taskId;
  
  private String number;
  
  private String company;
  
  private String receiver;
  
  private String address;
  
  private int status;
  
  private String currentAddress;
  
  private Date createTime; 
  
  private String operatorId;
  
  private Date operatorTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getCurrentAddress() {
    return currentAddress;
  }

  public void setCurrentAddress(String currentAddress) {
    this.currentAddress = currentAddress;
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
  
}
