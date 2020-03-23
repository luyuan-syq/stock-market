/**
 * com.syq.biz.bo.PassportBo.java created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日
 * 下午1:40:36
 */
package com.syq.biz.bo;

import com.syq.biz.domain.Passport;

/**
 * 
 * created by Huangye on 2016年8月28日 下午1:40:36
 */
public class RenewalPassportBo extends Passport{
  
  private Long passportId;
  private String instructionNo;
  private String headerName;
  private String taskCountry;

  public Long getPassportId() {
    return passportId;
  }

  public void setPassportId(Long passportId) {
    this.passportId = passportId;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }

  public String getHeaderName() {
    return headerName;
  }

  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  public String getTaskCountry() {
    return taskCountry;
  }

  public void setTaskCountry(String taskCountry) {
    this.taskCountry = taskCountry;
  }
}
