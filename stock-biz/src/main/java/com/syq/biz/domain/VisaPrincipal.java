package com.syq.biz.domain;

import java.util.Date;
import java.util.List;


public class VisaPrincipal {

  private long id;

  private Date createTime;

  private String operatorId;

  private Date operatorTime;

  private int status;
  
  private long taskId;
  
  private long personId;
  
  private int flowStatus;
  
  private String flowMsg;
  
  private String templateCode;
  
  private List<VisaAttribute> attributes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public List<VisaAttribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<VisaAttribute> attributes) {
    this.attributes = attributes;
  }

  public long getTaskId() {
    return taskId;
  }

  public void setTaskId(long taskId) {
    this.taskId = taskId;
  }

  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
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

  public String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }
  
  
}
