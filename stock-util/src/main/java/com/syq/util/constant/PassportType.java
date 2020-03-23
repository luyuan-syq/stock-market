/**
 * com.syq.util.constant.PassportType.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午3:01:52
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午3:01:52
 */
public enum PassportType {

  TYPE_1("P","普通因私护照"), 
  TYPE_2("W","外交护照"),
  TYPE_3("G","公务护照");
  
  private String code;
  private String title;
  
  PassportType(String code,String title){
    this.code = code;
    this.title = title;
  }
  
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  } 
}
