package com.syq.biz.bo;

import java.util.Date;

import org.springframework.util.StringUtils;

public class ApplyPassportInfoBo {
  
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

  public static ApplyPassportInfoBo getNewInstance(){
    return new ApplyPassportInfoBo();
  }
  
  public void buildUpdateEntity(ApplyPassportInfoBo oldEntity){
    if (oldEntity == null){
      return;
    }
    
    this.setId(oldEntity.getId());
    this.setOperatorId(oldEntity.getOperatorId());
    this.setOperatorName(oldEntity.getOperatorName());
    this.setOperatorTime(new Date());
    
    if (getPersonId() < 1){
      this.setPersonId(oldEntity.getPersonId());
    }
    
    if (StringUtils.isEmpty(getPlaceBirth())){
      this.setPlaceBirth(oldEntity.getPlaceBirth());
    }
    
    if (StringUtils.isEmpty(getIdCardImg())){
      this.setIdCardImg(oldEntity.getIdCardImg());
    }
    
    if (StringUtils.isEmpty(getFamilyBookImg())){
      this.setFamilyBookImg(oldEntity.getFamilyBookImg());
    }
    
    
    if (StringUtils.isEmpty(getPictureNo())){
      this.setPictureNo(oldEntity.getPictureNo());
    }
  }
}
