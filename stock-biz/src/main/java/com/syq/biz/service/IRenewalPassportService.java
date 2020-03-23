/**
 * com.syq.biz.service.IPassportService.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:04:53
 */
package com.syq.biz.service;

import java.util.List;

import com.syq.biz.bo.RenewalPassportBo;
import com.syq.biz.domain.Passport;
import com.syq.pagination.common.PassportPo;
import com.syq.util.constant.RenewalPassportStatus;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:04:53
 */
public interface IRenewalPassportService {
  
  int save(RenewalPassportBo renewalpassportBo);
  
  int update(RenewalPassportBo renewalpassportBo);
  
  int delete(Long passportId);
  
  Passport selectByPrimaryKey(Long passportId);
  
  List<RenewalPassportBo> selectCondition(PassportPo passportPo);
   
  void updatePassportStatus(Long passportId, RenewalPassportStatus status);
}
