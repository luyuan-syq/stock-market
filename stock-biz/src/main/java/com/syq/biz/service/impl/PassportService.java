/**
 * com.syq.biz.service.impl.PassportService.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午2:07:08
 */
package com.syq.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.syq.biz.bo.ApplyPassportInfoBo;
import com.syq.biz.bo.IEPassportBo;
import com.syq.biz.bo.IETaskBo;
import com.syq.biz.bo.PassportBo;
import com.syq.biz.bo.PersonBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.TaskCodeBo;
import com.syq.biz.domain.ApplyPassportInfo;
import com.syq.biz.domain.Manager;
import com.syq.biz.domain.Passport;
import com.syq.biz.mapper.ApplyPassportInfoMapper;
import com.syq.biz.mapper.PassportMapper;
import com.syq.biz.service.IPassportService;
import com.syq.biz.service.IPersonService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.AuditInfoPo;
import com.syq.pagination.common.PaginationPo;
import com.syq.pagination.common.PassportPo;
import com.syq.util.TimeUtil;
import com.syq.util.constant.AuditStatus;
import com.syq.util.constant.PassportStatus;
import com.syq.util.constant.TaskStatus;
import com.syq.util.convert.BeanUtil;

/**
 * 
 * created by Huangye on 2016年8月28日 下午2:07:08
 */
