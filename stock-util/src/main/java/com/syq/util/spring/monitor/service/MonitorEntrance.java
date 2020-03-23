package com.syq.util.spring.monitor.service;

import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;


import com.google.common.collect.ImmutableMap;
import com.syq.util.spring.monitor.MonitorType;

/**
 * 监控信息访问入口
 * created by xuzhw on 2015年9月22日 上午10:56:59
 */
public final class MonitorEntrance {
  
  /**
   * 不可变map持有监控值
   */
  private ImmutableMap<MonitorType, AtomicLong> map = null;
  
  private static class MonitorHolder {
    public static final MonitorEntrance instance = new MonitorEntrance();
  }
  
  /**
   * 延迟加载单例
   * @return
   * created by xuzhw on 2015年9月22日 上午11:01:13
   */
  public static MonitorEntrance getInstance() {
    return MonitorHolder.instance;
  }
  
  private MonitorEntrance() {
    MonitorType[] types = MonitorType.values();
    ImmutableMap.Builder<MonitorType, AtomicLong> b = ImmutableMap.builder();
    for (MonitorType o : types) {
      b.put(o, new AtomicLong());
    }
    map = b.build();
  }

  /**
   * 累加一个监控属性
   * @param key
   * @return
   * created by xuzhw on 2015年9月22日 上午11:01:32
   */
  public long inc(MonitorType key) {
    return map.get(key).incrementAndGet();
  }

  /**
   * 将某个监控属性加上delta
   * @param key
   * @param delta
   * @return
   * created by xuzhw on 2015年9月22日 上午11:01:42
   */
  public long inc(MonitorType key, long delta) {
    return map.get(key).addAndGet(delta);
  }

  /**
   * 获取某个监控属性
   * @param key
   * @return
   * created by xuzhw on 2015年9月22日 上午11:02:26
   */
  public long get(MonitorType key) {
    return map.containsKey(key) ? map.get(key).get() : 0L;
  }
  
}