package com.syq.biz.bo;

import java.util.Date;


public class IEPersonBo {
  
  private Long id;

  private String userName;

  private Date createTime;

  private String operatorId;

  private Date operatorTime;

  private String contactPhone;

  private String mobile;

  private String contactEmail;
  
  private String deptName;
  
  private String idNumber;
  
  private Integer sexey;
  
  private Date birthday;
  
  private String placeBirth;
  
  private String business;
  
  private Integer identity;
  
  private Integer isLeader;
  
  private int rowNum;
  
  private String errorMsg;
  
  private boolean isOk;
  
  private String instructionNo;
  
  public IEPersonBo(int rowNum,String errorMsg) {
    this.rowNum = rowNum;
    this.errorMsg = errorMsg;
  }
  
  public IEPersonBo(int rowNum,String instructionNo,String userName,String idNumber,int sexeyInt,Date birthday,String placeBirth,String deptName,String business,int identity,int isLeader) {
    this.rowNum = rowNum;
    this.userName = userName;
    this.idNumber = idNumber;
    this.sexey = sexeyInt;
    this.birthday = birthday;
    this.placeBirth = placeBirth;
    this.deptName = deptName;
    this.business = business;
    this.identity = identity;
    this.isLeader = isLeader;
    this.instructionNo = instructionNo;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getUserName() {
    return userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public Integer getSexey() {
    return sexey;
  }

  public void setSexey(Integer sexey) {
    this.sexey = sexey;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getPlaceBirth() {
    return placeBirth;
  }

  public void setPlaceBirth(String placeBirth) {
    this.placeBirth = placeBirth;
  }

  public String getBusiness() {
    return business;
  }

  public void setBusiness(String business) {
    this.business = business;
  }

  public Integer getIdentity() {
    return identity;
  }

  public void setIdentity(Integer identity) {
    this.identity = identity;
  }

  public Integer getIsLeader() {
    return isLeader;
  }

  public void setIsLeader(Integer isLeader) {
    this.isLeader = isLeader;
  }

  public int getRowNum() {
    return rowNum;
  }

  public void setRowNum(int rowNum) {
    this.rowNum = rowNum;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean isOk) {
    this.isOk = isOk;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }
  
}
