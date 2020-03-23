package com.syq.web.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.FlowPassportBo;
import com.syq.biz.domain.Passport;
import com.syq.util.convert.BeanUtil;

public class PassportHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PassportHelper.class);
  
  public static Passport createFlowPassport(FlowPassportBo flowPassportBo) {
    Passport passport = new Passport();
    BeanUtil.copyProperties(flowPassportBo,  passport);
    return passport;
  }
  
}
