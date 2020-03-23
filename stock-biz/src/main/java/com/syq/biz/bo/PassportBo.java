/**
 * com.syq.biz.bo.PassportBo.java created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日
 * 下午1:40:36
 */
package com.syq.biz.bo;

import java.util.Date;

/**
 * 
 * created by Huangye on 2016年8月28日 下午1:40:36
 */
public class PassportBo {
  
  private Long id;
  private Long taskId;
  private String taskCode;
  private String passportNo;
  private String passportType;
  private String countryCode;
  private String name;
  private Integer sex;
  private Date dataBirth;
  private String placeBirth;
  private String placeIssue;
  private Date dateIssue;
  private Date dateExpire;
  private String anthority;
  private String idNumber;
  private Integer passportStatus;
  private Integer flowStatus;
  private String flowMsg;
  private Date createTime;
  private Date operatorTime;
  private String operatorId;
  
  private String userName;
  
  private String taskName;
  
  private String instructionNo;

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


  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

  public String getPassportType() {
    return passportType;
  }

  public void setPassportType(String passportType) {
    this.passportType = passportType;
  }

  public String getName() {
    return name;
  }

  public String getFlowMsg() {
    return flowMsg;
  }

  public void setFlowMsg(String flowMsg) {
    this.flowMsg = flowMsg;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public Date getDataBirth() {
    return dataBirth;
  }

  public void setDataBirth(Date dataBirth) {
    this.dataBirth = dataBirth;
  }

  public String getPlaceBirth() {
    return placeBirth;
  }

  public void setPlaceBirth(String placeBirth) {
    this.placeBirth = placeBirth;
  }

  public String getPlaceIssue() {
    return placeIssue;
  }

  public void setPlaceIssue(String placeIssue) {
    this.placeIssue = placeIssue;
  }

  public Date getDateIssue() {
    return dateIssue;
  }

  public void setDateIssue(Date dateIssue) {
    this.dateIssue = dateIssue;
  }

  public Date getDateExpire() {
    return dateExpire;
  }

  public void setDateExpire(Date dateExpire) {
    this.dateExpire = dateExpire;
  }

  public String getAnthority() {
    return anthority;
  }

  public void setAnthority(String anthority) {
    this.anthority = anthority;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public Integer getPassportStatus() {
    return passportStatus;
  }

  public void setPassportStatus(Integer passportStatus) {
    this.passportStatus = passportStatus;
  }

  public Integer getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(Integer flowStatus) {
    this.flowStatus = flowStatus;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getOperatorTime() {
    return operatorTime;
  }

  public void setOperatorTime(Date operatorTime) {
    this.operatorTime = operatorTime;
  }

  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }
  
}
