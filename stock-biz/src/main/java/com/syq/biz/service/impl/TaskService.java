package com.syq.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.IETaskBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.bo.TaskCountryBo;
import com.syq.biz.bo.TaskWordBo;
import com.syq.biz.domain.Manager;
import com.syq.biz.domain.Task;
import com.syq.biz.mapper.PersonMapper;
import com.syq.biz.mapper.TaskMapper;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.ITaskCodeService;
import com.syq.biz.service.ITaskService;
import com.syq.exception.SYQException;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.util.TaskCodeGenerator;
import com.syq.util.constant.TaskCodeStatus;
import com.syq.util.constant.TaskStatus;
import com.syq.util.convert.BeanUtil;

@Service
public class TaskService implements ITaskService, IAdapterService<TaskBo> {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(TaskService.class);

  @Autowired
  private TaskMapper taskMapper;

  @Autowired
  private PersonMapper personMapper;

  @Autowired
  private ITaskCodeService taskCodeService;
  
  @Autowired
  private IPersonService personService;

  @Override
  public int getCount(PaginationPo t) {
    return taskMapper.getCount(t);
  }

  @Override
  public List<TaskBo> query(PaginationPo t) {

    List<TaskBo> list = taskMapper.selectByCondition(t);
    return list;
  }

  @Override
  @Transactional
  public boolean save(TaskBo taskBo) {
    Task task = new Task();
    BeanUtil.copyProperties(taskBo, task);

    boolean isOk = 1 == taskMapper.save(task);
    if (!isOk) {
      throw new SYQException("保存任务失败!");
    }

    taskCodeService.updateStatus(task.getTaskCode(), TaskCodeStatus.USEING);

    return isOk;

  }

  @Override
  public int deleteByIds(Long[] ids) {
    return taskMapper.deleteByIds(ids);
  }

  @Override
  public TaskBo selectById(Long id) {
    TaskBo taskBo = taskMapper.selectTaskBoById(id);
    return taskBo;
  }

  @Override
  @Transactional
  public boolean update(TaskBo taskBo) {
    Task task = new Task();
    BeanUtil.copyProperties(taskBo, task);
    if (CollectionUtils.isNotEmpty(taskBo.getTaskCountrys())){
      for(TaskCountryBo bo : taskBo.getTaskCountrys()) {
        bo.setCreateTime(new Date());
        taskMapper.batchReplaceTaskCountrys(bo);
      }
     
    }
    return 1 == taskMapper.update(task);
  }

  @Transactional
  public boolean assign(Long taskId, Long[] accountIds) {
    taskMapper.unAssign(taskId);
    if (accountIds != null && accountIds.length > 0) {
      taskMapper.assign(taskId, accountIds);
    }
//    Task task = new Task();
//    task.setId(taskId);
//    taskMapper.update(task);
    return true;
  }

  @Override
  public TaskBo selectTaskByPersonIdNumber(String idNumber) {
    PersonBo personBo = personMapper.selectPersonBoByIdNumber(idNumber);
    if (personBo == null) {
      return null;
    }

    List<TaskBo> list = taskMapper.selectTaskByPersonId(personBo.getId());
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public void opearteTaskStatus(Long taskId, TaskStatus status) {
    TaskBo bo = selectById(taskId);
    if (bo == null) {
      throw new RuntimeException("不存在的任务ID" + taskId);
    }
    bo.buildStatus(status);
    update(bo);
  }

  @Override
  public TaskBo selectByCode(String code) {
    List<TaskBo> list = taskMapper.selectByCode(code);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    return list.get(0);
  }
  
  @Override
  public TaskBo selectByInstructionNo(String instructionNo) {
    List<TaskBo> list = taskMapper.selectByInstructionNo(instructionNo);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    return list.get(0);
  }
  
  

  @Override
  @Transactional
  public void batchProcessTask(List<IETaskBo> boes, Manager manager) {
    if(!CollectionUtils.isEmpty(boes)) {
      for(IETaskBo bo : boes) {
        //生成任务编码
        List<TaskCodeBo> taskCodeList = generate(1,manager.getId().toString());
        taskCodeService.addBatch(taskCodeList);
        //绑定任务
        TaskBo taskBo = new TaskBo();
        BeanUtil.copyProperties(bo, taskBo);
        fillTaskBo(taskBo,manager.getId().toString());
        taskBo.setTaskCode(taskCodeList.get(0).getTaskCode());
        //查看任务批号
        TaskBo temp = selectByInstructionNo(bo.getInstructionNo());
        if(temp != null) {
          bo.setErrorMsg("批件号已经绑定任务");
          continue;
        }
        save(taskBo);
        bo.setOk(true);
      }
    }
  }
  
  @Override
  @Transactional
  public void batchProcessPersons(List<IEPersonBo> boes, Long taskId,Manager manager) {
    if(!CollectionUtils.isEmpty(boes)) {
      for(IEPersonBo bo : boes) {
        bo.setOk(true);
        PersonBo personBo = new PersonBo();
        BeanUtil.copyProperties(bo, personBo);
        fillPersonBo(personBo,manager.getId().toString());
        
        //根据批件号查询任务
        List<TaskBo> taskBoes = taskMapper.selectByInstructionNo(personBo.getInstructionNo());
        if(CollectionUtils.isEmpty(taskBoes)) {
          bo.setOk(false);
          bo.setErrorMsg("批件号对应的任务不存在");
          continue;
        }
        
        personService.imsave(bo,personBo, taskBoes.get(0).getId());
        
      }
    }
  }

  private List<TaskCodeBo> generate(int num, String operatorId) {
    List<TaskCodeBo> boes = new ArrayList<TaskCodeBo>();
    TaskCodeBo code = null;
    Date createTime = new Date();

    for (int i = 0; i < num; i++) {
      code = new TaskCodeBo();
      code.setCreateTime(createTime);
      code.setOperatorId(operatorId);
      code.setOperatorTime(createTime);
      code.setStatus(TaskCodeStatus.UNDISTRIBUTED.getCode());// 未分配
      code.setTaskCode(TaskCodeGenerator.generate());
      boes.add(code);
    }

    return boes;
  }
  
  private void fillTaskBo(TaskBo bo,String operatorId) {
    Date currDate = new Date();
    bo.setOperatorId(operatorId);
    bo.setOperatorTime(currDate);
    bo.setCreateTime(currDate);
    bo.setStatus(TaskStatus.NEW_APPLY.getCode());
    
  }
  
  private void fillPersonBo(PersonBo bo,String operatorId) {
    bo.setCreateTime(new Date());
    bo.setOperatorId(operatorId);
    bo.setOperatorTime(new Date());
  }

  @Override
  public TaskWordBo getTaskWordVoById(Long taskId) {
    TaskBo bo = selectById(taskId);
    return TaskWordBo.converTo(bo);
  }

  @Override
  public List<TaskCountryBo> selectTaskCountrysByTaskCode(String taskCode) {
    return taskMapper.selectTaskCountrysByTaskCode(taskCode);
  }

}
