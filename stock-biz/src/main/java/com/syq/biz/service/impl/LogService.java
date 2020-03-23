package com.syq.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.domain.Log;
import com.syq.biz.mapper.LogMapper;
import com.syq.biz.service.ILogService;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;

@Service
public class LogService implements ILogService,IAdapterService<Log>{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(LogService.class);
  
  @Autowired
  private LogMapper logMapper;

  @Override
  public <T extends PaginationPo> int getCount(T t) {
    return logMapper.getCount(t);
  }

  @Override
  public <T extends PaginationPo> List<Log> query(T t) {
    return logMapper.selectByCondition(t);
  }
}
