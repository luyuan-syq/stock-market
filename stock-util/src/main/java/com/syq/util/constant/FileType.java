package com.syq.util.constant;


public enum FileType {
  
  FILE_TEMPLATE(0,"标准文件模板"),
  TEXT_VALUE(1,"非标准文件模板");
  
  private int code;
  private String message;
  
  private FileType(int code,String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
  
}
