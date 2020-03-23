package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.domain.Person;

@Repository
public interface PersonMapper extends BaseMapper<Person>{
  
  <T> List<PersonBo> selectByCondition(T personPo);
  
  <T> int getCount(T personPo);
  
  <T> PersonBo selectPersonBoById(Long id);
  
  <T> PersonBo selectPersonBoByIdNumber(String idNumber);
  
  List<AuditPersonBo> selectPersonsByTaskId(Long taskId);
  
  List<AuditPersonBo> selectPersonByVisaStatus(int status);
  
  List<Long> selectHasApplyPersonIds(Long taskId);
  
  int saveTaskAndPerson(Long taskId,Long personId);
  
  int deleteById(Long personId);
  
  int deleteTaskAndPerson(Long personId,Long taskId);
  
  List<AuditPersonBo> selectPersonsByTaskIdForToDoList(Long taskId);
  
  int getTaskAndPerson(Long taskId,Long personId);
  
  PersonBo getTaskHeaderPerson(Long taskId);
  
  
}
