/**
 * com.syq.biz.service.impl.PassportService.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:07:08
 */
package com.syq.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.PassportBo;
import com.syq.biz.bo.RenewalPassportBo;
import com.syq.biz.domain.Passport;
import com.syq.biz.domain.RenewalPassport;
import com.syq.biz.mapper.RenewalPassportMapper;
import com.syq.biz.service.IRenewalPassportService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.pagination.common.PassportPo;
import com.syq.util.constant.RenewalPassportStatus;
import com.syq.util.convert.BeanUtil;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:07:08
 */
@Service
public class RenewalPassportService implements IRenewalPassportService,IAdapterService<PassportBo> {

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(RenewalPassportService.class);
  
  @Autowired
  private RenewalPassportMapper passportMapper;
  
  @Override
  public int save(RenewalPassportBo renewalpassportBo) {
    RenewalPassport passport = new RenewalPassport();
    BeanUtil.copyProperties(renewalpassportBo, passport);
    return passportMapper.saveRenewalPassport(passport);
  }

  @Override
  public int update(RenewalPassportBo renewalpassportBo) {
    RenewalPassport passport = new RenewalPassport();
    BeanUtil.copyProperties(renewalpassportBo, passport);
    return passportMapper.updateRenewalPassport(passport);
  }

  @Override
  public int delete(Long passportId) {
    return passportMapper.deleteByPassportId(passportId);
  }

  @Override
  public Passport selectByPrimaryKey(Long passportId) {
    return passportMapper.selectByPrimaryKey(passportId);
  }

  @Override
  public List<RenewalPassportBo> selectCondition(PassportPo passportPo) {
    return passportMapper.selectCondition(passportPo);
  }

  @Override
  public void updatePassportStatus(Long passportId, RenewalPassportStatus status) {
    
    if (passportId == null || passportId < 1){
      throw new RuntimeException("无效的护照Id" + passportId);
    }
    
    // 业务现仅保留【护照换发办理完成】，后续业务会有追加
    switch (status) {
//      case WAITING_RENEWAL :
//        doInsert(passportId, status);
//        break;
//      case RENEWAL_COMMIT :
//        doInsert(passportId, status);
//        break;
//      case RENEWAL_INFO_REGISTER :
//        doInsert(passportId, status);
//        break;
      case RENEWAL_OVER :
        doInsert(passportId, status);
        break; 
      default :
        LOG.info("unknown status {}", status);
    }
  }

  @Override
  public int getCount(PaginationPo t) {
    return passportMapper.selectCount(t);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<RenewalPassportBo> query(PaginationPo t) {
    List<RenewalPassportBo> boes =  passportMapper.selectCondition(t);
    return boes;
  }
  
  private void doInsert(Long passportId, RenewalPassportStatus status){
    RenewalPassport passport = 
        RenewalPassport.buildInsertInstance(passportId, status.getCode(), status.getTitle());
    
    passportMapper.saveRenewalPassport(passport);
  }
  
  private void doUpdate(Long passportId, RenewalPassportStatus status){
    RenewalPassport renewalPassport = 
        RenewalPassport.buildUpdateInstance(passportId, status.getCode(), status.getTitle());
    
    passportMapper.updateRenewalPassport(renewalPassport);
  }
  
  private void doDelete(Long passportId){
    passportMapper.deleteByPassportId(passportId);
  }
}
