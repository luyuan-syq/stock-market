/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum TaskStatus {
  
  NEW_APPLY(-1,"新建"), 
  HADNDLING(1,"办理中"),
  WAITING_RECEIVE(9,"待接收"),
  STOP(10,"终止"),
  DONE(2,"完成"), 
  CLOESD(3,"关闭"),
  RENEWAL_COMMIT(4,"护照换发函提交中"),
  RENEWAL_AUDITING(5,"换发函审核中"),
  RENEWAL_LETTER_ONTHEWAT(6,"复函已发出"),
  RENEWAL_INFO_REGISTER(7,"复函信息登记中"),
  RENEWAL_OVER(8,"护照换发办理完成");
  
  
  private int code;
  private String msg;
  
  TaskStatus(int code,String msg){
    this.code = code;
    this.msg = msg;
  }
  
  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
  
  public static TaskStatus from(int status){
    
    for (TaskStatus statusEnum :TaskStatus.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
}