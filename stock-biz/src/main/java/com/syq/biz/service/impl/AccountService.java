package com.syq.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.util.convert.BeanUtil;
import com.syq.biz.bo.AccountBo;
import com.syq.biz.domain.Account;
import com.syq.biz.mapper.AccountMapper;
import com.syq.biz.service.IAccountService;


@Service
public class AccountService implements IAccountService,IAdapterService<AccountBo> {

  @Autowired
  private AccountMapper accountMapper;

  public boolean save(AccountBo accountBo) {
    Account account = new Account();
    BeanUtil.copyProperties(accountBo, account);
    return 1 == accountMapper.save(account);
  }
  
  public boolean update(AccountBo accountBo) {
    Account account = new Account();
    BeanUtil.copyProperties(accountBo, account);
    return 1 == accountMapper.update(account);
  }


  @Override
  public int getCount(PaginationPo t) {
    
    return accountMapper.getCount(t);
  }

  @Override
  public List<AccountBo> query(PaginationPo t) {
    
    return accountMapper.selectByCondition(t);
  }


  @Override
  public int deleteByIds(Long[] ids) {
    return accountMapper.deleteByIds(ids);
  }


  @Override
  public AccountBo selectById(Long id) {
    AccountBo accountBo =  accountMapper.selectAccountBoById(id);
    return accountBo;
  }

  @Override
  public List<AccountBo> getAccountsByTaskId(long taskId) {
    return accountMapper.selectAccountsByTaskId(taskId);
  }
  
  


}
