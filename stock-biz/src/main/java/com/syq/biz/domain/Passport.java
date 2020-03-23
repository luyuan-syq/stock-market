/**
 * com.syq.biz.domain.Passport.java created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日
 * 下午12:29:35
 */
package com.syq.biz.domain;

import java.util.Date;

/**
 * 
 * created by Huangye on 2016年8月28日 下午12:29:35
 */
public class Passport extends BaseBean {

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
  private Date createTime;
  private Date operatorTime;
  private String operatorId;
  private Integer flowStatus;
  private String flowMsg;

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

  public String getFlowMsg() {
    return flowMsg;
  }

  public void setFlowMsg(String flowMsg) {
    this.flowMsg = flowMsg;
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

  /**
   * TODO 增加操作人Id更新
   * @param taskId
   * @param taskCode
   * created by Jinglei on 2017年2月26日 下午2:30:45
   */
  public void buildRelateTask(Long taskId, String taskCode){
    this.setTaskId(taskId);
    this.setTaskCode(taskCode);
    this.setOperatorTime(new Date());
  }
  
  public void buildFlowStatus(int flowStatus, String msg){
    this.setFlowStatus(flowStatus);
    this.setFlowMsg(msg);
    this.setOperatorTime(new Date());
  }
}
