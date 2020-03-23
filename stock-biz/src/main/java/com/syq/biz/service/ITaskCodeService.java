package com.syq.biz.service;

import java.util.List;

import com.syq.biz.bo.TaskCodeBo;
import com.syq.util.constant.TaskCodeStatus;


public interface ITaskCodeService {
  
  boolean addBatch(List<TaskCodeBo> boes);
  
  TaskCodeBo findByCode(String code);
  
  boolean updateStatus(String taskCode, TaskCodeStatus status);
}
