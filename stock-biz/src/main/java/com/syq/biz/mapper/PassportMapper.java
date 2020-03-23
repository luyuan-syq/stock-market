/**
 * com.syq.biz.mapper.PassportMapper.java
 * created by Huangye(yuanhan.syq@alibaba-inc.com) on 2016年8月28日 下午12:39:52
 */
package com.syq.biz.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.PassportBo;
import com.syq.biz.domain.Passport;
import com.syq.pagination.common.PassportPo;

/**
 * 
 * created by Huangye on 2016年8月28日 下午12:39:52
 */
@Repository
public interface PassportMapper extends BaseMapper<Passport>{
  
  Passport selectByPrimaryKey(Long id);
  
  <T> int selectCount(T passport);
  
  Long insertPassport(Passport passport);
  
  <T> List<Passport> selectCondition(T passport);
  
  int deleteByPrimaryKey(Long id);
  
  void releasePassportRelateTask(String idNumber);
  
  List<Map<String, Object>> selectTaskPassportCount(Integer flowStatus);
  
  int batchUpdateStatus(Long taskId,int status);
  
  int selectCountForTodo(PassportPo passportPo);
  
  List<PassportBo> selectConditionForTodo(PassportPo passportPo);
}
