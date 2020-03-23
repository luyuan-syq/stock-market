package com.syq.biz.domain;

import java.util.Date;
import java.util.List;


public class Role {
  
  private long id;
  
  private String name;
  
  private String description;
  
  private long operatorId;
  
  private String operatorName;
  
  private Date operatorTime;
  
  private Date createTime;
  
  private List<WzaxRight> rights;
  
  private List<Manager> manager;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
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

  public Date getOperatorTime() {
    return operatorTime;
  }

  public void setOperatorTime(Date operatorTime) {
    this.operatorTime = operatorTime;
  }

  public List<WzaxRight> getRights() {
    return rights;
  }

  public void setRights(List<WzaxRight> rights) {
    this.rights = rights;
  }

  public List<Manager> getManager() {
    return manager;
  }

  public void setManager(List<Manager> manager) {
    this.manager = manager;
  }
  
}
