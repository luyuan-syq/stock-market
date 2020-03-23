package com.syq.util.spring.monitor;

/**
 * 监控类型枚举
 * 
 * created by Shaoyuqi on 2016年8月28日 上午12:47:56
 */
public enum MonitorType {
  QPS, TPS, RT;

  /**
   * 解析字符串，找到对应的枚举，如果没有返回null
   * @param name
   * @return
   * created by Shaoyuqi on 2016年8月28日 上午12:48:02
   */
  public static MonitorType parse(String name) {
    for (MonitorType o : MonitorType.values()) {
      if (o.name().equals(name)) {
        return o;
      }
    }
    return null;
  }
}