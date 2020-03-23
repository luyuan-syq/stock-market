package com.syq.biz.domain;

import java.util.Set;


public class WzaxRight {
  
  private long id;
  
  private String name;
  
  private String url;
  
  private WzaxModule module;
  
  private String uuid;
  
  private Set<Role> roles;

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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public WzaxModule getModule() {
    return module;
  }

  public void setModule(WzaxModule module) {
    this.module = module;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
