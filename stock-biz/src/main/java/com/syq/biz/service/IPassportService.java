/**
 * com.syq.biz.service.IPassportService.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:04:53
 */
package com.syq.biz.service;

import java.util.List;
import java.util.Map;

import com.syq.biz.bo.ApplyPassportInfoBo;
import com.syq.biz.bo.IEPassportBo;
import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.PassportBo;
import com.syq.biz.domain.Manager;
import com.syq.biz.domain.Passport;
import com.syq.pagination.common.AuditInfoPo;
import com.syq.pagination.common.PassportPo;
import com.syq.util.constant.PassportStatus;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:04:53
 */
public interface IPassportService {
  
  //保存返回主鍵
  Long insertPassport(PassportBo passportBo);
  
  int save(PassportBo passportBo);
  
  int update(PassportBo passportBo);
  
  int delete(Long passportId);
  
  Passport selectByPrimaryKey(Long passportId);
  
  List<Passport> selectCondition(PassportPo passportPo);
  
  int saveApplyPassportInfo(ApplyPassportInfoBo applyPassportInfo);
  
  Passport getPassportByPersonId(Long personId);
  
  Passport getPassportByIdNumber(String idNumber);
  
  ApplyPassportInfoBo getApplyPassportByPersonId(Long personId);
  
  ApplyPassportInfoBo getApplyPassportByIdNumber(String idNumber);
  
  void auditApplyPassportInfo(AuditInfoPo auditInfo);
  
  void updatePassportStatus(Long personId, PassportStatus status, String msg);
  
  void updatePassportStatus(String passportCode, PassportStatus status, String msg);
  
  Map<Object, Object> getTaskWaitAuditPassportCount();
  
  boolean updatePassportStatus(Long taskId,PassportStatus status);
  
  int expireCount();
  
  int overtimeNotReturnCount();
  
  int getCountTodo(PassportPo passportPo);
  
  List<PassportBo> queryTodo(PassportPo passportPo);
  
  void batchProcessPassport(List<IEPassportBo> boes,Manager manager);
}
