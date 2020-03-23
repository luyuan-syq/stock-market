package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.ManagerBo;
import com.syq.biz.domain.Manager;

@Repository
public interface ManagerMapper extends BaseMapper<Manager>{
  
  <T> List<ManagerBo> selectByCondition(T managerPo);
  
  <T> int getCount(T managerPo);
  
  <T> ManagerBo selectManagerBoById(Long id);
  
  boolean saveManagerAndRole(ManagerBo managerBo);
  
  Manager findManagerByNameAndPass(String name,String password);
  
  
}
