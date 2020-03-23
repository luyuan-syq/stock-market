package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.AccountBo;
import com.syq.biz.domain.Account;

@Repository
public interface AccountMapper extends BaseMapper<Account>{
  
  <T> List<AccountBo> selectByCondition(T accountPo);
  
  <T> int getCount(T accountPo);
  
  <T> AccountBo selectAccountBoById(Long id);
  
  List<AccountBo> selectAccountsByTaskId(Long taskId);
  
  
}
