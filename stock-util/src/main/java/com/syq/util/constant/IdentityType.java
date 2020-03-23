/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum IdentityType {
  
  MEMBER(0, "组员"), 
  HEADMAN(1,"组长"),
  LEAGUE_MEMBER(2,"团员"), 
  COLONEL(3,"团长");
  
  
  private int code;
  private String msg;
  
  IdentityType(int code,String msg){
    this.code = code;
    this.msg = msg;
  }
  
  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
  
  public static IdentityType from(int status){
    
    for (IdentityType statusEnum :IdentityType.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
  
  public static int of(String msg){
    
    for (IdentityType statusEnum :IdentityType.values()){
       if (statusEnum.getMsg() == msg){
         return statusEnum.getCode();
       }
    }
    
    throw new IllegalArgumentException(msg);
  }
}