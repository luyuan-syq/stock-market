package com.syq.pagination.common;

public class AuditInfoPo {

  private Long personId;
  
  private Integer auditStatus;
  
  private String auditMsg;

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  public Integer getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(Integer auditStatus) {
    this.auditStatus = auditStatus;
  }

  public String getAuditMsg() {
    return auditMsg;
  }

  public void setAuditMsg(String auditMsg) {
    this.auditMsg = auditMsg;
  }
}
