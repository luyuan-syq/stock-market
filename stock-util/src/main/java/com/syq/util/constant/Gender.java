/**
 * com.syq.util.constant.Gender.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:55:15
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:55:15
 */
public enum Gender {
  MAN(1,"男"), 
  WOMEN(2,"女");
  
  private final Integer code;
  private final String title;

  Gender(Integer code,String title) {
      this.code = code;
      this.title = title;
  }

  public Integer getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }
}