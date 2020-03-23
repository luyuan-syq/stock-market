package com.syq.web.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitFaff {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(InitFaff.class);
  
  public void init() {
    LOG.info("----------系统初始化开始------------");
    
    LOG.info("----------系统初始化结束------------");
  }
}
