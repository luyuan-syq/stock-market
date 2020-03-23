package com.syq.biz.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.syq.biz.bo.AuditPersonBo;
import com.syq.biz.bo.FlowPassportBo;
import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.domain.Passport;
import com.syq.biz.domain.Person;
import com.syq.biz.mapper.PassportMapper;
import com.syq.biz.mapper.PersonMapper;
import com.syq.biz.mapper.TaskMapper;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.IPersonService;
import com.syq.biz.service.ITaskService;
import com.syq.biz.service.IVisaCountryService;
import com.syq.biz.service.IVisaService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.util.constant.VisaStatus;
import com.syq.util.convert.BeanUtil;


@Service
public class PersonService implements IPersonService,IAdapterService<PersonBo> {
  
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PersonService.class);

  @Autowired
  private PersonMapper personMapper;
  
  @Autowired
  private TaskMapper taskMapper;
  
  @Autowired
  private PassportMapper passportMapper;
  
  @Autowired
  private IPassportService passportService;
  
  @Autowired
  private ITaskService taskService;
  
  @Autowired
  private IVisaCountryService visaCountryService;
  
  @Autowired
  private IVisaService visaService;
  
  private final static int CORRECT_SINGLE_OP_COUNT = 1;

  
  @Transactional
  public boolean imsave(IEPersonBo ieBo,PersonBo personBo,Long taskId) {
    
    Person person = new Person();
    BeanUtil.copyProperties(personBo, person);
   
    
    //查看人员是否存在
    PersonBo temp = selectByIdNumber(personBo.getIdNumber());
    if(temp == null) {
      savePersonInfo(person);
    }else{
      BeanUtils.copyProperties(temp, person);
    }
    
    if(!getTaskRelatePerson(taskId,person.getId())) {
      saveTaskRelatePerson(taskId, person.getId());
      saveToBuildPassport(person, taskId);
    }else {
      ieBo.setOk(false);
      ieBo.setErrorMsg("人员已经存在");
    }
    
    return true;
  }
  
  
  @Transactional
  public boolean save(PersonBo personBo,Long taskId) {
    
    Person person = new Person();
    BeanUtil.copyProperties(personBo, person);
   
    
    //查看人员是否存在
    PersonBo temp = selectByIdNumber(personBo.getIdNumber());
    if(temp == null) {
      savePersonInfo(person);
      temp = selectByIdNumber(personBo.getIdNumber());
    }else{
      BeanUtils.copyProperties(temp, person);
    }
    
    if(!getTaskRelatePerson(taskId,person.getId())) {
      saveTaskRelatePerson(taskId, person.getId());
      saveToBuildPassport(person, taskId);
    }
    
    //保存签证信息
    TaskBo taskBo = taskService.selectById(taskId);
    
    String taskCountryCodes = taskBo.getTaskCountry();
    List<VisaCountryBo> countrys = visaCountryService.queryVisaCountryNamesForCode(Arrays.asList(taskCountryCodes.split(",")));
    
    visaService.assertVisaPrincipal(temp.getId(),taskBo.getId(),Arrays.asList(taskCountryCodes.split(",")));
    
    return true;
  }
  
  public boolean update(PersonBo personBo) {
    Person person = new Person();
    BeanUtil.copyProperties(personBo, person);
    return CORRECT_SINGLE_OP_COUNT == personMapper.update(person);
  }

  @Override
  public int getCount(PaginationPo t) {
    return personMapper.getCount(t);
  }

  @Override
  public List<PersonBo> query(PaginationPo t) {
    return personMapper.selectByCondition(t);
  }

  @Override
  public int deleteByIds(Long[] ids) {
    return personMapper.deleteByIds(ids);
  }

  @Override
  public PersonBo selectById(Long id) {
    PersonBo personBo =  personMapper.selectPersonBoById(id);
    return personBo;
  }

  @Override
  public PersonBo selectByIdNumber(String idNumber) {
    
    if (StringUtils.isEmpty(idNumber)){
      return null;
    }
    
    return personMapper.selectPersonBoByIdNumber(idNumber);
  }

  @Override
  public List<AuditPersonBo> getPersonsByTaskId(long taskId) {
    return personMapper.selectPersonsByTaskId(taskId);
  }

  @Override
  public List<AuditPersonBo> getPersonsByVisaStatus(VisaStatus status) {
    return personMapper.selectPersonByVisaStatus(status.getCode());
  }

  @Override
  @Transactional
  public int deleteById(Long personId,Long taskId) {
    
    PersonBo personBo = selectById(personId);
    if (personBo == null){
     return  CORRECT_SINGLE_OP_COUNT;
    }
    
    // TODO 是否需要删除已存在人员信息
    personMapper.deleteById(personId);
    deleteTaskRelatePerson(taskId, personBo);
    return CORRECT_SINGLE_OP_COUNT;
  }
  
  @Override
  public boolean isPersonHasRelateTask(String idNumber) {
    return null != taskService.selectTaskByPersonIdNumber(idNumber);
  }
  
  @Override
  public List<Long> getHasApplyPersonIds(long taskId) {
    return personMapper.selectHasApplyPersonIds(taskId);
  }

  private void saveToBuildPassport(Person person, Long taskId){
    TaskBo task = taskMapper.selectTaskBoById(taskId);
    if (task == null){
      throw new RuntimeException("无效的任务Id:" + taskId);
    }
    
    int opResultCount;
    Passport passport = passportService.getPassportByIdNumber(person.getIdNumber());
    
    if (passport == null){
      passport = new Passport();
      FlowPassportBo flowPassportBo = new FlowPassportBo(person.getId(),taskId,task.getTaskCode(),person.getIdNumber(),0,"");
      BeanUtil.copyProperties(flowPassportBo, passport);
      opResultCount = passportMapper.save(passport);
    } else {
      // TODO 关联前判断护照有效性
      passport.buildRelateTask(taskId, task.getTaskCode());
      opResultCount = passportMapper.update(passport);
    }
    
    if(!(CORRECT_SINGLE_OP_COUNT == opResultCount)) {
      throw new RuntimeException("添加出差人員生成護照信息错误，原因：保存Passport异常");
    }
  }
  
  private void saveTaskRelatePerson(Long taskId, Long personId){
    if(!(CORRECT_SINGLE_OP_COUNT == personMapper.saveTaskAndPerson(taskId, personId))) {
      throw new RuntimeException("保存出差人员任务关系错误");
    }
  }
  
  private boolean  getTaskRelatePerson(Long taskId, Long personId){
   
    return  personMapper.getTaskAndPerson(taskId, personId) >= 1;
  }
  
  private void savePersonInfo(Person person){
    
    PersonBo personBoInDb = personMapper.selectPersonBoByIdNumber(person.getIdNumber());
    int opResultCount;
    if (null != personBoInDb){
      person.setId(personBoInDb.getId());
      opResultCount = personMapper.update(person);
    } else {
      opResultCount = personMapper.save(person);
    }
    
    if(!(CORRECT_SINGLE_OP_COUNT == opResultCount)) {
      throw new RuntimeException("添加出差人员错误，原因：保存Person异常");
    }
  }
  
  private void deleteTaskRelatePerson(Long taskId, PersonBo personBo){
    personMapper.deleteTaskAndPerson(personBo.getId(), taskId);
    
    String idNumber = personBo.getIdNumber();
    if (passportService.getPassportByIdNumber(idNumber) != null){
      // 释放护照关联信息Task
      passportMapper.releasePassportRelateTask(idNumber);
    }
  }

  @Override
  public List<AuditPersonBo> getPersonsByTaskIdForToDoList(long taskId) {
    return personMapper.selectPersonsByTaskIdForToDoList(taskId);
  }


  @Override
  public PersonBo getTaskHeaderPerson(Long taskId) {
    return personMapper.getTaskHeaderPerson(taskId);
  }
}
