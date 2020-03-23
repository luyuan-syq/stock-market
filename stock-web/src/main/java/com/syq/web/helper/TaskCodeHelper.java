package com.syq.web.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.TaskCodeBo;
import com.syq.util.TaskCodeGenerator;
import com.syq.util.constant.TaskCodeStatus;

public class TaskCodeHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskCodeHelper.class);
  
  public static List<TaskCodeBo> generate(int num,String operatorId) {
    List<TaskCodeBo> boes = new ArrayList<TaskCodeBo>();
    TaskCodeBo code = null;
    Date createTime = new Date();
    
    for(int i = 0;i<num;i++) {
      code = new TaskCodeBo();
      code.setCreateTime(createTime);
      code.setOperatorId(operatorId);
      code.setOperatorTime(createTime);
      code.setStatus(TaskCodeStatus.UNDISTRIBUTED.getCode());//未分配
      code.setTaskCode(TaskCodeGenerator.generate());
      boes.add(code);
    }
    
    return boes;
  }
}
