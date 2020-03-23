package com.syq.biz.domain;


/**
 * 国家entity
 * 
 * created by Shaoyuqi on 2016年10月3日 下午1:04:43
 */
public class VisaCountry {

  private long id;
  private String name;
  private String code;
  private long parentId;
  private int needVisa;

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

  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getNeedVisa() {
    return needVisa;
  }

  public void setNeedVisa(int needVisa) {
    this.needVisa = needVisa;
  }
  
  
}
