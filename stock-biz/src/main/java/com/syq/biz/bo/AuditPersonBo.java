package com.syq.biz.bo;

import com.syq.util.constant.IdentityType;

public class AuditPersonBo extends PersonBo{
  
  private long taskId;
  
  private int passportStatus;
  
  private int flowStatus;
  
  private String flowMsg;
  
  public long getTaskId() {
    return taskId;
  }

  public void setTaskId(long taskId) {
    this.taskId = taskId;
  }

  public int getPassportStatus() {
    return passportStatus;
  }

  public void setPassportStatus(int passportStatus) {
    this.passportStatus = passportStatus;
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
  
  public boolean isColonel(){
    if (null == this.getIdentity()){
       return false;
    }
    
    return IdentityType.COLONEL.getCode() == this.getIdentity();
  }
}
