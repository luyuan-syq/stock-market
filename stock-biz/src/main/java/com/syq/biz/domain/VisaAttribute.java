package com.syq.biz.domain;


public class VisaAttribute {
  
  private long id;
  
  private long principalId;
  
  private String attrName;
  
  private String attrValue;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getPrincipalId() {
    return principalId;
  }

  public void setPrincipalId(long principalId) {
    this.principalId = principalId;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public String getAttrValue() {
    return attrValue;
  }

  public void setAttrValue(String attrValue) {
    this.attrValue = attrValue;
  }
  
}
