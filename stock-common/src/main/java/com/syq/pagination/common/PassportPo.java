/**
 * com.syq.pagination.common.PassportPo.java created by Huangye(yuanhan.syq@alibaba-inc.com) on
 * 2016年9月4日 下午1:09:41
 */
package com.syq.pagination.common;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.syq.util.TimeUtil;
import com.syq.util.constant.PassportStatus;

/**
 * 
 * created by Huangye on 2016年9月4日 下午1:09:41
 */
public class PassportPo extends PaginationPo {

  private String taskCode;
  private String instructionNo;
  private String passportNo;
  private String passportType;
  private String name;
  private Date dateIssue;
  private Date dateExpire;
  private Date expireBeginTime;
  private Date expireEndTime;
  private Date notReturnLimitTime;
  private String idNumber;
  private Integer passportStatus;
  private Integer[] status;
  private boolean needCountExprie;
  private boolean needCountNotReturn;
  private boolean needCountAboutExprie;

  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

  public String getPassportType() {
    return passportType;
  }

  public void setPassportType(String passportType) {
    this.passportType = passportType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDateIssue() {
    return dateIssue;
  }

  public void setDateIssue(Date dateIssue) {
    this.dateIssue = dateIssue;
  }

  public Date getDateExpire() {
    return dateExpire;
  }

  public void setDateExpire(Date dateExpire) {
    this.dateExpire = dateExpire;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public Integer getPassportStatus() {
    return passportStatus;
  }

  public void setPassportStatus(Integer passportStatus) {
    this.passportStatus = passportStatus;
  }

  public Integer[] getStatus() {
    return status;
  }

  public void setStatus(Integer[] status) {
    this.status = status;
    if (status == null){
      return;
    }
    List<Integer> statusList =  Arrays.asList(status);
    if (statusList.contains(PassportStatus.ABOUT_EXPRIE.getCode())){
      setExpireEndTime(TimeUtil.getInsulateDate(new Date(), 60));
    }
    
    if (statusList.contains(PassportStatus.EXPRIE.getCode())){
      setExpireBeginTime(new Date());
    }
  }
  
  public static PassportPo getNewInstance(){
    return new PassportPo();
  }

  public Date getExpireBeginTime() {
    return expireBeginTime;
  }

  public void setExpireBeginTime(Date expireBeginTime) {
    this.expireBeginTime = expireBeginTime;
  }

  public Date getExpireEndTime() {
    return expireEndTime;
  }

  public void setExpireEndTime(Date expireEndTime) {
    this.expireEndTime = expireEndTime;
  }

  public Date getNotReturnLimitTime() {
    return notReturnLimitTime;
  }

  public void setNotReturnLimitTime(Date notReturnLimitTime) {
    this.notReturnLimitTime = notReturnLimitTime;
  }

  public boolean isNeedCountExprie() {
    return needCountExprie;
  }

  public void setNeedCountExprie(boolean needCountExprie) {
    this.needCountExprie = needCountExprie;
  }

  public boolean isNeedCountNotReturn() {
    return needCountNotReturn;
  }

  public void setNeedCountNotReturn(boolean needCountNotReturn) {
    this.needCountNotReturn = needCountNotReturn;
  }
  
  public boolean isNeedCountAboutExprie() {
    return needCountAboutExprie;
  }

  public void setNeedCountAboutExprie(boolean needCountAboutExprie) {
    this.needCountAboutExprie = needCountAboutExprie;
  }

  public String getInstructionNo() {
    return instructionNo;
  }

  public void setInstructionNo(String instructionNo) {
    this.instructionNo = instructionNo;
  }

  public void buildCountExpireParam(){
    if (needCountExprie){
      setExpireEndTime(new Date());
    }
  }
  
  public void builCountNotReturnParam(){
    if (needCountNotReturn){
      setNotReturnLimitTime(TimeUtil.getInsulateDate(new Date(), -10));
    }
  }
  
  public void buildAboutExpiredTime(){
    if (needCountAboutExprie){
      setExpireEndTime(TimeUtil.getInsulateDate(new Date(), 60));
    }
  }
}
