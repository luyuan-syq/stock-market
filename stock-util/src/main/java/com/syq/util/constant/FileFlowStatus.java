package com.syq.util.constant;


public enum FileFlowStatus {
  
  NOT_HANDLED(0,"未办理"), 
  AUDIT_WAIT(1,"待审核"),
  AUDIT_REJECT(2,"审核未通过"),
  AUDIT_PASS(3,"审核通过");
  
  private int code;
  private String message;
  
  private FileFlowStatus(int code,String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
}
