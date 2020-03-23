package com.syq.biz.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 任务编码表
 * 
 * created by Shaoyuqi on 2016年10月2日 下午12:54:44
 */
public class TaskCode {
  
  private long id;
  
  private String taskCode;
  
  private Date createTime;
  
  private String operatorId;
  
  private Date operatorTime;
  
  private int status;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
  
  public static TaskCode getNewInstance(){
    return new TaskCode();
  } 
  
  public TaskCode buildUpdateEntity(String taskCode, int status, String opeator){
    this.setTaskCode(taskCode);
    this.setStatus(status);
    this.setOperatorTime(new Date());
    if (StringUtils.isNotBlank(opeator)){
      this.setOperatorId(opeator);
    }
    return this;
  }
}
