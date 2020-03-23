package com.syq.biz.bo;

import java.util.Date;
import java.util.List;

public class VisaFileFlowBo {
 
  private int flowStatus;
  
  private String flowMsg;
  
  private Long id;
  
  private Long cafileId;


  private Date createTime;

  private String operatorId;

  private Date operatorTime;
  
  private Long principalId;
  
  private Long taskId;
  
  private Long personId;
  
  private Long countryId;
  
  private String countryCode;
  
  private List<Integer> flowStatusArray;
  
  private String name;
  
  private String url;
  
  private Integer type;
  
  private String flowStatusValue;
  
  
  public String getFlowStatusValue() {
    return flowStatusValue;
  }

  public void setFlowStatusValue(String flowStatusValue) {
    this.flowStatusValue = flowStatusValue;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
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

  public Long getCafileId() {
    return cafileId;
  }

  public void setCafileId(Long cafileId) {
    this.cafileId = cafileId;
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

  public Long getPrincipalId() {
    return principalId;
  }

  public void setPrincipalId(Long principalId) {
    this.principalId = principalId;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public List<Integer> getFlowStatusArray() {
    return flowStatusArray;
  }

  public void setFlowStatusArray(List<Integer> flowStatusArray) {
    this.flowStatusArray = flowStatusArray;
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
  
  
}
