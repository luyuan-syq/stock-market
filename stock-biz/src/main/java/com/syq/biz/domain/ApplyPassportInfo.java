package com.syq.biz.domain;

import java.util.Date;

public class ApplyPassportInfo {
  
  private long id;
  
  private long operatorId;
  
  private String operatorName;
  
  private Date operatorTime;
  
  private String idCardImg;
  
  private String familyBookImg;
  
  private Date createTime;
  
  private long personId;
  
  private String placeBirth;
  
  private String pictureNo;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(long operatorId) {
    this.operatorId = operatorId;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public Date getOperatorTime() {
    return operatorTime;
  }

  public void setOperatorTime(Date operatorTime) {
    this.operatorTime = operatorTime;
  }

  public String getIdCardImg() {
    return idCardImg;
  }

  public void setIdCardImg(String idCardImg) {
    this.idCardImg = idCardImg;
  }

  public String getFamilyBookImg() {
    return familyBookImg;
  }

  public void setFamilyBookImg(String familyBookImg) {
    this.familyBookImg = familyBookImg;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }

  public String getPlaceBirth() {
    return placeBirth;
  }

  public void setPlaceBirth(String placeBirth) {
    this.placeBirth = placeBirth;
  }
  
  public String getPictureNo() {
    return pictureNo;
  }

  public void setPictureNo(String pictureNo) {
    this.pictureNo = pictureNo;
  }

  public static ApplyPassportInfo getNewInstance(){
    return new ApplyPassportInfo();
  }
}
