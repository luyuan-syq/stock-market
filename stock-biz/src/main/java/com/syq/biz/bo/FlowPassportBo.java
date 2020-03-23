package com.syq.biz.bo;


public class FlowPassportBo {


  private Long personId;

  private Long taskId;

  private String taskCode;

  private String idNumber;

  private int flowStatus;

  private String flowMsg;


  public FlowPassportBo(Long personId, Long taskId, String taskCode, String idNumber,
      int flowStatus, String flowMsg) {
    this.personId = personId;
    this.taskId = taskId;
    this.taskCode = taskCode;
    this.idNumber = idNumber;
    this.flowMsg = flowMsg;
    this.flowStatus = flowStatus;
  }


  public Long getPersonId() {
    return personId;
  }


  public void setPersonId(Long personId) {
    this.personId = personId;
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


  public String getIdNumber() {
    return idNumber;
  }


  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
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



}
