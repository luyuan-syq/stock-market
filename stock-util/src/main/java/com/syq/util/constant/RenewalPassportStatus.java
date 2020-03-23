/**
 * com.syq.util.constant.PassportStatus.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:50:06
 */
package com.syq.util.constant;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:50:06
 */
public enum RenewalPassportStatus {
  
  WAITING_RENEWAL(0,"待换发"), 
  RENEWAL_COMMIT(1,"护照换发函已提交"),
  RENEWAL_INFO_REGISTER(2,"待登记"), 
  RENEWAL_OVER(3,"护照换发办理完成");
  
  private Integer code;
  private String title;
  
  RenewalPassportStatus(Integer code,String title){
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
  
  public static RenewalPassportStatus from(int status){
    
    for (RenewalPassportStatus statusEnum :RenewalPassportStatus.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
}