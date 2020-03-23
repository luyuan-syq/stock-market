package com.syq.biz.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.domain.TaskCode;
import com.syq.biz.mapper.TaskCodeMapper;
import com.syq.biz.service.ITaskCodeService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.util.constant.TaskCodeStatus;
import com.syq.util.convert.BeanUtil;

@Service
public class TaskCodeService implements ITaskCodeService,IAdapterService<TaskCodeBo>{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskCodeService.class);
  
  @Autowired
  private TaskCodeMapper taskCodeMapper;

  @Override
  public int getCount(PaginationPo t) {
    return taskCodeMapper.getCount(t);
  }

  @Override
  public  List<TaskCodeBo> query(PaginationPo t) {
    
    return taskCodeMapper.selectByCondition(t);
  }

  @Override
  public boolean addBatch(List<TaskCodeBo> boes) {
    List<TaskCode> taskCodes = BeanUtil.copyList(boes, TaskCode.class);
    return boes.size() == taskCodeMapper.batchSave(taskCodes);
  }

  @Override
  public TaskCodeBo findByCode(String code) {
    TaskCode taskCode = new TaskCode();
    taskCode.setTaskCode(code);
    
    List<TaskCode> taskCodes = taskCodeMapper.selectByTaskCode(taskCode);
    
    if(CollectionUtils.isNotEmpty(taskCodes)) {
      TaskCodeBo bo = new TaskCodeBo();
      BeanUtil.copyProperties(taskCodes.get(0), bo);
      return bo;
    }
    
    return null;
  }

  @Override
  public boolean updateStatus(String taskCode, TaskCodeStatus status) {
    TaskCode taskCodeBo = TaskCode.getNewInstance().buildUpdateEntity(taskCode, status.getCode(), "shaoyuqui");
    return 1 == taskCodeMapper.update(taskCodeBo);
  }
  
  
}
