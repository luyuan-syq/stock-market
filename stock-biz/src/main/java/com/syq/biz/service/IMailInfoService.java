package com.syq.biz.service;

import com.syq.biz.domain.MailInfo;


public interface IMailInfoService {
  
  boolean saveOrUpdate(MailInfo mailInfoBo);
  
  MailInfo select();

}
