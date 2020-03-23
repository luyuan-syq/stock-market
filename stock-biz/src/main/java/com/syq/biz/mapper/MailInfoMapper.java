package com.syq.biz.mapper;

import org.springframework.stereotype.Repository;

import com.syq.biz.domain.MailInfo;

@Repository
public interface MailInfoMapper extends BaseMapper<MailInfo>{
  
  boolean saveOrUpdate(MailInfo mailInfo);
  
}
