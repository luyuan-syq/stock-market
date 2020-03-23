package com.syq.biz.factory;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SYQServiceFactory {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(SYQServiceFactory.class);

  private final static String fileSuffix = ".xml";

  private final static String filePrefix = "visatemplate/";

  public static <T> T getService(Class<T> clazz, String id) {
    T t = null;
    try {
      Constructor<T> constructor = clazz.getConstructor(String.class);
      t = (T)constructor.newInstance(getTemplatePath(id));
    } catch (Exception e) {
      LOG.error("SYQServiceFactory获取service失败，原因 ", e);
    }
    return t;
  }
  
  private static String getTemplatePath(String id) {
    return new StringBuilder(filePrefix).append(id).append(fileSuffix).toString();
  }
  
  
}
