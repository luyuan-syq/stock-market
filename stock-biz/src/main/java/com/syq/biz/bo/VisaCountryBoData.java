/**
 * com.syq.biz.bo.VisaCountryBoData.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2017年2月26日 上午10:09:15
 */
package com.syq.biz.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * created by Huangye on 2017年2月26日 上午10:09:15
 */
public class VisaCountryBoData {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaCountryBoData.class);
  
  private String text;
  private String value;
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  
  
}
