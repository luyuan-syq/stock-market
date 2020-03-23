package com.syq.util;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtil {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TimeUtil.class);
  
  public static Calendar ca = Calendar.getInstance();
  
  public static Date getInsulateDate(Date soureDate, int insulateDay){
    ca.setTime(soureDate);
    ca.add(Calendar.DAY_OF_YEAR, insulateDay);
    return ca.getTime();
  }
}
