package com.syq.pagination.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginationPo {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PaginationPo.class);

  private Integer pageNo;

  private Integer start;

  private Integer maxResults = 10;

  private String sortField;

  private String sortOrder;

  private Integer flowStatus = -1;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getMaxResults() {
    return maxResults;
  }

  public void setMaxResults(Integer maxResults) {
    this.maxResults = maxResults;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public Integer getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(Integer flowStatus) {
    this.flowStatus = flowStatus;
  }


}