@Service
public class PassportService implements IPassportService,IAdapterService<PassportBo> {

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PassportService.class);
  
  @Autowired
  private PassportMapper passportMapper;
  
  @Autowired
  private ApplyPassportInfoMapper applyPassportInfoMapper;
  
  @Autowired
  private IPersonService personService;
  
  
    
  public int save(PassportBo passportBo) {
    Passport passport = new Passport();
    BeanUtil.copyProperties(passportBo, passport);
    Integer id = passportMapper.save(passport);
    return id;
  }
  
  @Override
  public Map<Object, Object> getTaskWaitAuditPassportCount() {
    List<Map<String, Object>> result = 
        passportMapper.selectTaskPassportCount(PassportStatus.AUDIT_WAIT.getCode());

    return convertTo(result, "taskCode", "countNum");
  }

  @Override
  public Long insertPassport(PassportBo passportBo) {
    Passport passport = new Passport();
    BeanUtil.copyProperties(passportBo, passport);
    Long id = passportMapper.insertPassport(passport);
    return id;
  }

  @Override
  public int update(PassportBo passportBo) {
    Passport passport = new Passport();
    BeanUtil.copyProperties(passportBo, passport);
    int result = passportMapper.update(passport);
    return result;
  }

  @Override
  public int delete(Long passportId) {
    int result = passportMapper.deleteByPrimaryKey(passportId);
    return result;
  }

  @Override
  public Passport selectByPrimaryKey(Long passportId) {
    Passport passport = passportMapper.selectByPrimaryKey(passportId);
    return passport;
  }

  @Override
  public List<Passport> selectCondition(PassportPo passportPo) {
    List<Passport> passportList = passportMapper.selectCondition(passportPo);
    return passportList;
  }

  @Override
  public int getCount(PaginationPo t) {
    return passportMapper.selectCount(t);
  }

  @SuppressWarnings("unchecked")
  @Override
  public  List<Passport> query(PaginationPo t) {
    return passportMapper.selectCondition(t);
  }

  @Override
  @Transactional
  public int saveApplyPassportInfo(ApplyPassportInfoBo applyPassportInfo) {

    updatePassportStatus(applyPassportInfo.getPersonId(), PassportStatus.AUDIT_WAIT, "流转到待审核");
    
    return saveOrUpdateApplyPassportInfo(applyPassportInfo);
  }
  
  @Override
  public ApplyPassportInfoBo getApplyPassportByPersonId(Long personId) {
    
    ApplyPassportInfo info = ApplyPassportInfo.getNewInstance();
    info.setPersonId(personId);
    
    List<ApplyPassportInfoBo> infoList = applyPassportInfoMapper.getApplyPassportInfoByPersonId(personId);
    
    return CollectionUtils.isEmpty(infoList) ? null : infoList.get(0);
  }
  
  @Override
  public ApplyPassportInfoBo getApplyPassportByIdNumber(String idNumber) {
    PersonBo bo = personService.selectByIdNumber(idNumber);
    if (bo == null || bo.getId() == null){
      LOG.info("idNumber not match person {}", idNumber);
      return null;
    }
    return getApplyPassportByPersonId(bo.getId());
  }

  @Override
  public void auditApplyPassportInfo(AuditInfoPo auditInfo) {    
    PassportStatus flowStatus = auditInfo.getAuditStatus() == AuditStatus.AUDIT_PASS.getCode() ? 
        PassportStatus.HADNDLING : PassportStatus.AUDIT_REJECT;
    
    if (flowStatus == PassportStatus.AUDIT_REJECT && StringUtils.isEmpty(auditInfo.getAuditMsg())){
      throw new RuntimeException("审核决绝必须填写决绝原因");
    }
    
    updatePassportStatus(auditInfo.getPersonId(), flowStatus, auditInfo.getAuditMsg());
  }
  @Override
  public Passport getPassportByIdNumber(String idNumber) {
    PassportPo passportPo = PassportPo.getNewInstance();
    passportPo.setIdNumber(idNumber.trim());
    List<Passport> passports = passportMapper.selectCondition(passportPo);

    return CollectionUtils.isEmpty(passports) ? null : passports.get(0);
  }
  
  @Override
  public Passport getPassportByPersonId(Long personId) {
    PersonBo personBo = personService.selectById(personId);
    
    if (personBo == null || StringUtils.isEmpty(personBo.getIdNumber())){
      throw new RuntimeException("无效的出国人员信息：" + personId);
    }
    
    return this.getPassportByIdNumber(personBo.getIdNumber());
  }
  
  @Override
  public void updatePassportStatus(Long personId, PassportStatus status, String msg) {
    Passport passport = getPassportByPersonId(personId);
    if (passport == null){
      throw new RuntimeException("出国人员未正确生成护照信息" + personId);
    }
    
    passport.buildFlowStatus(status.getCode(), msg);
    if (1 != passportMapper.update(passport)){
      throw new RuntimeException("更新护照信息内部异常");
    }
  }
  
  @Override
  public void updatePassportStatus(String passportCode, PassportStatus status, String msg) {
    Passport passport = getPassportByCode(passportCode);
    
    if (passport == null){
      throw new RuntimeException("无效的护照编码" + passportCode);
    }
    
    passport.buildFlowStatus(status.getCode(), msg);
    if (1 != passportMapper.update(passport)){
      throw new RuntimeException("更新护照信息内部异常");
    }
      
  }
  
  private Passport getPassportByCode(String code){
      
    if (StringUtils.isEmpty(code)){
      return null;
    }
    
    PassportPo bo = new PassportPo();
    bo.setPassportNo(code);
    List<Passport> passports = selectCondition(bo);
    
    return CollectionUtils.isEmpty(passports) ? null : passports.get(0);
  }

  private int saveOrUpdateApplyPassportInfo(ApplyPassportInfoBo applyPassportInfo){
    ApplyPassportInfoBo oldApplyInfo = getApplyPassportByPersonId(applyPassportInfo.getPersonId());
    applyPassportInfo.buildUpdateEntity(oldApplyInfo);
    
    ApplyPassportInfo info = new ApplyPassportInfo();
    BeanUtil.copyProperties(applyPassportInfo, info);
        
    if (oldApplyInfo == null){
      return applyPassportInfoMapper.save(info);
    } else {
      return applyPassportInfoMapper.update(info);  
    }
  }
  
  private Map<Object, Object> convertTo(List<Map<String, Object>> list, String Mapkey, String mapValue){
    Map<Object, Object> resultMap = Maps.newHashMap();
    if (CollectionUtils.isNotEmpty(list)){
      for (Map<String, Object> map : list){
        Object key = map.get(Mapkey);
        Object value = map.get(mapValue);
        resultMap.put(key, value);
      }
    }
    return resultMap;
  }

  @Override
  public boolean updatePassportStatus(Long taskId, PassportStatus status) {
    
    passportMapper.batchUpdateStatus(taskId,status.getCode());
    
    return true;
  }

  @Override
  public int expireCount() {
    
    PassportPo po = PassportPo.getNewInstance();
    po.setExpireEndTime(new Date());
    
    return passportMapper.selectCount(po);
  }

  @Override
  public int overtimeNotReturnCount() {
    
    PassportPo po = PassportPo.getNewInstance();
    po.setPassportStatus(TaskStatus.CLOESD.getCode());
    po.setNotReturnLimitTime(getNotReturnLimitTime());
    
    return passportMapper.selectCount(po);
  }
  
  private Date getNotReturnLimitTime(){
    return TimeUtil.getInsulateDate(new Date(), -10);
  }

  @Override
  public int getCountTodo(PassportPo passportPo) {
    return passportMapper.selectCountForTodo(passportPo);
  }

  @Override
  public List<PassportBo> queryTodo(PassportPo passportPo) {
    return passportMapper.selectConditionForTodo(passportPo);
  }
  
  
  @Override
  @Transactional
  public void batchProcessPassport(List<IEPassportBo> boes, Manager manager) {
    
    Map<String, IEPassportBo> map = convertTo(boes);
    
    if(MapUtils.isNotEmpty(map)) {
      
      for (IEPassportBo bo : map.values()) {  
        //根据护照编码查看是否已经录入护照
        Passport passport = getPassportByCode(bo.getPassportNo());
        if(passport != null) {
          bo.setErrorMsg("护照编码已经存在");
          continue;
        }
        //根据身份证号查看是否存在护照
        
        passport = new Passport();
        BeanUtil.copyProperties(bo, passport);
        passport.setOperatorId(manager.getName());
        passport.setOperatorTime(new Date());
        passport.setFlowStatus(PassportStatus.FILE_IN.getCode());
        passportMapper.save(passport);
        bo.setOk(true);
      } 
    }
  }
  
  /**
   * 身份证号相同的保留过期时间最大的数据
   * @param boes
   * @return
   * created by Jinglei on 2017年4月30日 下午4:44:16
   */
  private Map<String, IEPassportBo> convertTo(List<IEPassportBo> boes){
    
    if(CollectionUtils.isEmpty(boes)) {
      return null;
    }
    
    Map<String, IEPassportBo> resultMap = Maps.newHashMap();
    
    for (IEPassportBo bo : boes){
      IEPassportBo tempBo = resultMap.get(bo.getIdNumber());
      if (tempBo == null){
        resultMap.put(bo.getIdNumber(), bo);
        continue;
      }
      
      if (bo.getDateExpire() == null || tempBo.getDateExpire() == null){
        throw new RuntimeException("存在护照过期时间为空数据，请核查！");
      }
      
      if (bo.getDateExpire().after(tempBo.getDateExpire())){
        resultMap.put(bo.getIdNumber(), bo);
      }
    }
    
    return resultMap;
  }
  
}
