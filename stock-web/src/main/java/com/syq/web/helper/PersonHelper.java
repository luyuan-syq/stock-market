package com.syq.web.helper;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.PersonBo;

public class PersonHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PersonHelper.class);
  
  public static void forCreate(PersonBo bo) {
    bo.setCreateTime(new Date());
    bo.setOperatorId("100000");
    bo.setOperatorTime(new Date());
    
  }
  
}
