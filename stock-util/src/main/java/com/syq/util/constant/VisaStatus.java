package com.syq.util.constant;

import java.util.ArrayList;
import java.util.List;


public enum VisaStatus {
  
  NOT_HANDLED(0,"未办理"), 
  AUDIT_WAIT(1,"待审核"),
  AUDIT_REJECT(2,"审核未通过"), 
  HADNDLING(3,"办理中"),
  DELIVER(4,"完成"),
  BREADK(5,"拒签");
  
  private Integer code;
  private String title;
  
  VisaStatus(Integer code,String title){
    this.code = code;
    this.title = title;
  }
  
  public static List<Integer> getValuesList() {
    List<Integer> valuesList = new  ArrayList<Integer>();
    valuesList.add(NOT_HANDLED.getCode());
    valuesList.add(AUDIT_WAIT.getCode());
    valuesList.add(AUDIT_REJECT.getCode());
    valuesList.add(HADNDLING.getCode());
    valuesList.add(DELIVER.getCode());
    valuesList.add(BREADK.getCode());
    return valuesList;
  }
  
  public static List<Integer> getManagerValuesList() {
    List<Integer> valuesList = new  ArrayList<Integer>();
    valuesList.add(DELIVER.getCode());
    valuesList.add(BREADK.getCode());
    return valuesList;
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
  
  public static VisaStatus from(int status){
    
    for (VisaStatus statusEnum :VisaStatus.values()){
       if (statusEnum.getCode() == status){
         return statusEnum;
       }
    }
    
    return null;
  }
  
}
