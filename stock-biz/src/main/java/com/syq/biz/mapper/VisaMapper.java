package com.syq.biz.mapper;

import java.util.List;
import java.util.Map;

import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.VisaPrincipalBo;
import com.syq.biz.domain.VisaPrincipal;
import com.syq.util.constant.VisaStatus;


public interface VisaMapper {

  VisaPrincipalBo selectById(long id);

  int batchSaveVisaAttribute(Map parameters);

  VisaPrincipalBo selectVisaAttribute(Map parameters);

  int saveVisaAttribute(Map parameters);

  int updateVisaAttribute(Map parameters);

  VisaPrincipalBo selectByPersonIdTaskIdAndCode(Long personId, Long taskId, String templateCode);

  boolean insert(VisaPrincipal visaPrincipal);

  boolean audit(VisaPrincipal visaPrincipal);

  boolean batchUpdateStatus(Long visaPrincipalId, int status);

  <T> List<TaskBo> selectTaskByCondition(T taskPo);

  <T> int getTaskCount(T taskPo);
  
  <T> List<TaskBo> selectVisaPrincipalByCondition(T taskPo);

  <T> int getVisaPrincipalCount(T taskPo);
  
  VisaPrincipalBo getVisaPrincipal(Long visaPrincipalId);
  
  int updateVisaStatusByTaskId(long taskId,int status);
  
  <T> List<TaskBo> selectVisaPrincipalByConditionForTodo(T taskPo);

  <T> int getVisaPrincipalCountForTodo(T taskPo);
  
  
  <T> int getNewVisaPrincipalCount(T taskPo);
  
  <T> List<TaskBo> selectNewVisaPrincipalByCondition(T taskPo);

}
