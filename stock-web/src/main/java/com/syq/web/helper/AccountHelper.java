package com.syq.web.helper;

import static com.syq.web.common.WebConst.DEFAULT_PASSWORD;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.AccountBo;
import com.syq.biz.domain.Manager;
import com.syq.util.convert.BeanUtil;
import com.syq.web.vo.AccountVo;

public class AccountHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(AccountHelper.class);
  
  public static AccountBo accountVoToBoForCreate(AccountVo vo,Manager manager) {
    AccountBo bo = new AccountBo();
    BeanUtil.copyProperties(vo, bo);
    bo.setPassword(DEFAULT_PASSWORD);
    bo.setCreateTime(new Date());
    bo.setOperatorId(manager.getOperatorId());
    bo.setOperatorTime(new Date());
    return bo;
    
  }
  
  public static AccountBo accountVoToBoForUpdate(AccountVo vo) {
    AccountBo bo = new AccountBo();
    BeanUtil.copyProperties(vo, bo);
    bo.setOperatorId("100000");
    bo.setOperatorTime(new Date());
    return bo;
    
  }
}
