package com.syq.util.spring.monitor.aop;


import com.syq.util.spring.monitor.MonitorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 监控注解
 * 
 * created by Shaoyuqi on 2016年8月28日 上午12:48:22
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitor {
  
  /**
   * 监控的标识
   * @return
   * created by Shaoyuqi on 2016年8月28日 上午12:48:29
   */
  MonitorType[] value() default {MonitorType.QPS,MonitorType.TPS,MonitorType.RT};
}