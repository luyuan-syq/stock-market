package com.syq.biz.domain;

import java.util.List;

public class WzaxModule {
  
  private long id;
  
  private String name;
  
  private String uuid;
  
  private String uuname;
  
  private String url;
  
  private WzaxModule parent;
  
  private List<WzaxModule> children;
  
  private List<WzaxRight> rights;

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

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUuname() {
    return uuname;
  }

  public void setUuname(String uuname) {
    this.uuname = uuname;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public WzaxModule getParent() {
    return parent;
  }

  public void setParent(WzaxModule parent) {
    this.parent = parent;
  }

  public List<WzaxModule> getChildren() {
    return children;
  }

  public void setChildren(List<WzaxModule> children) {
    this.children = children;
  }

  public List<WzaxRight> getRights() {
    return rights;
  }

  public void setRights(List<WzaxRight> rights) {
    this.rights = rights;
  }
  
}
