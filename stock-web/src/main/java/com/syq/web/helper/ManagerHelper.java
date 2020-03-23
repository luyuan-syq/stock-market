package com.syq.web.helper;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.ManagerBo;

public class ManagerHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(ManagerHelper.class);
  
  public static void forCreate(ManagerBo bo) {
    bo.setCreateTime(new Date());
    bo.setOperatorId("100000");
    bo.setOperatorTime(new Date());
    
  }
}
