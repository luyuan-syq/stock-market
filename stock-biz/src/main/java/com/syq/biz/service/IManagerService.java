package com.syq.biz.service;


import com.syq.biz.bo.ManagerBo;
import com.syq.biz.domain.Manager;


public interface IManagerService {
  
  boolean save(ManagerBo managerBo);
  
  int deleteByIds(Long[] ids);
  
  ManagerBo selectById(Long id);
  
  boolean update(ManagerBo managerBo);
  
  Manager findManagerByNameAndPass(String name,String password);
  
}
