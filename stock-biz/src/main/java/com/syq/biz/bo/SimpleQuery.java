/**
 * com.syq.biz.bo.SimpleQuery.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年9月3日 上午9:41:06
 */
package com.syq.biz.bo;

/**
 * 
 * created by Huangye on 2016年9月3日 上午9:41:06
 */
public class SimpleQuery {
  /**
   * 当前页码
   */
  private Integer page  = 1;
  /**
   * 页面记录数
   */
  private Integer rows  = 10;
  /**
   * 起始索引
   */
  private Integer index = 0;

  /**
   * 获取当前页码
   * 
   * @return page 当前页码
   */
  public Integer getPage() {
      return page;
  }

  /**
   * 设置当前页码
   * 
   * @param page 当前页码
   */
  public void setPage(Integer page) {
      this.page = page;
  }

  /**
   * 获取页面记录数
   * 
   * @return rows 页面记录数
   */
  public Integer getRows() {
      return rows;
  }

  /**
   * 设置页面记录数
   * 
   * @param rows 页面记录数
   */
  public void setRows(Integer rows) {
      this.rows = rows;
  }

  /**
   * 获取起始索引
   * 
   * @return index 起始索引
   */
  public Integer getIndex() {
      return index;
  }

  /**
   * 设置起始索引
   * 
   * @param index 起始索引
   */
  public void setIndex(Integer index) {
      this.index = index;
  }
}
