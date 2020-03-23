package com.syq.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.ManagerBo;
import com.syq.biz.domain.Manager;
import com.syq.biz.mapper.ManagerMapper;
import com.syq.biz.service.IManagerService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.util.convert.BeanUtil;


@Service
public class ManagerService implements IManagerService,IAdapterService<ManagerBo> {

  @Autowired
  private ManagerMapper managerMapper;

  public boolean save(ManagerBo managerBo) {
    Manager manager = new Manager();
    BeanUtil.copyProperties(managerBo, manager);
    managerMapper.save(manager);
    managerBo.setId(manager.getId());
    managerMapper.saveManagerAndRole(managerBo);
    return true;
  }
  
  public boolean update(ManagerBo managerBo) {
    Manager manager = new Manager();
    BeanUtil.copyProperties(managerBo, manager);
    return 1 == managerMapper.update(manager);
  }


  @Override
  public int getCount(PaginationPo t) {
    
    return managerMapper.getCount(t);
  }

  @Override
  public List<ManagerBo> query(PaginationPo t) {
    
    return managerMapper.selectByCondition(t);
  }


  @Override
  public int deleteByIds(Long[] ids) {
    return managerMapper.deleteByIds(ids);
  }


  @Override
  public ManagerBo selectById(Long id) {
    ManagerBo managerBo =  managerMapper.selectManagerBoById(id);
    return managerBo;
  }

  @Override
  public Manager findManagerByNameAndPass(String name, String password) {
    return managerMapper.findManagerByNameAndPass(name, password);
  }

}
