package com.syq.util.constant;


public enum FileStatus {
  
  NEW(0,"提交资料"), 
  AUDITING(1,"待审核"),
  REJECT(2,"拒绝"), 
  PASS(3,"通过");
  
  
  private int code;
  private String msg;
  
  FileStatus(int code,String msg){
    this.code = code;
    this.msg = msg;
  }
  
  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
  
  public static FileStatus from(int status){
    
    for (FileStatus statusEnum :FileStatus.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }

}
