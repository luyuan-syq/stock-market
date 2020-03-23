package com.syq.pagination.common;

import java.util.Date;

public class LogPo extends PaginationPo {

  private long id;

  private long operatorId;

  private String operatorName;

  private String operatorRole;

  private String targetSubject;

  private String content;

  private String contentDetail;

  private Date createBeginTime;

  private Date createEndTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(long operatorId) {
    this.operatorId = operatorId;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public String getOperatorRole() {
    return operatorRole;
  }

  public void setOperatorRole(String operatorRole) {
    this.operatorRole = operatorRole;
  }

  public String getTargetSubject() {
    return targetSubject;
  }

  public void setTargetSubject(String targetSubject) {
    this.targetSubject = targetSubject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getContentDetail() {
    return contentDetail;
  }

  public void setContentDetail(String contentDetail) {
    this.contentDetail = contentDetail;
  }

  public Date getCreateBeginTime() {
    return createBeginTime;
  }

  public void setCreateBeginTime(Date createBeginTime) {
    this.createBeginTime = createBeginTime;
  }

  public Date getCreateEndTime() {
    return createEndTime;
  }

  public void setCreateEndTime(Date createEndTime) {
    this.createEndTime = createEndTime;
  }


}
