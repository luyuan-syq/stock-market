/**
 * com.syq.biz.bo.PassportBo.java created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日
 * 下午1:40:36
 */
package com.syq.biz.domain;

import java.util.Date;

import com.syq.util.constant.PassportStatus;

/**
 * 
 * created by Huangye on 2016年8月28日 下午1:40:36
 */
public class RenewalPassport {
  
  private Long id;
  private Long passportId;
  private Integer flowStatus;
  private String flowMsg;
  private Date createTime;
  private Date operatorTime;
  private String operatorId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPassportId() {
    return passportId;
  }

  public void setPassportId(Long passportId) {
    this.passportId = passportId;
  }

  public Integer getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(Integer flowStatus) {
    this.flowStatus = flowStatus;
  }

  public String getFlowMsg() {
    return flowMsg;
  }

  public void setFlowMsg(String flowMsg) {
    this.flowMsg = flowMsg;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getOperatorTime() {
    return operatorTime;
  }

  public void setOperatorTime(Date operatorTime) {
    this.operatorTime = operatorTime;
  }

  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }
  
  public static RenewalPassport buildInsertInstance(Long passportId, int status, String msg){
    RenewalPassport passport = buildInstance(passportId, status, msg);
    passport.setCreateTime(new Date());
    return passport;
  }
  
  public static RenewalPassport buildUpdateInstance(Long passportId, int status, String msg){
    RenewalPassport passport = buildInstance(passportId, status, msg);
    passport.setOperatorTime(new Date());
    return passport;
  }
  
  private static RenewalPassport buildInstance(Long passportId, int status, String msg){
    RenewalPassport passport = new RenewalPassport();
    
    passport.setPassportId(passportId);
    passport.setFlowStatus(status);
    passport.setFlowMsg(msg);
    
    return passport;
  }
}
