/**
 * com.syq.biz.mapper.PassportMapper.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午12:39:52
 */
package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.RenewalPassportBo;
import com.syq.biz.domain.Passport;
import com.syq.biz.domain.RenewalPassport;

/**
 * 
 * created by Huangye on 2016年8月28日 下午12:39:52
 */
@Repository
public interface RenewalPassportMapper extends BaseMapper<Passport>{
  
  Passport selectByPrimaryKey(Long id);
  
  <T> int selectCount(T passport);
  
  int saveRenewalPassport(RenewalPassport renewalPassport);
  
  int updateRenewalPassport(RenewalPassport renewalPassport);
  
  <T> List<RenewalPassportBo> selectCondition(T passport);
  
  int deleteByPassportId(Long passportId);
    
}
