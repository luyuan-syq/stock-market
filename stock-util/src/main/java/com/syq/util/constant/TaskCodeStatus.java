/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum TaskCodeStatus {
  
  UNDISTRIBUTED(1,"未分配"), 
  USEING(2,"在用"),
  CLOESD(3,"关闭");
  
  
  private int code;
  private String msg;
  
  TaskCodeStatus(int code,String msg){
    this.code = code;
    this.msg = msg;
  }
  
  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
  
  public static TaskCodeStatus from(int status){
    
    for (TaskCodeStatus statusEnum :TaskCodeStatus.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
}