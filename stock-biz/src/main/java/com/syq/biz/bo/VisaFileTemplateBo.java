package com.syq.biz.bo;

import java.util.Date;

public class VisaFileTemplateBo {
  
  private Long cafileId;
  
  private int flowStatus;
  
  private String flowMsg;
  
  private Long id;

  private String name;

  private String url;

  private String description;

  private Date createTime;

  private String operatorId;

  private Date operatorTime;
  
  private int type;

  public Long getCafileId() {
    return cafileId;
  }

  public void setCafileId(Long cafileId) {
    this.cafileId = cafileId;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
  
  
  
}
