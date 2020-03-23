package com.syq.web.vo;

import java.util.Date;

public class TaskCountryVo {
    
  private String taskCountry;
  
  private int expectStickDay;
  
  private String comment;
  
  private Date createTime;

  public String getTaskCountry() {
    return taskCountry;
  }

  public void setTaskCountry(String taskCountry) {
    this.taskCountry = taskCountry;
  }

  public int getExpectStickDay() {
    return expectStickDay;
  }

  public void setExpectStickDay(int expectStickDay) {
    this.expectStickDay = expectStickDay;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
