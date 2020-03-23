package com.syq.util.spring.monitor.aop;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.syq.util.spring.monitor.MonitorType;
import com.syq.util.spring.monitor.service.MonitorEntrance;

/**
 * 监控切面实现
 * created by xuzhw on 2015年9月23日 下午8:01:59
 */
@Aspect
public class MonitorAspect {
  
  /**
   * 切入点，带有注解com.sm.uc.common.monitor.aop.Monitor
   * 
   * created by xuzhw on 2015年9月23日 下午8:02:29
   */
  @Pointcut("@annotation(com.syq.util.spring.monitor.aop.Monitor)")
  public void monitor() {
  }
  
  /**
   * 进行增强，统计执行时间，记录执行次数
   * @param joinPoint
   * @return
   * @throws Throwable
   * created by xuzhw on 2015年9月23日 下午8:03:03
   */
  @Around("monitor()")
  public Object monitoringMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long rt = System.currentTimeMillis() - start;
    Monitor annotation = getAnnotation(joinPoint, Monitor.class);
    MonitorType[] value = annotation.value();
    for (MonitorType o : value) {
      if (o == MonitorType.RT) {
        MonitorEntrance.getInstance().inc(o, rt);
        continue;
      }
      MonitorEntrance.getInstance().inc(o);
    }
    return proceed;
  }
  
  @AfterThrowing(value = "monitor()", throwing = "error")
  public void throwingException(JoinPoint joinPoint, Throwable error) {
    
  }
  
  /**
   * 获取某个切入点带有的注解
   * @param joinPoint
   * @param annotationType
   * @return
   * created by xuzhw on 2015年9月23日 下午8:03:33
   */
  private <T  extends Annotation> T getAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationType){
    MethodSignature o = (MethodSignature) joinPoint.getSignature();  
    return o.getMethod().getAnnotation(annotationType);  
  }
}
