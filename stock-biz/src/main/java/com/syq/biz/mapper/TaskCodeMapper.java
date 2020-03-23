package com.syq.biz.mapper;


import java.util.List;

import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.domain.TaskCode;

public interface TaskCodeMapper extends BaseMapper<TaskCode>{
  
  <T> List<TaskCodeBo> selectByCondition(T taskCodePo);
  
  <T> int getCount(T taskCodePo);
  
  List<TaskCode> selectByTaskCode(TaskCode code);
}
