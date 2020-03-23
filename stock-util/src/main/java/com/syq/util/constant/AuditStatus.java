/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum AuditStatus {
  
  AUDIT_PASS(0,"审核通过"), 
  AUDIT_REJECT(1,"审核拒绝");
  
  private int code;
  private String msg;
  
  AuditStatus(int code,String msg){
    this.code = code;
    this.msg = msg;
  }
  
  public int getCode() {
    return code;
  }
    
  public String getMsg() {
    return msg;
  }  
}