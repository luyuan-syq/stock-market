package com.syq.biz.domain;

import java.util.Date;


public class Log {

  private long id;
  /**
   * 操作者id
   */
  private long operatorId;
  /**
   * 操作者账户名
   */
  private String operatorName;
  /**
   * 操作者当前对应角色
   */
  private String operatorRole;
  /**
   * 被操作对象编码
   */
  private String targetSubject;
  /**
   * 操作内容, 用于前端展示
   */
  private String content = "";
  /**
   * 操作详细内容, 用于前端展示
   */
  private String contentDetail = "";
  /**
   * 操作时间
   */
  private Date operationTime;
  /**
   * 创建时间
   */
  private Date createTime;

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

  public Date getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(Date operationTime) {
    this.operationTime = operationTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
