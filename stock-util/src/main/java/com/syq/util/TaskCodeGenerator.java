package com.syq.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskCodeGenerator {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskCodeGenerator.class);
  
  private static final String base = "abcdefghijklmnopqistuvwxyz0123456789";
  
  
  public static String generate() {
    return getRandomString(6);
  }
  
  private static String getRandomString(int length) {
    Random r = new Random();
    StringBuffer buff = new StringBuffer();
    for(int i = 0;i<length;i++) {
      int number = r.nextInt(base.length());
      buff.append(base.charAt(number));
    }
    return buff.toString();
  }
}
