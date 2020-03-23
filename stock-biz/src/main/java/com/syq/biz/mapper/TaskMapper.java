package com.syq.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.TaskCountryBo;
import com.syq.biz.domain.Task;


public interface TaskMapper extends BaseMapper<Task>{
  
  <T> List<TaskBo> selectByCondition(T taskPo);
  
  <T> int getCount(T taskPo);
  
  <T> TaskBo selectTaskBoById(Long id);
  
  int unAssign(Long taskId);
  
  int assign(@Param("taskId") Long taskId,@Param("accountIds") Long[] accountIds);
  
  List<TaskBo> selectByCode(String code);
  
  List<TaskBo> selectByInstructionNo(String instructionNo);
  
  List<TaskBo> selectTaskByPersonId(Long personId);
  
  int batchReplaceTaskCountrys(TaskCountryBo taskCountryBo);
  
  List<TaskCountryBo> selectTaskCountrysByTaskCode(String taskCode);
  
}
