package com.syq.biz.service;

import java.util.List;

import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.IETaskBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.TaskCountryBo;
import com.syq.biz.bo.TaskWordBo;
import com.syq.biz.domain.Manager;
import com.syq.util.constant.TaskStatus;


public interface ITaskService {
  
  boolean save(TaskBo taskBo);
  
  int deleteByIds(Long[] ids);
  
  TaskBo selectById(Long id);
  
  boolean update(TaskBo taskBo);
  
  boolean assign(Long taskId,Long[] accountIds);
  
  TaskBo selectByCode(String code);
  
  TaskBo selectTaskByPersonIdNumber(String idNumber);
  
  void opearteTaskStatus(Long taskId, TaskStatus status);
  
  void batchProcessTask(List<IETaskBo> boes,Manager manager);
  
  void batchProcessPersons(List<IEPersonBo> boes,Long taskId,Manager manager);
  
  TaskBo selectByInstructionNo(String instructionNo);
  
  TaskWordBo getTaskWordVoById(Long taskId);
  
  List<TaskCountryBo> selectTaskCountrysByTaskCode(String taskCode);
  
}
