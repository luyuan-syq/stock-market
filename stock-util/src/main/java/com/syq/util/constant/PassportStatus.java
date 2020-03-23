/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum PassportStatus {
  
  NOT_HANDLED(0,"未办理"), 
  AUDIT_WAIT(1,"待审核"),
  AUDIT_REJECT(2,"审核未通过"), 
  HADNDLING(3,"办理中"),
  LOAN(4,"借出"), 
  FILE_IN(5,"归档"),
  ABOUT_EXPRIE(100,"将过期"),
  EXPRIE(101,"已过期");
  
  private Integer code;
  private String title;
  
  PassportStatus(Integer code,String title){
    this.code = code;
    this.title = title;
  }
  
  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  
  public static PassportStatus from(int status){
    
    for (PassportStatus statusEnum :PassportStatus.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
}