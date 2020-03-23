/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum CostSourceType {
  
  EXPARTIATE_UNIT(1,"派员单位"), 
  INVITER(2,"邀请方"),
  OTHER(3,"其他");
  
  
  private int code;
  private String msg;
  
  CostSourceType(int code,String msg){
    this.code = code;
    this.msg = msg;
  }
  
  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
  
  public static CostSourceType from(int status){
    
    for (CostSourceType statusEnum :CostSourceType.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
}