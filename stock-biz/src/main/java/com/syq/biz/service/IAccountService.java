package com.syq.biz.service;


import java.util.List;

import com.syq.biz.bo.AccountBo;


public interface IAccountService {
  
  boolean save(AccountBo accountBo);
  
  int deleteByIds(Long[] ids);
  
  AccountBo selectById(Long id);
  
  boolean update(AccountBo accountBo);
  
  List<AccountBo> getAccountsByTaskId(long taskId);
  
}
