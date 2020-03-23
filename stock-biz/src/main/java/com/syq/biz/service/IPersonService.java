package com.syq.biz.service;


import java.util.List;

import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.PersonBo;
import com.syq.util.constant.VisaStatus;


public interface IPersonService {
  
  boolean save(PersonBo personBo,Long taskId);
  
  boolean imsave(IEPersonBo ieBo,PersonBo personBo,Long taskId);
  
  int deleteByIds(Long[] ids);
  
  int deleteById(Long personId,Long taskId);
  
  PersonBo selectById(Long id);
  
  PersonBo selectByIdNumber(String idNumber);
  
  boolean update(PersonBo personBo);
  
  List<AuditPersonBo> getPersonsByTaskId(long taskId);
  
  List<AuditPersonBo> getPersonsByVisaStatus(VisaStatus status);
  
  boolean isPersonHasRelateTask(String idNumber);
  
  List<Long> getHasApplyPersonIds(long taskId);
  
  List<AuditPersonBo> getPersonsByTaskIdForToDoList(long taskId);
  
  PersonBo getTaskHeaderPerson(Long taskId);
  
}
