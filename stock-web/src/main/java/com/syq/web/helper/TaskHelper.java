package com.syq.web.helper;


import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.TaskCountryBo;
import com.syq.util.constant.TaskStatus;
import com.syq.util.convert.BeanUtil;
import com.syq.web.vo.TaskCountryVo;
import com.syq.web.vo.TaskVo;

public class TaskHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskHelper.class);
  
  public static TaskBo taskVoToBoForCreate(TaskVo vo) {
    TaskBo bo = new TaskBo();
    Date currDate = new Date();
    BeanUtil.copyProperties(vo, bo);
    bo.setOperatorId("100000");
    bo.setOperatorTime(currDate);
    bo.setCreateTime(currDate);
    bo.setStatus(TaskStatus.NEW_APPLY.getCode());
    return bo;
    
  }
  
  public static TaskBo taskVoToBoForUpdate(TaskVo vo) {
    TaskBo bo = new TaskBo();
    BeanUtil.copyProperties(vo, bo);
    bo.setOperatorId("100000");
    bo.setOperatorTime(new Date());
    return bo;
    
  }
  
  public static List<TaskCountryBo> convertTo(String taskCode, String instructionNo, List<TaskCountryVo> taskCountrys){
      List<TaskCountryBo> resultList = Lists.newArrayList();
      
      if (CollectionUtils.isEmpty(taskCountrys)){
        return resultList;
      }
      
      for (TaskCountryVo vo : taskCountrys){
        TaskCountryBo bo = TaskCountryBo.getNewInstance(taskCode, instructionNo);
        BeanUtil.copyProperties(vo, bo);
        resultList.add(bo);
      }
      return resultList;
  }
}
