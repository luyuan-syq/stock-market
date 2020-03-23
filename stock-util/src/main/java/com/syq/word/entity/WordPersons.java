package com.syq.word.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordPersons {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(WordPersons.class);
  
  private String userName;
  
  private String idNumber;
  
  private String deptName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }
  
  public static WordPersons getNewInstance(String userName ,String idNumber ,String deptName){
    WordPersons person = new WordPersons();
    person.setUserName(userName == null ? "" : userName);
    person.setIdNumber(idNumber == null ? "" : idNumber);
    person.setDeptName(deptName == null ? "" : deptName);
    return person;
  }
}
